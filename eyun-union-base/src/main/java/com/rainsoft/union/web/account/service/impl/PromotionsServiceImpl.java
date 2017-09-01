package com.rainsoft.union.web.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.bean.BeanMapUtil;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.service.impl.CommonUtilService;
import com.rainsoft.base.common.utils.*;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.union.web.account.dao.PromotionsDao;
import com.rainsoft.union.web.account.model.PromotionsModel;
import com.rainsoft.union.web.account.model.WwbStrategy;
import com.rainsoft.union.web.account.service.PromotionsService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 优惠活动
 * Created by sun on 2016/9/21.
 */
@Service
public class PromotionsServiceImpl implements PromotionsService {

    @Resource
    private PromotionsDao promotionsDao;
    private static final Logger logger = LoggerFactory.getLogger(PromotionsServiceImpl.class);
    private static final int maxSize = 10;//凭证限制大小 单位：M

    /**
     * 优惠策略列表
     *
     * @param promotionsModel
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPromotionsList(PromotionsModel promotionsModel, PageBean page) throws Exception {
        List<PromotionsModel> list;
        //使用map作为参数传入dao层
        Map<String, Object> paramMap;
        paramMap = BeanMapUtil.toMap(promotionsModel);
        paramMap.put("SORT", page.getSort());
        paramMap.put("DIR", page.getOrder());
        // mybatis 分页插件
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = promotionsDao.getPromotionsList(paramMap);
        page.setTotal(((Page<PromotionsModel>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());          // 当前页
        jsonObject.put("totalPages", page.getPageCount());      // 总页数
        jsonObject.put("totalRecords", page.getTotal());        // 总记录数
        // 页面显示记录数设置
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
    }

    @Override
    public List<PromotionsModel> getPlaceListByEyun(String name) throws Exception {
//        List<PromotionsModel> list = promotionsDao.getPlaceListByEyun(name);
        List<PromotionsModel> list = promotionsDao.getPlaceListByParam(name);
        return list;
    }

    /**
     * 新增充送旺旺币
     * @param promotionsModel 条件
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Integer updatePresentWwb(PromotionsModel promotionsModel, MultipartFile file) throws Exception {
        if (file != null) {
            if (file.getSize() > 10 * 1024*1024) {
                return -3;//图片大小不得大于10M
            }
            String url = uploadPicUrl(file);
            if (StringUtil.isEmpty(url)) {
                return -2;//凭证上传失败
            }
            promotionsModel.setCertificate(url);
        }
        promotionsModel.setStatus("N");
        String payChannel = promotionsModel.getPayChannel();
        // 如果是赠送 则人民币消费是0
        if ("9".equals(payChannel)) {
            promotionsModel.setRmbFee(0.0d);
        }else if ("4".equals(payChannel)) {
            promotionsModel.setWwbFee(DecimalCalculate.add(promotionsModel.getRmbFee(),promotionsModel.getWwbFee()));
        }
        // 保存记录
        return promotionsDao.presentWwb(promotionsModel);
    }

    @Override
    public Integer updateWwbInfo(PromotionsModel promotionsModel) throws Exception {
        promotionsModel.setStatus("8");
        promotionsDao.updatePresentWwb(promotionsModel);
        return promotionsDao.updateWwbInfo(promotionsModel);
    }

    @Override
    public Integer deletePresent(Integer id) throws Exception {
        return promotionsDao.deletePresent(id);
    }

    @Override
    public Integer updateSubmitPresentWwb(PromotionsModel promotionsModel, MultipartFile file) throws Exception {
        String tempUrl = promotionsModel.getCertificate();//存放原来的凭证地址
        String payChannel = promotionsModel.getPayChannel();
        // 如果是赠送 则人民币消费是0
        if ("9".equals(payChannel)) {
            promotionsModel.setRmbFee(0.0d);
        }else if ("4".equals(payChannel)) {
            promotionsModel.setWwbFee(DecimalCalculate.add(promotionsModel.getRmbFee(),promotionsModel.getWwbFee()));
        }
        int rs = -1;
        if (file != null) {
            if (file.getSize() > 10 * 1024*1024) {
                return -3;//图片大小不得大于10M
            }
            String url = uploadPicUrl(file);
            if (StringUtil.isEmpty(url)) {
                return -2;//凭证上传失败
            }
            promotionsModel.setCertificate(url);
            rs = promotionsDao.updatePresentWwb(promotionsModel);
            List<String> fileList = new ArrayList<>();
            fileList.add(tempUrl);
            // 删除上传的图片
            UploadImgUtil.delFiles(fileList);
        } else {
            promotionsModel.setCertificate("");
            rs = promotionsDao.updatePresentWwb(promotionsModel);
        }
        return rs;
}

    @Override
    public JSONObject getWwbStrategyList(WwbStrategy wwbStrategy, PageBean page) throws Exception {
        List<WwbStrategy> list;
        //使用map作为参数传入dao层
        Map<String, Object> paramMap;
        paramMap = BeanMapUtil.toMap(wwbStrategy);
        paramMap.put("SORT", page.getSort());
        paramMap.put("DIR", page.getOrder());
        // mybatis 分页插件
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = promotionsDao.getWwbStrategyList(paramMap);
        list = getAreaName(list);
        page.setTotal(((Page<WwbStrategy>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());          // 当前页
        jsonObject.put("totalPages", page.getPageCount());      // 总页数
        jsonObject.put("totalRecords", page.getTotal());        // 总记录数
        // 页面显示记录数设置
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
    }

    @Override
    public Integer insertStrategy(WwbStrategy wwbStrategy) throws Exception {
        return promotionsDao.insertStrategy(wwbStrategy);
    }

    @Override
    public Integer updateStrategy(WwbStrategy wwbStrategy) throws Exception {
        return promotionsDao.updateStrategy(wwbStrategy);
    }

    /**
     * 获取对应区域的名称
     *
     * @param list
     * @return
     */
    private List getAreaName(List<WwbStrategy> list) {
        List<BaseArea> areaList = CommonUtilService.areaList;
        for (WwbStrategy wwbStrategy : list) {
            String refCode = wwbStrategy.getRefCode();
            String province = "";
            String city = "";
            String country = "";
            String areaName = "";
            if (!"00".equals(refCode.substring(4))) {//县
                province = refCode.substring(0, 2) + "0000";
                city = refCode.substring(0, 4) + "00";
                country = refCode;
                for (BaseArea baseArea : areaList) {
                    if (baseArea.getId().toString().equals(province)) {
                        province = baseArea.getName();
                        break;
                    }

                }
                for (BaseArea baseArea : areaList) {
                    if (baseArea.getId().toString().equals(city)) {
                        city = baseArea.getName();
                        break;
                    }

                }
                for (BaseArea baseArea : areaList) {
                    if (baseArea.getId().toString().equals(country)) {
                        country = baseArea.getName();
                        break;
                    }

                }
                wwbStrategy.setRefCodeName(province + "-" + city + "-" + country);
            } else if (refCode.substring(2).equals("0000")) {//省
                province = refCode;
                for (BaseArea baseArea : areaList) {
                    if (baseArea.getId().toString().equals(province)) {
                        areaName = baseArea.getName();
                        wwbStrategy.setRefCodeName(areaName);
                        break;
                    }
                }
            } else {
                //市
                province = refCode.substring(0, 2) + "0000";
                city = refCode;
                for (BaseArea baseArea : areaList) {
                    if (baseArea.getId().toString().equals(province)) {
                        province = baseArea.getName();
                        break;
                    }

                }
                for (BaseArea baseArea : areaList) {
                    if (baseArea.getId().toString().equals(city)) {
                        city = baseArea.getName();
                        break;
                    }
                }
                wwbStrategy.setRefCodeName(province + "-" + city);
            }

        }
        return list;
    }

    /**
     * @param file         上传的图片
     * @return 生成的图片url
     */
    public String uploadPicUrl(MultipartFile file) throws Exception {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem diskFileItem = (DiskFileItem) cf.getFileItem();
        File imageFile = diskFileItem.getStoreLocation();
        String extName = getImageFormat(diskFileItem);// 文件扩展名
        /**新文件名 时间戳 + 6位随机数 保证文件名不会重复**/
        String newImageName = System.currentTimeMillis() + CommonUtil.getRandomString(6);
        /**图片父目录**/
        String parentPath = imageFile.getParent();
        /**压缩处理临时目录**/
        String tempPath = parentPath + "/temp/" + newImageName + extName;
        File tempFile = new File(tempPath);
        /**文件不存在，创建**/
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        //rename
        imageFile.renameTo(new File(tempPath));
        String url = ""; // 返回图片路径
        try {
            /**上传操作**/
            url = UploadImgUtil.uploadToRemote(tempPath, SpringMvcUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**删除临时图片（压缩图）**/
            delTempImage(tempPath);
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
