package com.rainsoft.union.web.account.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.account.dao.IAccDetailDao;
import com.rainsoft.union.web.account.model.AccDetail;
import com.rainsoft.union.web.account.model.AccUseRecord;


@Repository("accDetailDao")
public class AccDetailDaoImpl extends MybatisPersitenceDaoImpl<AccDetail, String> implements IAccDetailDao {

	 /**
	  * 旺旺币支出记录查询
	  * @return list
	  */
	public List<AccUseRecord> findAccUseRecord(Map<String, Object> map) {
		
		return this.getSqlSession().selectList("getAccUseRecord", map);
	}
	
}
