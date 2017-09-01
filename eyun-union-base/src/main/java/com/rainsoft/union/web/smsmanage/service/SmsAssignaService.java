package com.rainsoft.union.web.smsmanage.service;

import com.rainsoft.union.web.pubdata.model.PlaceData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 短信分配
 * Created by sun on 2016/6/23.
 */
public interface SmsAssignaService {
    /**
     * 查询当前会员的未分配短信余额
     * @param userId 会员ID
     * @return
     */
    Integer getMemberSMSbalance(Integer userId)throws Exception;

    /**
     * 短信分配列表
     * @param userId 会员ID
     * @param list 场所列表
     * @return 返回短信余额
     * @throws Exception
     */
    Integer getMemberSMSbalance(Integer userId,List<PlaceData> list)throws Exception;

    /**
     * 更新会员未分配短信数量
     * @param memberID 会员ID
     * @param number 短信数量
     * @return
     */
    Integer updateBuySMSNum(Integer memberID,Integer number)throws Exception;

    /**
     * 更新场所配短信数量
     * @param placeID 场所ID
     * @param number 短信数量
     * @return
     */
    Integer updateSMSRemain(Integer placeID, Integer number)throws Exception;

    /**
     * 查询当前会员场所短信余额
     *
     * @param placeID 场所ID
     * @param userId 会员ID
     * @return
     */
    Integer getSMSbalance(Integer placeID,Integer userId)throws Exception;

    /**
     * 账户短信余额
     * @param memberId 会员ID
     * @return
     */
    Integer getSMSbalance(Integer memberId)throws Exception;

    /**
     * 分配场所短信
     * @param userId        会员iD
     * @param allPlaceValue 场所分配参数
     * @return
     * @throws Exception
     */
    String smsTransferSetup(Integer userId,String allPlaceValue) throws Exception;
}
