package com.rainsoft.union.web.sysmanage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.SurfWebSet;
import javax.servlet.http.HttpServletRequest;

/**
 *上网设置 
 *
 */
public interface ISurfWebSetService extends IMybatisBasePersitenceService<SurfWebSet, String> {
	/**
	 * 
	 * 更新上网设置
	 * @param  surfWebSet 上网设置实体
	 * @return Result  
	 * @throws Exception
	 */
	public Result updateSurfWebSet(SurfWebSet surfWebSet, HttpServletRequest request) throws Exception;
}
