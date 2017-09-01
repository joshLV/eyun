/**短信管理实现类
 * @Description:
 * @author:王乾
 * @date:2016年4月19日下午4:10:38
 */
package com.rainsoft.union.web.account.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.bean.BeanMapUtil;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.dao.IAccDetailDao;
import com.rainsoft.union.web.account.model.AccDetail;
import com.rainsoft.union.web.account.model.AccUseRecord;
import com.rainsoft.union.web.account.service.IAccDetailService;


@Service("accDetailService")
public class AccDetailServiceImpl extends MybatisBasePersitenceServiceImpl<AccDetail, String> implements IAccDetailService {
    
	
	@Resource
	private IAccDetailDao accDetailDao;
	
	@Override
	protected IMybatisPersitenceDao<AccDetail, String> getBaseDao() {
		return accDetailDao;
	}
	/**
	 * 获取账户支出记录列表
	 * @param wwb Entity
	 * @param page PageBean
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject getAccUseRecord(AccUseRecord wwb, PageBean page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isEmpty(wwb.getStatus())){
			wwb.setStatus("Y");
		}
		if(wwb.getPlaceID() < 1){
			wwb.setPlaceID(-1);
		}
		map = BeanMapUtil.toMap(wwb);
		map.put("SORT", page.getSort());
		map.put("DIR", page.getOrder());
		PageHelper.startPage(page.getCurrPage(), page.getPageSize());
		List<AccUseRecord> list=accDetailDao.findAccUseRecord(map);
		page.setTotal(((Page<AccUseRecord>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());          // 当前页
        jsonObject.put("totalPages", page.getPageCount());      // 总页数
        jsonObject.put("totalRecords", page.getTotal());        // 总记录数
        // 页面显示记录数设置
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
	}

}