package com.rainsoft.union.web.account.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.bean.BeanMapUtil;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.dao.IWwbDetailDao;
import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.account.model.WwbBuyRecord;
import com.rainsoft.union.web.account.model.WwbUseRecord;
import com.rainsoft.union.web.account.service.IWwbDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author huxiaolong
 * 旺旺币账户信息查询
 */
@Service("wwbDetailService")
public class WwbDetailServiceImpl implements IWwbDetailService{
	
	@Resource
	private IWwbDetailDao iWwbDetailDao;

	/**
	 * 旺旺币购买记录列表
	 */
	@Override
	public JSONObject getBuyWwbRecord(WwbAccount wwbAccount, PageBean page) throws Exception {
		List<WwbBuyRecord> list = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = BeanMapUtil.toMap(wwbAccount);
        paramMap.put("SORT", page.getSort());
        paramMap.put("DIR", page.getOrder());
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = iWwbDetailDao.getBuyWwbRecord(paramMap);
            for (WwbBuyRecord wwbBuyRecord : list) {
                    if (wwbBuyRecord.getStatus().equals("Y") || "8".equals(wwbBuyRecord.getStatus())) {
                            wwbBuyRecord.setStatus("已到账");
                    }else{
                            wwbBuyRecord.setStatus("未到账");
                    }
                    if (wwbBuyRecord.getPayChannel().equals("2")) {
                            wwbBuyRecord.setPayChannel("余额支付");
                    }else if (wwbBuyRecord.getPayChannel().equals("4")) {
                            wwbBuyRecord.setPayChannel("现金支付");

                    }else if (wwbBuyRecord.getPayChannel().equals("5")) {
                            wwbBuyRecord.setPayChannel("旺旺狗同步");

                    }else if (wwbBuyRecord.getPayChannel().equals("6")) {
                            wwbBuyRecord.setPayChannel("易宝支付");

                    }else if (wwbBuyRecord.getPayChannel().equals("7")) {
                            wwbBuyRecord.setPayChannel("支付宝支付");

                    }else if (wwbBuyRecord.getPayChannel().equals("8")) {
                            wwbBuyRecord.setPayChannel("微信支付");

                    }else if (wwbBuyRecord.getPayChannel().equals("9")) {
                            wwbBuyRecord.setPayChannel("云辰赠送");

                    }
            }
        page.setTotal(((Page<WwbBuyRecord>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());          
        jsonObject.put("totalPages", page.getPageCount());     
        jsonObject.put("totalRecords", page.getTotal());       
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
	}

	/**
	 * 旺旺币消费记录
	 */
	@Override
	public JSONObject getWwbUseRecord(WwbAccount wwbAccount, PageBean page) throws Exception {
		List<WwbUseRecord> list = null;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = BeanMapUtil.toMap(wwbAccount);
        paramMap.put("SORT", page.getSort());
        paramMap.put("DIR", page.getOrder());
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = iWwbDetailDao.getWwbUseRecord(paramMap);
        page.setTotal(((Page<WwbUseRecord>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());     
        jsonObject.put("totalPages", page.getPageCount());     
        jsonObject.put("totalRecords", page.getTotal()); 
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
	}
	/**
	 * 旺旺币消费详细记录
	 */
	@Override
	public List<WwbUseRecord> getWwbUseRecordDetail(WwbAccount wwbAccount) {
            List<WwbUseRecord> list = iWwbDetailDao.getWwbUseRecordDetail(wwbAccount);
            //消费卡类型:1：临时卡；2：'固定时长'; 3：'固定时段';4：'有限制固定时长';5：'按金额计费';6：'按时间计费';7：'包时间';8：'连锁计费';
            for (WwbUseRecord wwbUseRecord : list) {
                    switch (wwbUseRecord.getCardType()) {
                            case "1" : wwbUseRecord.setCardType("临时卡");
                                    break;
                            case "2" : wwbUseRecord.setCardType("固定时长");
                                    break;
                            case "3" : wwbUseRecord.setCardType("固定时段");
                                    break;
                            case "4" : wwbUseRecord.setCardType("有限制固定时长");
                                    break;
                            case "5" : wwbUseRecord.setCardType("按金额计费");
                                    break;
                            case "6" : wwbUseRecord.setCardType("按时间计费");
                                    break;
                            case "7" : wwbUseRecord.setCardType("包时间");
                                    break;
                            case "8" : wwbUseRecord.setCardType("连锁计费");
                                    break;
                            default  : wwbUseRecord.setCardType("其他");
                                    break;
                    }
            }
            return list;
    }
}