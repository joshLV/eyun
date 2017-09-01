package com.rainsoft.base.common.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * ImgUploadController 图片上传处理Controllers类
 * @author jinyanjie
 *	edit by 13646223842@163.com 2015年12月7日 15:53:34
 */
@Controller
@RequestMapping("/upload")
public class ImgUploadController{
	
	private static final Logger logger = LoggerFactory.getLogger(ImgUploadController.class);

	public int width =100;					//图片宽默认
	public int height=100;				//图片高默认

	@RequestMapping("/uploadPro")
    public void execute(@RequestParam(value = "imgFile", required = false) MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	/**返回结果 0成功   -1失败**/
    	String result = "-1";
    	/**返回信息**/
    	String message = "";
        /**返回图片路径**/
    	String url="";
		/**上传素材的类型 type
		 * 0：头像；1：portal背景图；2：logo；3：中心广告；4:普通广告；5：手机版广告;
		 **/
		String type = request.getParameter("type");
		/**MultipartFile 强制装成 File**/
		CommonsMultipartFile cf= (CommonsMultipartFile)file;
		DiskFileItem  diskFileItem = (DiskFileItem) cf.getFileItem();
		File imageFile = diskFileItem.getStoreLocation();

    	if(!imageFile.exists()){
    		message = "您选择的文件不存在！";
    		returnResult(result, message, url,response);
    		return;
    	}
    	/**获取图片扩展名**/
    	String extName = getImageFormat(diskFileItem);
    	/**判断是否图片类型格式**/
    	if(!isImageFormat(extName)){
    		message = "您选择的图片格式不正确！";
    		returnResult(result, message, url,response);
			return;
		}
    	/**判断图片是否尺寸是否需要压缩**/
    	boolean compressFlag = isCompressImage(type,imageFile);
    	/**设置图片width、height**/
    	getImageSize(type);
    	/**新文件名 时间戳 + 6位随机数 保证文件名不会重复**/
    	String newImageName = System.currentTimeMillis() + CommonUtil.getRandomString(6);
    	/**本地路径**/
    	//String localPath = imageFile.getAbsolutePath();
    	/**图片父目录**/
    	String parentPath = imageFile.getParent();
    	/**压缩处理临时目录**/
    	String tempPath = parentPath + "/temp/" + newImageName + extName;
    	/**略缩图临时目录**/
    	String smallPath = parentPath + "/temp/small/" + newImageName + extName;

		File tempFile = new File(tempPath);
		/**文件不存在，创建**/
		if(!tempFile.getParentFile().exists()){
			tempFile.getParentFile().mkdirs();
		}
    	/**质量压缩参数**/
    	if(compressFlag){
    		/**生成压缩图片**/
			compressImageUpload(tempPath, width, height, imageFile);
		}else {
			//rename
			imageFile.renameTo(new File(tempPath));
		}
		/**略缩图参数**/
		lessenImageSize(new File(tempPath),response);
		File smallFile = new File(smallPath);
		/**文件不存在，创建**/
		if(!smallFile.getParentFile().exists()){
			smallFile.getParentFile().mkdir();
		}
		compressImageUpload(tempPath, smallPath, width, height, 1);
    	/**开始上传**/
    	try {
    		/**上传操作**/
    		url = UploadImgUtil.uploadToRemote(tempPath, smallPath, SpringMvcUtil.getUserId());
		} catch (Exception e) {
			e.printStackTrace(); 
			message = "上传失败！";
			returnResult(result, message, "",response);
		}finally{
				/**删除临时图片（压缩图）**/
				delTempImage(tempPath);
			if(!"0".equals(type)){
				/**删除临时图片（略缩图）**/
				delTempImage(smallPath);
			}
		}
    	
		if(StringUtil.isEmpty(url)){
			message = "上传失败！";
			returnResult(result, message, "",response);
		}
    	
		result = "0";
		message = "上传成功";
		returnResult(result, message, url,response);
    }

    /**
     * 获取图片扩展名（图片后缀）
     * @return
     */
    public String getImageFormat(DiskFileItem file){
    	/**扩展名格式：**/
		String extName="";
		if (file.getName().lastIndexOf(".") >= 0) {
			extName = file.getName().substring(file.getName().lastIndexOf("."));
		}
		return extName;
    }
    
    /**
     * 判断是否图片类型格式
     * @param extName
     * @return
     */
    public boolean isImageFormat(String extName){
    	/**图片格式**/
		String imgArr = ".JPG.JPEG.GIF.PNG.BMP";//无论大小写
		if (imgArr.contains(extName.toUpperCase())) {
			return true;
		}
		return false;
    }

