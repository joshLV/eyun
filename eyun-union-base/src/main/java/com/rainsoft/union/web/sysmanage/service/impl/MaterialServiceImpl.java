package com.rainsoft.union.web.sysmanage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.*;
import com.rainsoft.union.web.sysmanage.dao.IMaterialDao;
import com.rainsoft.union.web.sysmanage.model.Material;
import com.rainsoft.union.web.sysmanage.service.IMaterialService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 认证素材
 *
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Service("materialService")
public class MaterialServiceImpl extends MybatisBasePersitenceServiceImpl<Material, String> implements IMaterialService {
    @Resource
    private IMaterialDao materialDao;

    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);
    public int width = 100;                    //图片宽默认
    public int height = 100;                //图片高默认

    @Override
    protected IMybatisPersitenceDao<Material, String> getBaseDao() {
        return materialDao;
    }

    /**
     * 保存素材
     *
     * @param material 素材
     * @return 返回值
     */
    @Override
    public Integer saveMaterial(Material material, MultipartFile file) throws Exception {
        Integer rs = -1;//-1 代表失败
        String url = uploadPicUrl(file, material.getMaterialType());
        if (StringUtil.isEmpty(url)) {
            return -5;
        }
        material.setMaterialUrl(url);
        // 这里开始查重
        rs = getBaseDao().findCountBy(material);
        if (rs != null && rs >= 0) {
            List<String> fileList = new ArrayList<String>();
            fileList.add(url);
            // 删除上传的图片
            UploadImgUtil.delFiles(fileList);
            rs = -2;//素材名称重复
        } else {
            rs = getBaseDao().save(material);
        }
        return rs;
    }

    /**
     * 修改素材
     *
     * @param material 素材
     * @return 返回值
     */
    @Override
    public Integer updateMaterial(Material material, MultipartFile file) throws Exception {
        String url = "";
        String tempUrl = material.getMaterialUrl();
        if (file != null) {
            url = uploadPicUrl(file, material.getMaterialType());
            material.setMaterialUrl(url);
        }
        // 这里开始查重
        Integer rs = getBaseDao().findCountBy(material);
        if (rs != null && rs >= 0) {
            if (file != null) {
                List<String> fileList = new ArrayList<>();
                fileList.add(url);
                // 删除上传的图片
                UploadImgUtil.delFiles(fileList);
            }
            rs = -2;//素材名称重复
        } else {
            rs = getBaseDao().update(material);
            if (file != null) {
                List<String> fileList = new ArrayList<>();
                fileList.add(tempUrl);
                // 删除上传的图片
                UploadImgUtil.delFiles(fileList);
            }
        }
        return rs;
    }

    /**
     * 删除素材
     *
     * @param ids          传入的id 一个或者多个
     * @param updateTimes  修改时间 同上
     * @param materialUrls 素材的url
     * @return 返回值
     * @throws Exception
     */
    @Override
    public Integer deleteMaterial(String ids, String updateTimes, String materialUrls) throws Exception {
        /**String 转成数组用于循环处理数据**/
        String[] idArr = ids.split(",");
        String[] updateTimeArr = updateTimes.split(",");
        String[] materialUrlArr = materialUrls.split(",");
        /**文件url**/
        List<String> fileList = new ArrayList<>();
        /**数组转成list，存放的是id**/
        List<String> list = Arrays.asList(idArr);
        Map<String, Object> map = new HashMap<>();
        /**根据传入id 查询数据更新时间放入map**/
        List<Material> updateTimeList = materialDao.selectList("getUpdateTimeByIds", list);
        if (updateTimeList != null && updateTimeList.size() > 0) {
            for (Material m : updateTimeList) {
                map.put(m.getId() + "", m.getUpdateTimeStr());
            }
        }
        for (int i = 0; i < idArr.length; i++) {
            /**如果数据已被更改 则取消操作**/
            if (!updateTimeArr[i].equals(map.get(idArr[i]))) {
                return -2;//数据已被更改
            }
            fileList.add(materialUrlArr[i]);
        }
        /**开始删除数据**/
        int rs = -1;
        if (idArr.length == 1) {
            rs = getBaseDao().deleteById(idArr[0]);
        } else {
            rs = getBaseDao().deleteByIds(idArr);
        }
        if (rs > 0) {
            /**数据删除成功后才开始删除文件**/
            UploadImgUtil.delFiles(fileList);
        }
        return rs;
    }

    /**
     * @param file         上传的图片
     * @param materialType 素材类型 用于压缩成对应的尺寸
     * @return 生成的图片url
     */
    public String uploadPicUrl(MultipartFile file, String materialType) throws Exception {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem diskFileItem = (DiskFileItem) cf.getFileItem();
        File imageFile = diskFileItem.getStoreLocation();
        String extName = getImageFormat(diskFileItem);// 文件扩展名
        /**判断图片是否尺寸是否需要压缩**/
        boolean compressFlag = isCompressImage(materialType, imageFile);
        /**设置图片width、height**/
        getImageSize(materialType);
        /**新文件名 时间戳 + 6位随机数 保证文件名不会重复**/
        String newImageName = System.currentTimeMillis() + CommonUtil.getRandomString(6);
        /**图片父目录**/
        String parentPath = imageFile.getParent();
        /**压缩处理临时目录**/
        String tempPath = parentPath + "/temp/" + newImageName + extName;
        /**略缩图临时目录**/
        String smallPath = parentPath + "/temp/small/" + newImageName + extName;

        File tempFile = new File(tempPath);
        /**文件不存在，创建**/
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        /**质量压缩参数**/
        if (compressFlag) {
            /**生成压缩图片**/
            compressImageUpload(tempPath, width, height, imageFile);
        } else {
            //rename
            imageFile.renameTo(new File(tempPath));
        }
        /**略缩图参数**/
        lessenImageSize(new File(tempPath));
        File smallFile = new File(smallPath);
        /**文件不存在，创建**/
        if (!smallFile.getParentFile().exists()) {
            smallFile.getParentFile().mkdir();
        }
        compressImageUpload(tempPath, smallPath, width, height, 1);
        String url = ""; // 返回图片路径
        try {
            /**上传操作**/
            url = UploadImgUtil.uploadToRemote(tempPath, smallPath, SpringMvcUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**删除临时图片（压缩图）**/
            delTempImage(tempPath);
            if (!"0".equals(materialType)) {
                /**删除临时图片（略缩图）**/
                delTempImage(smallPath);
            }
        }
        return url;
    }

    /**
     * 获取图片扩展名（图片后缀）
     *
     * @return
     */
    public String getImageFormat(DiskFileItem file) {
        /**扩展名格式：**/
        String extName = "";
        if (file.getName().lastIndexOf(".") >= 0) {
            extName = file.getName().substring(file.getName().lastIndexOf("."));
        }
        return extName;
    }

    /**
     * 判断是否图片类型格式
     *
     * @param extName
     * @return
     */
    public boolean isImageFormat(String extName) {
        /**图片格式**/
        String imgArr = ".JPG.JPEG.GIF.PNG.BMP";//无论大小写
        if (imgArr.contains(extName.toUpperCase())) {
            return true;
        }
        return false;
    }

    /**
     * 图片压缩尺寸设置（按比例压缩）
     *
     * @param imageFile
     */
    public void lessenImageSize(File imageFile) {
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            logger.error("图片压缩异常" + e.getMessage());
        }
        /**获取图片的实际大小 高度**/
        double h = img.getHeight(null);
        /**获取图片的实际大小 宽度**/
        double w = img.getWidth(null);
        int destWidth = 0, destHeight = 0;
        /**为等比缩放计算输出的图片宽度及高度**/
        if ((w > Constants.WIDTH_LESSEN) || (h > Constants.HEIGHT_LESSEN)) {
            /**为等比缩放计算输出的图片宽度及高度**/
            double rateW = w / Constants.WIDTH_LESSEN * 1.0;
            double rateH = h / Constants.HEIGHT_LESSEN * 1.0;
            /**根据缩放比率大的进行缩放控制**/
            double rate = rateW > rateH ? rateW : rateH;
            destWidth = (int) (w / rate);
            destHeight = (int) (h / rate);
        } else {
            destWidth = (int) w;
            destHeight = (int) h;
        }
        width = destWidth;
        height = destHeight;
    }

    /**
     * 图片压缩处理（按质量压缩）
     *
     * @param outImgPath
     * @param width
     * @param height
     * @param imageFile
     * @throws Exception
     */
    public void compressImageUpload(String outImgPath, int width, int height, File imageFile) throws Exception {
        //BufferedImage srcImage = null;
        Image srcImage = null;
        try {
            // 构造BufferedImage对象
            srcImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(srcImage, 0, 0, width, height, null); // 绘制缩小后的图
        File destFile = new File(outImgPath);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }

    /**
     * 图片压缩处理（按质量压缩）
     *
     * @param imgPath
     * @param outImgPath
     * @param width
     * @param height
     * @param per
     * @throws Exception
     */
    public void compressImageUpload(String imgPath, String outImgPath, int width, int height, float per) throws Exception {
        File file = new File(imgPath);
        //BufferedImage srcImage = null;
        Image srcImage = null;
        try {
            /**构造BufferedImage对象**/
            srcImage = ImageIO.read(file);
        } catch (IOException e) {
            logger.error("素材图片压处理异常" + e.getMessage());
        }
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(srcImage, 0, 0, width, height, null); // 绘制缩小后的图
        File destFile = new File(outImgPath);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }

    /**
     * 根据广告位 匹配图片width、height
     *
     * @param materialType 素材类型
     */
    public void getImageSize(String materialType) {

        //portal背景图
        if ("1".equals(materialType)) {
            width = Constants.WIDTH_PORTA_BACKGROUP;
            height = Constants.HEIGHT_PORTA_BACKGROUP;
        } else if ("2".equals(materialType)) {
            //logo
            width = Constants.WIDTH_LOGO;
            height = Constants.HEIGHT_LOGO;
        } else if ("3".equals(materialType)) {
            //中心广告
            width = Constants.WIDTH_POSTER_ONE;
            height = Constants.HEIGHT_POSTER_ONE;
        } else if ("4".equals(materialType)) {
            //普通广告
            width = Constants.WIDTH_POSTER_TWO;
            height = Constants.HEIGHT_POSTER_TWO;
        } else if ("5".equals(materialType)) {
            //手机广告
            width = Constants.WIDTH_POSTER_MOBILE;
            height = Constants.HEIGHT_POSTER_MOBILE;
        } else if ("0".equals(materialType)) {
            /**个人头像**/
            width = Constants.WIDTH_AVATAR;
            height = Constants.HEIGHT_AVATAR;
        }
    }

    /**
     * 图片是否需要压缩处理
     *
     * @param materialType
     * @return
     */
    public Boolean isCompressImage(String materialType, File imageFile) {
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            /**图片宽**/
            int w = img.getWidth(null);
            /**图片高**/
            int h = img.getHeight(null);
            /**portal背景图**/
            if ("1".equals(materialType)) {
                if ((w == Constants.WIDTH_PORTA_BACKGROUP) && (h == Constants.HEIGHT_PORTA_BACKGROUP)) {
                    return false;
                }
            } else if ("2".equals(materialType)) {
                /**logo**/
                if ((w == Constants.WIDTH_LOGO) && (h == Constants.HEIGHT_LOGO)) {
                    return false;
                }
            } else if ("3".equals(materialType)) {
                /**中心广告**/
                if ((w == Constants.WIDTH_POSTER_ONE) && (h == Constants.HEIGHT_POSTER_ONE)) {
                    return false;
                }
            } else if ("4".equals(materialType)) {
                /**普通广告**/
                if ((w == Constants.WIDTH_POSTER_TWO) && (h == Constants.HEIGHT_POSTER_TWO)) {
                    return false;
                }
            } else if ("5".equals(materialType)) {
                /**手机版广告**/
                if ((w == Constants.WIDTH_POSTER_MOBILE) && (h == Constants.HEIGHT_POSTER_MOBILE)) {
                    return false;
                }
            } else if ("0".equals(materialType)) {
                /**个人头像**/
                if ((w == Constants.WIDTH_AVATAR) && (h == Constants.HEIGHT_AVATAR)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("图片压缩异常" + e.getMessage());
        }
        return true;
    }

    /**
     * 删除压缩临时图片
     *
     * @param path
     */
    public void delTempImage(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
