package com.rainsoft.base.common.utils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class UploadImgUtil {
    private static final Logger logger = LoggerFactory.getLogger(UploadImgUtil.class);

    /**
     * 图片上传到远程服务器
     *
     * @param localPath 本地文件路径，
     * @return String remoteURl
     * @throws java.io.IOException
     */
    public static String uploadToRemote(String localPath, String smallPath, int memberId) throws IOException {
        File file = new File(localPath);
        if (!file.exists() || !file.isFile()) {
            logger.error("UploadImgUtil.uploadToRemote-->图片文件不存在，获取不是图片文件！");
            return null;
        }
        /**获取大图片的远程路径**/
        String name = file.getName();
        String remotePath = getRemotePath(name, memberId);
        /**获取小图的远程地址**/
        String remoteSamllPath = null;
        if (!CommonUtil.isEmpty(smallPath)) {
            File smallFile = new File(smallPath);
            if (smallFile.exists() && smallFile.isFile()) {
                String smallName = smallFile.getName();
                remoteSamllPath = getRemoteSmallPath(smallName);
            }
        }

        ContinueFTP continueFTP = ContinueFTP.getInstance();
        String hostname = continueFTP.getServerIP();   //获取服务器Ip
        Integer port = continueFTP.getPort();          //获取服务器端口
        String username = continueFTP.getFtpUserName();//获取服务器用户名
        String password = continueFTP.getFtpPwd();     //获取服务器密码
        if (continueFTP.connect(hostname, port, username, password)) {
            //图片上传服务器操作
            ContinueFTP.UploadStatus uploadStatus = null;
            try {
                uploadStatus = continueFTP.upload(localPath, remotePath);
                if (!CommonUtil.isEmpty(smallPath) && !CommonUtil.isEmpty(remoteSamllPath)) {
                    if (uploadStatus == ContinueFTP.UploadStatus.Upload_New_File_Success || uploadStatus == ContinueFTP.UploadStatus.Upload_From_Break_Success) {
                        /**略缩图上传服务器操作**/
                        uploadStatus = continueFTP.upload(smallPath, remoteSamllPath);
                    }
                }
            } catch (IOException e) {
                logger.error("***素材图片上传IO异常" + e.getMessage());
            } catch (Exception e) {
                logger.error("***素材图片上传异常" + e.getMessage());
            } finally {
                if (continueFTP != null) {
                    continueFTP.disconnect();
                }
            }
            //判断是否文件上传成功     上传成功修改文件状态
            if (uploadStatus == ContinueFTP.UploadStatus.Upload_New_File_Success || uploadStatus == ContinueFTP.UploadStatus.Upload_From_Break_Success) {
                return continueFTP.getDomainName() + remotePath;
            }
        }
        return "";
    }

    /**
     * 获取上传服务器地址
     *
     * @return String
     */
    private static String getRemotePath(String name, int memberId) {
        //创建路径   远程文件路径    /upload/eyun/YYYY/MM/dd/User/memberID/XXXX.jpg
        StringBuilder path = new StringBuilder();
        path.append(Constants.SAVE_PATH);
        path.append(memberId);
        path.append("/");
        path.append(CommonUtil.getyear());
        path.append(CommonUtil.getmonth());
        path.append("/");
        path.append(CommonUtil.getday());
        path.append("/");
        path.append(name);
        return path.toString();
    }

    /**
     * 获取略缩图上传服务器地址
     *
     * @return
     */
    private static String getRemoteSmallPath(String name) {
        //创建路径   远程文件路径    /upload/eyun/YYYY/MM/dd/User/small/memberID/XXXX.jpg
        StringBuilder path = new StringBuilder();
        path.append("/small/");
        path.append(name);
        return path.toString();
    }


    /**
     * * 将图片文件输出到指定的路径，并可设定压缩质量
     *
     * @param outImgPath [输出路径]
     * @param newImg     [新图片]
     * @param per        [压缩比例]
     */
    @SuppressWarnings("restriction")
    public static void outImage(String outImgPath, BufferedImage newImg, float per, boolean removeOldPic) throws Exception {
        // 判断输出的文件夹路径是否存在，不存在则创建  
        File file = new File(outImgPath);
        File folder = file.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String parentPath = folder.getAbsolutePath();
        if (removeOldPic) {
            removeTmpFile(parentPath); //删除文件夹中的文件
        }
        // 输出到文件流  
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(outImgPath));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(newImg);
            /* 压缩质量 */
            jep.setQuality(per, true);
            encoder.encode(newImg, jep);
        } catch (FileNotFoundException e) {
            logger.error("素材图片下载本地错误：FileNotFoundException 异常" + e.getMessage());
        } catch (ImageFormatException e) {
            logger.error("素材图片下载本地错误：ImageFormatException 异常" + e.getMessage());
        } catch (IOException e) {
            logger.error("素材图片下载本地错误：IOException 异常" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static float getCompressTimes(File imgFile) {
        float per = 1;
        if (imgFile != null) {
            float size = imgFile.length() / 1024;//bytes
            per = size < 40 ? 1 : 40 / size;
        }
        return per;
    }

    /**
     * 图片压缩处理（按质量压缩）
     *
     * @return BufferedImage 【新图片缓冲数据对象】
     * @throws Exception
     */
    public static BufferedImage getCompressImgByQuality(File imgfile) throws Exception {
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(imgfile);  // 构造BufferedImage对象  
        } catch (IOException e) {
            logger.error("ImgTmpUploadAction.compressImageUpload-->" + e.getMessage());
        }
        if (srcImage == null) {
            logger.error("ImgTmpUploadAction.compressImageUpload-->读取图片文件失败！");
            return null;
        }
        int imgW = srcImage.getWidth(), imgH = srcImage.getHeight();

        BufferedImage bimg = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
        // 在新的画布上生成原图的缩略图
        Graphics2D g = bimg.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, imgW, imgH);
        g.drawImage(srcImage, 0, 0, imgW, imgH, Color.white, null);
        g.dispose();

        BufferedImage newImg = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
        newImg.getGraphics().drawImage(bimg.getScaledInstance(imgW, imgH, Image.SCALE_SMOOTH), 0, 0, null);
        return newImg;
    }

    /**
     * 判断是否图片类型格式
     *
     * @param extName [包含点‘.’]
     * @return
     */
    public static boolean isImageFormat(String extName) {
        //图片格式
        String imgArr = ".jpg.jpeg.gif.png.bmp";
        if (imgArr.indexOf(extName) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除文件下面的图片文件
     *
     * @param path
     */
    public static void removeTmpFile(String path) throws Exception {
        File folder = new File(path);//文件夹对象
        if (folder.exists() && folder.isDirectory()) { //文件夹存在，则删除文件夹下面的文件
            String[] fileList = folder.list(); //获取文件列表
            if (fileList != null) {
                File temp = null;
                for (int i = 0; i < fileList.length; i++) {
                    //判断是否是图片文件，不是则直接返回
                    String fileName = fileList[i];
                    if (CommonUtil.isEmpty(fileName)) {
                        continue;
                    }
                    if (fileName.indexOf(",") >= 0) { //如果文件名中含有，
                        String ext = fileName.substring(fileName.indexOf(","));
                        if (!isImageFormat(ext)) {
                            continue;
                        }
                        if (path.endsWith(File.separator)) {
                            temp = new File(path + fileList[i]);
                        } else {
                            temp = new File(path + File.separator + fileList[i]);
                        }
                        if (temp.exists() && temp.isFile()) {
                            temp.delete();
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    private static void delFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            logger.error("" + e.getMessage());
        }
    }

    /**
     * 删除删除ftp服务器的图片
     * @param path 文件url
     * @throws IOException
     */
    public static void delFiles(java.util.List<String> path) throws IOException {
        ContinueFTP continueFTP = ContinueFTP.getInstance();
        String hostname = continueFTP.getServerIP();   //获取服务器Ip
        Integer port = continueFTP.getPort();          //获取服务器端口
        String username = continueFTP.getFtpUserName();//获取服务器用户名
        String password = continueFTP.getFtpPwd();     //获取服务器密码
        if (continueFTP.connect(hostname, port, username, password)) {
            /**开始删除文件**/
            try {
                continueFTP.deleteFlie(path);
            } catch (IOException e) {
                logger.error("***素材图片删除O异常" + e.getMessage());
            } catch (Exception e) {
                logger.error("***素材图片删除异常" + e.getMessage());
            } finally {
                if (continueFTP != null) {
                    continueFTP.disconnect();
                }
            }
        }
    }

    /**
     * 图片上传到远程服务器
     *
     * @param localPath 本地文件路径，
     * @return String remoteURl
     * @throws java.io.IOException
     */
    public static String uploadToRemote(String localPath, int memberId) throws IOException {
        File file = new File(localPath);
        if (!file.exists() || !file.isFile()) {
            logger.error("UploadImgUtil.uploadToRemote-->图片文件不存在，获取不是图片文件！");
            return null;
        }
        /**获取大图片的远程路径**/
        String name = file.getName();
        String remotePath = getRemotePath(name, memberId);
        ContinueFTP continueFTP = ContinueFTP.getInstance();
        String hostname = continueFTP.getServerIP();   //获取服务器Ip
        Integer port = continueFTP.getPort();          //获取服务器端口
        String username = continueFTP.getFtpUserName();//获取服务器用户名
        String password = continueFTP.getFtpPwd();     //获取服务器密码
        if (continueFTP.connect(hostname, port, username, password)) {
            //图片上传服务器操作
            ContinueFTP.UploadStatus uploadStatus = null;
            try {
                uploadStatus = continueFTP.upload(localPath, remotePath);
            } catch (IOException e) {
                logger.error("***素材图片上传IO异常" + e.getMessage());
            } catch (Exception e) {
                logger.error("***素材图片上传异常" + e.getMessage());
            } finally {
                if (continueFTP != null) {
                    continueFTP.disconnect();
                }
            }
            //判断是否文件上传成功     上传成功修改文件状态
            if (uploadStatus == ContinueFTP.UploadStatus.Upload_New_File_Success || uploadStatus == ContinueFTP.UploadStatus.Upload_From_Break_Success) {
                return continueFTP.getDomainName() + remotePath;
            }
        }
        return "";
    }
}
