package com.rainsoft.union.web.account.dao;

import com.rainsoft.union.web.account.model.PromotionsModel;
import com.rainsoft.union.web.account.model.WwbStrategy;

import java.util.List;
import java.util.Map;

/**
 * 优惠活动dao
 * Created by sun on 2016/9/21.
 */
public interface PromotionsDao {
    /**
     * 获取赠送列表
     * @param map 实体
     * @return 列表
     */
    List<PromotionsModel> getPromotionsList(Map map);

    /**
     * 查询该会员拥有的场所
     * @param name 账号名称
     * @return 场所列表
     */
//    List<PromotionsModel> getPlaceListByEyun(String name);

    /**
     * 根据易盟账号，场所名称【模糊】，场所编号，设备号获取场所
     * @author qianna
     * @param name [易盟账号，场所名称【模糊】，场所编号，设备号获取场所]
     * @return 场所列表
     */
    List<PromotionsModel> getPlaceListByParam(String name);

    /**
     * 赠送旺旺币
     * @param promotionsModel 条件
     * @return 是否成功
     */
    Integer presentWwb(PromotionsModel promotionsModel);

    /**
     * 同步总金额
     * @param promotionsModel 条件
     * @return 是否成功
     */
    Integer updateWwbInfo(PromotionsModel promotionsModel);

    /**
     * 审核
     * @param promotionsModel 条件
     * @return
     */
    Integer updatePresentWwb(PromotionsModel promotionsModel);

    /**
     * 删除赠送数据（只能删除未审核的）
     * @param promotionsModel
     * @return
     */
    Integer deletePresent(Integer id);

    /**
     * 优惠策略列表
     * @param map
     * @return
     */
    List<WwbStrategy> getWwbStrategyList(Map map);

    /**
     * 新增策略
     * @param wwbStrategy 策略实体
     * @return
     */
    Integer insertStrategy(WwbStrategy wwbStrategy);

    /**
     * 修改策略
     * @param wwbStrategy 策略实体
     * @return
     */
    Integer updateStrategy(WwbStrategy wwbStrategy);
}
