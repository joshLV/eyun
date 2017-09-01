package com.rainsoft.union.web.account.service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.model.PromotionsModel;
import com.rainsoft.union.web.account.model.WwbStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 优惠活动
 * Created by sun on 2016/9/21.
 */
public interface PromotionsService {
    /**
     * jqGrid 表格请求数据
     * @param promotionsModel
     * @return
     */
    JSONObject getPromotionsList(PromotionsModel promotionsModel, PageBean page) throws Exception;

    /**
     * 易盟号获取对应的场所
     * @param name 易盟号
     * @return 场所列表
     * @throws Exception
     */
    List<PromotionsModel> getPlaceListByEyun(String name) throws Exception;

    /**
     * 赠送旺旺币
     * @param promotionsModel 条件
     * @return 是否成功
     */
    Integer updatePresentWwb(PromotionsModel promotionsModel,MultipartFile file)throws Exception;

    /**
     * 修改旺旺币余额表
     * @param promotionsModel
     * @return
     * @throws Exception
     */
    Integer updateWwbInfo(PromotionsModel promotionsModel)throws Exception;

    /**
     * 删除赠送数据（只能删除未审核的）
     * @param id
     * @return
     */
    Integer deletePresent(Integer id)throws Exception;

    /**
     * 审核
     * @param promotionsModel
     * @return
     * @throws Exception
     */
    Integer updateSubmitPresentWwb(PromotionsModel promotionsModel,MultipartFile file)throws Exception;

    /**
     * 获取策略列表
     * @param wwbStrategy 旺旺币策略
     * @param page        分页对象
     * @return            json格式数据
     * @throws Exception
     */
    JSONObject getWwbStrategyList(WwbStrategy wwbStrategy, PageBean page) throws Exception;

    /**
     * 新增策略
     * @param wwbStrategy 策略实体
     * @return
     */
    Integer insertStrategy(WwbStrategy wwbStrategy)throws Exception;

    /**
     * 修改策略
     * @param wwbStrategy 策略实体
     * @return
     */
    Integer updateStrategy(WwbStrategy wwbStrategy)throws Exception;

}
