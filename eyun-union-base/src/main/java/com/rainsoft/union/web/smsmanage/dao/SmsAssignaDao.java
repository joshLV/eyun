package com.rainsoft.union.web.smsmanage.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Objects;

/**
 * 短信分配
 * Created by sun on 2016/6/23.
 */
public interface SmsAssignaDao {
    /**
     * 查询当前会员的未分配短信余额
     * @param userId 会员ID
     * @return
     */
    Integer getMemberSMSbalance(Integer userId);
    /**
     * 更新会员未分配短信数量
     * map 参数
     * @return
     */
    Integer updateBuySMSNum(Map<String,Object> map);

    /**
     * 更新场所配短信数量
     * map 参数
     * @return
     */
    Integer updateSMSRemain(Map<String,Object> map);
    /**
     * 查询当前会员场所短信余额
     * map 参数
     * @return
     */
    Integer getSMSbalance(Map<String,Object> map);

    /**
     * 账户短信余额
     * @param memberId 会员ID
     * @return
     */
    Integer smsBalance(Integer memberId);


}
