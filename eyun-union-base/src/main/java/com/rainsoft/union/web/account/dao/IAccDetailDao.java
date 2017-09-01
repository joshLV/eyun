/**
 * @Description:
 * @author:王乾
 * @date:2016年4月18日下午12:58:22
 */
package com.rainsoft.union.web.account.dao;
import java.util.List;
import java.util.Map;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.union.web.account.model.AccDetail;
import com.rainsoft.union.web.account.model.AccUseRecord;

public interface IAccDetailDao extends IMybatisPersitenceDao<AccDetail, String> {
	
    /** 
     * 账户支出记录查询
     * @param map
     * @return
     */
    public List<AccUseRecord> findAccUseRecord(Map<String, Object> map);
    
}