	/**
	 * 图片压缩尺寸设置（按比例压缩）
	 * @param imageFile
	 */
    public void lessenImageSize(File imageFile,HttpServletResponse response){
		Image img = null;
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
			logger.error("图片压缩异常"+e.getMessage());
			returnResult("-2", "图片压缩异常", "",response);
		}
		/**获取图片的实际大小 高度**/
		double h = img.getHeight(null);
		/**获取图片的实际大小 宽度**/
		double w = img.getWidth(null);
		int destWidth = 0, destHeight = 0; 
		/**为等比缩放计算输出的图片宽度及高度**/
		if((w > Constants.WIDTH_LESSEN) || (h > Constants.HEIGHT_LESSEN)){
			/**为等比缩放计算输出的图片宽度及高度**/
			double rateW = w / Constants.WIDTH_LESSEN * 1.0;
			double rateH = h / Constants.HEIGHT_LESSEN * 1.0;
			/**根据缩放比率大的进行缩放控制**/
			double rate = rateW > rateH ? rateW : rateH;
			destWidth = (int)(w / rate);
			destHeight = (int)(h / rate);
		}else{
			destWidth = (int)w;
			destHeight = (int)h;
		}
		width = destWidth;
		height = destHeight;
    }

	/**
	 * 图片压缩处理（按质量压缩）
	 * @param outImgPath
	 * @param width
	 * @param height
	 * @param imageFile
	 * @throws Exception
	 */
    public void compressImageUpload(String outImgPath, int width, int height,File imageFile) throws Exception {
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

		// 得到图片
		// 根据原图的大小生成空白画布
		/* BufferedImage tempImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		 // 在新的画布上生成原图的缩略图
    	 Graphics2D g = tempImg.createGraphics();  
    	 g.setColor(Color.white);  
         g.fillRect(0, 0, width, height);  
         g.drawImage(srcImage, 0, 0, width, height, Color.white, null);  
         g.dispose();
         
         BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
         newImg.getGraphics().drawImage(tempImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);  */
		// 调用方法输出图片文件
		//UploadImgUtil.outImage(outImgPath, newImg, 1, false);
	}

	/**
	 * 图片压缩处理（按质量压缩）
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
		/**得到图片**/
		// 根据原图的大小生成空白画布
		/*BufferedImage tempImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 在新的画布上生成原图的缩略图
		Graphics2D g = tempImg.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.drawImage(srcImage, 0, 0, width, height, Color.white, null);
		g.dispose();

		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(tempImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		// 调用方法输出图片文件
		UploadImgUtil.outImage(outImgPath, newImg,1,false);*/
	}

    /**
     *
     * @param result (0：上传成功；-1:图片上传失败；-2：会员session超时)
     * @param msg
     * @param url
     */
    public void returnResult(String result, String msg, String url,HttpServletResponse response){
    	
    	//json格式转换
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("result", result);
    	jsonObj.put("msg", msg);
    	jsonObj.put("url", url);

        //设置编码
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = null;
        try {
        	pw = response.getWriter();
        	pw.print(jsonObj);
        } catch (Exception e) {
        	e.printStackTrace(); 
        	logger.error("message输出出错！"+ e.getMessage());
        }finally{
        	pw.flush();
        	pw.close();
        }
    }
    /**
	 * 根据广告位 匹配图片width、height
	 * @param materialType 素材类型
	 */
	public void getImageSize(String materialType){
		
		//portal背景图
		if("1".equals(materialType)){
			width = Constants.WIDTH_PORTA_BACKGROUP;
			height = Constants.HEIGHT_PORTA_BACKGROUP;
		}else if("2".equals(materialType)){
			//logo
			width = Constants.WIDTH_LOGO;
			height = Constants.HEIGHT_LOGO;
		}else if("3".equals(materialType)){
			//中心广告
			width = Constants.WIDTH_POSTER_ONE;
			height = Constants.HEIGHT_POSTER_ONE;
		}else if("4".equals(materialType)){
			//普通广告
			width = Constants.WIDTH_POSTER_TWO;
			height = Constants.HEIGHT_POSTER_TWO;
		}else if("5".equals(materialType)){
			//手机广告
			width = Constants.WIDTH_POSTER_MOBILE;
			height = Constants.HEIGHT_POSTER_MOBILE;
		}
	}

	/**
	 * 图片是否需要压缩处理
	 * @param materialType
	 * @return
	 */
	public Boolean isCompressImage(String materialType,File imageFile){
		Image img = null;
		try {
			img = ImageIO.read(imageFile);
			/**图片宽**/
			int w = img.getWidth(null);
			/**图片高**/
			int h = img.getHeight(null);
			/**portal背景图**/
			if("1".equals(materialType)){
				if((w == Constants.WIDTH_PORTA_BACKGROUP) && (h == Constants.HEIGHT_PORTA_BACKGROUP)){
					return false;
				}
			}else if("2".equals(materialType)){
				/**logo**/
				if((w == Constants.WIDTH_LOGO) && (h == Constants.HEIGHT_LOGO)){
					return false;
				}
			}else if("3".equals(materialType)){
				/**中心广告**/
				if((w == Constants.WIDTH_POSTER_ONE) && (h == Constants.HEIGHT_POSTER_ONE)){
					return false;
				}
			}else if("4".equals(materialType)){
				/**普通广告**/
				if((w == Constants.WIDTH_POSTER_TWO) && (h == Constants.HEIGHT_POSTER_TWO)){
					return false;
				}
			}else if("5".equals(materialType)){
				/**手机版广告**/
				if((w == Constants.WIDTH_POSTER_MOBILE) && (h == Constants.HEIGHT_POSTER_MOBILE)){
					return false;
				}
			}else if("0".equals(materialType)){
				/**个人头像**/
				if((w == Constants.WIDTH_AVATAR) && (h == Constants.HEIGHT_AVATAR)){
					return false;
				}
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("图片压缩异常"+e.getMessage());
		}
		return true;
	}
	
	/**
	 * 删除压缩临时图片
	 * @param path
	 */
	public void delTempImage(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
}
