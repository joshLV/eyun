package com.rainsoft.union.web.smsmanage.service.impl;

import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.smsmanage.dao.SmsAssignaDao;
import com.rainsoft.union.web.smsmanage.service.SmsAssignaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场所分配短信
 * Created by sun on 2016/6/23.
 */
@Service
public class SmsAssignaServiceImpl implements SmsAssignaService{

    @Resource
    private SmsAssignaDao smsAssignaDao;

    /**
     * 查询当前会员的未分配短信余额
     * @param userId 会员ID
     * @return
     * @throws Exception
     */
    @Override
    public Integer getMemberSMSbalance(Integer userId) throws Exception{

        return smsAssignaDao.getMemberSMSbalance(userId);
    }

    /**
     * 短信分配列表
     * @param userId 会员ID
     * @param placeDataList
     * @return
     * @throws Exception
     */
    @Override
    public Integer getMemberSMSbalance(Integer userId, List<PlaceData> placeDataList) throws Exception {
        if (placeDataList != null && placeDataList.size() > 0) {
            for (PlaceData data : placeDataList) {
                Map<String, Object> map = new HashMap<>();
                map.put("memberID", userId);
                map.put("placeID", data.getId());
                int result = 0;
                try {
                    result = smsAssignaDao.getSMSbalance(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                data.setUserId(result); // 数据临时放这个字段里
            }
        }
        return smsAssignaDao.getMemberSMSbalance(userId);
    }

    /**
     * 更新会员未分配短信数量
     * @param memberID 会员ID
     * @param number 短信数量
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateBuySMSNum(Integer memberID,Integer number) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("memberID", memberID);
        map.put("theNumber", number);
        return smsAssignaDao.updateBuySMSNum(map);
    }

    /**
     * 更新场所配短信数量
     * @param placeID 场所ID
     * @param number 短信数量
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateSMSRemain(Integer placeID,Integer number) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("placeID", placeID);
        map.put("theNumber", number);
        return smsAssignaDao.updateBuySMSNum(map);
    }

    /**
     * 查询当前会员场所短信余额
     * @param placeID 场所ID
     * @param userId 会员ID
     * @return
     * @throws Exception
     */
    @Override
    public Integer getSMSbalance(Integer placeID, Integer userId) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("memberID", userId);
        map.put("placeID", placeID);
        return smsAssignaDao.getSMSbalance(map);
    }

    /**
     * 账户短信余额
     * @param memberId 会员ID
     * @return
     * @throws Exception
     */
    @Override
    public Integer getSMSbalance(Integer memberId) throws Exception{
        return smsAssignaDao.smsBalance(memberId);
    }

    /**
     * 分配场所短信
     * @param userId        会员iD
     * @param allPlaceValue 场所分配参数
     * @return
     * @throws Exception
     */
    @Override
    public String smsTransferSetup(Integer userId, String allPlaceValue) throws Exception {
        int allSMS = smsAssignaDao.smsBalance(userId); // 会员所有的短信数
        // 移除所有#号 获取对应的placeId
        String[] placeValues = allPlaceValue.replaceAll("#","").split(",");
        String msg = null;
        for (int i = 0; i < placeValues.length; i += 2) {
            try {
                int temp = Integer.parseInt(placeValues[i]);
                if (temp < 0) {
                    // 数字格式有问题
                    msg = "2";
                    return msg;
                }
                allSMS = allSMS - temp;
            } catch (NumberFormatException e) {
                // 数字格式有问题
                msg = "2";
                return msg;
            }
        }
        if (allSMS != 0) {
            // 短信总数不正确
            msg = "1";
        } else {
            for (int i = 0; i < placeValues.length; i += 2) {
                try {
                    Map<String, Object> map = new HashMap<>();
                    if (i == 0) {
                        map.put("memberID", userId);
                        map.put("theNumber", Integer.parseInt(placeValues[i]));
                        smsAssignaDao.updateBuySMSNum(map);
                    } else {
                        map.clear();
                        map.put("placeID", Integer.parseInt(placeValues[i - 1]));
                        map.put("theNumber", Integer.parseInt(placeValues[i]));
                        smsAssignaDao.updateSMSRemain(map);
                    }
                    msg = "0";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return msg;
    }
}
