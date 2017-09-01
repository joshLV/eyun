package com.rainsoft.union.web.sysmanage.service.impl;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.UploadImgUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.subsystem.service.SubSystemService;
import com.rainsoft.union.web.sysmanage.dao.IMemberInfoDao;
import com.rainsoft.union.web.sysmanage.model.MemberInfo;
import com.rainsoft.union.web.sysmanage.service.IMemberInfoService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 个人信息
 *
 */
@Service("memberInfoService")
public class MemberInfoServiceImpl extends MybatisBasePersitenceServiceImpl<MemberInfo, String> implements IMemberInfoService{
	private static Logger logger = (Logger)LoggerFactory.getLogger(MemberInfoServiceImpl.class);
	public int width = 80;                    //图片宽默认
    public int height = 80;                //图片高默认
	@Resource
	private IMemberInfoDao memberInfoDao;
    @Resource
    private SubSystemService subSystemServiceImpl;
	
	@Override
	protected IMybatisPersitenceDao<MemberInfo, String> getBaseDao() {
		return memberInfoDao;
	}
	
	
	/**
	 * 方法功能说明：密码设置
	 * @param MemberInfo 会员信息实体类
	 * @param userId 当前会员id
	 * @param newUserPassword 用户设置密码
	 * @param currentPassword 用户当前的密码
	 * @param
	 * @return
	 */
	public Result updateMemberPwd(MemberInfo memberInfo, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		//首先判断用户输入当前密码是否正确
		Integer count = memberInfoDao.findCountByKeyId("chcekCurrentPwd",memberInfo);
		logger.info("检查用户输入的当前密码是否正确返回记录："+ count);
		if (count == 1) {//密码正确，继续操作
			count = memberInfoDao.update("updatePwd", memberInfo);
            // 修改密码同步旺旺吧进销存  add by sun 2016年7月7日 14:10:15 start
            subSystemServiceImpl.updatePassword(memberInfo.getUserId(), memberInfo.getNewUserPassword());
            //   add by sun 2016年7月7日 14:10:15 end
            if (count != null && count > 0) {
				result.setMessage(requestContext.getMessage("修改成功！"));
				result.setStatus(Constants.RETURN_SUCCESS);
			} else {
				result.setMessage(requestContext.getMessage("修改失败！"));
				result.setStatus(Constants.RETURN_ERROR);
			}
		} else {
			result.setMessage(requestContext.getMessage("当前密码错误！"));
			result.setStatus(Constants.RETURN_ERROR);
		}
		return result;
	}
	
	/**
	 * 方法功能说明：会员信息
	 * @param userId 当前会员id
	 * @return MemberInfo 会员信息实体类
	 */
	public Result findMemberInfoById(String UserId, HttpServletRequest request)  throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		JSONObject json = new JSONObject();
		MemberInfo memberInfo = memberInfoDao.findById(UserId);
		if(memberInfo!=null){
			json.put("memberInfo", memberInfo);
			result.setMessage(requestContext.getMessage("获取数据成功！"));
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setData(JSONObject.toJSONString(memberInfo));
		}else{
			result.setMessage(requestContext.getMessage("获取数据失败！"));
			result.setStatus(Constants.RETURN_ERROR);
		}
		return result;
	}
	/**
	 * 检查别名是否存在
	 * @param memberInfo 个人信息实体
	 * @param requestContext
	 * @return  Result
	 * @throws Exception
	 */
	private Result checkNickName(MemberInfo memberInfo, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		Integer rs = null;
		Integer count = memberInfoDao.findCountByKeyId("checkNickName", memberInfo);
		logger.info("检查别名是否存在返回：" + count);
		if(count == null){
			rs = 0;
		} else {
			rs = -1;
		}
		if(rs != null){
			result.setCount(rs);
			if(rs == -1){
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("该别名已经存在！"));
			} 
		}
		return result;
	}
	/**
	 * 方法功能说明：设置用户别名
	 * @param memberInfo  个人信息实体
	 * @param nickName 用户别名
	 * @param userId 当前账户Id
	 * @return
	 */
	@Override
	public Result updateNickName(MemberInfo memberInfo, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		logger.info("处理结果rs说明: 1:设置别名成功； -1：别名重复；-2：设置失败；");
		Result result = null;
		result = checkNickName(memberInfo, request);
		if(result != null && result.getCount() < 0){
			return result;
		}
		logger.info("返回结果："+ result);
		Integer count = memberInfoDao.update("updateNickName", memberInfo);
		logger.info("返回记录结果：" + count);
		if (count != null &&  count > 0) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("别名设置成功！"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("别名设置失败！"));
		}
		return result;
	}
	
	


	/**
	 * 修改会员的信息
	 */
	public Integer updateMemberInfo(MemberInfo memberInfo, MultipartFile file) throws Exception {
	        String url = "";
	        String tempUrl = memberInfo.getHeadImgUrl();
	        if (file != null) {
	            url = uploadPicUrl(file,"0");
	            memberInfo.setHeadImgUrl(url);
	        }
	        Integer rs = getBaseDao().update(memberInfo);
            if (file != null) {
                List<String> fileList = new ArrayList<>();
                fileList.add(tempUrl);
                // 删除上传的图片
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
        }else if ("0".equals(materialType)) {
            //会员头像
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
