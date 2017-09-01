package com.rainsoft.base.web.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.support.RequestContext;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.model.OperateUser;

public interface IOperateUserService extends IMybatisBasePersitenceService<OperateUser, String> {
	/**
	 * 
	 * @Description: 保存操作用户
	 * @param @param operateUser
	 * @return void
	 * @throws
	 * @author yty
	 * @date 2015年12月7日下午12:00:39
	 */
	public Result saveOperateUser(OperateUser operateUser, RequestContext requestContext) throws Exception;

	/**
	 * 
	 * @Description: 更新操作用户
	 * @param @param operateUser
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月8日上午11:12:38
	 */
	public Result updateOperateUser(OperateUser operateUser, RequestContext requestContext) throws Exception;

	/**
	 * 
	 * @Description: 删除操作用户
	 * @param @param operateUser
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月8日上午11:58:22
	 */
	public Result deleteOperate(OperateUser operateUser, RequestContext requestContext) throws Exception;

	/**
	 * 
	 * @Description: 获取该用户下的所有角色
	 * @param @param userid
	 * @param @return
	 * @return List<BaseRole>
	 * @throws
	 * @author yty
	 * @date 2015年12月8日下午3:12:23
	 */
	public List<BaseRole> getRoleListByUserId(int userid);
	
	/**
	 * 
	 * @Description: TODO
	 * @param @param userId
	 * @param @return   
	 * @return List<BaseCompany>  
	 * @throws
	 * @author yty
	 * @date 2016年2月26日上午9:46:32
	 */
	public List<BaseCompany> getPlaceList(Map<String, Object> map);
	
	/**
	 * 
	 * @Description: 获取创建者的角色
	 * @param @param id
	 * @param @return   
	 * @return BaseRole  
	 * @throws
	 * @author yty
	 * @date 2016年5月10日下午3:43:17
	 */
	public BaseRole getRoleByUserId(Integer id);
}
