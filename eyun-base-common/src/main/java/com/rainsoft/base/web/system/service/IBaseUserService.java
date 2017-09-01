package com.rainsoft.base.web.system.service;

import javax.servlet.http.HttpServletRequest;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseUser;

public interface IBaseUserService extends IMybatisBasePersitenceService<BaseUser, String> {

	/**
	 * 
	 * 方法功能说明：用户登录
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月23日 下午5:18:27
	 * @param user
	 * @return
	 */
	public Result login(BaseUser user, HttpServletRequest request);
	
	
	public BaseUser loginWithPlatform(BaseUser user);


    BaseUser loginWithInterface(BaseUser user);
}