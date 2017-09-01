package com.rainsoft.base.web.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.dao.IBaseResourceDao;
import com.rainsoft.base.web.system.dao.IBaseRoleDao;
import com.rainsoft.base.web.system.dao.IBaseUserDao;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.model.BaseUser;
import com.rainsoft.base.web.system.service.IBaseUserService;

@Service("baseUserService")
public class BaseUserServiceImpl extends MybatisBasePersitenceServiceImpl<BaseUser, String> implements IBaseUserService {

	@Resource
	private IBaseUserDao baseUserDao;

	@Resource
	private IBaseResourceDao baseResourceDao;

	@Resource
	private IBaseRoleDao baseRoleDao;

	@Override
	protected IMybatisPersitenceDao<BaseUser, String> getBaseDao() {
		return baseUserDao;
	}

	@Override
	public Result login(BaseUser user,HttpServletRequest request) {
		Result result = new Result();
		user = baseUserDao.findBy("login", user);
		int id = 0;
		if (user != null) {
			id = user.getId();
		}
		if (id > 0) {
			// 用户角色取得
			BaseRole role = baseRoleDao.findBy("getRoleByUserId", id);

			// 角色资源取得
			List<BaseResource> resourceList = baseResourceDao.selectList("getResourceByRoleIds", role.getId());
			BaseResource.setAllResource(resourceList);
			if (null == BaseResource.allResource || BaseResource.allResource.size() <= 0) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(Constants.SYS_EXCEPTION);
				return result;
			}
			
			// 用户资源设置
			request.getSession().setAttribute("allResource", BaseResource.allResource);
			// 用户信息保存到session
			AuthenToken authenToken = new AuthenToken();
			authenToken.setAnotherName(user.getAnotherName());

			authenToken.setLoginTime(new Date());
			authenToken.setLoginName(user.getLoginName());
			authenToken.setUserId(user.getId());
			request.getSession().setAttribute("AuthenToken", authenToken);
			
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("登录成功！");
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("账号或密码错误，请重试！");
		}
		return result;
	}
	
	/**
	 * 接口调用需求需使用的查询用户方法
	 * @param user
	 * @return
	 */
	@Override
    public BaseUser loginWithInterface(BaseUser user) {
        user = baseUserDao.findBy("login", user);        
        return user;
    }

	@Override
	public BaseUser loginWithPlatform(BaseUser user) {
		String msg = "";
		BaseUser baseUser = new BaseUser();
		user = baseUserDao.findBy("login", user);
		if(user!=null){
			baseUser = user;
		}
		int id = 0;
		if(baseUser.getId()!=null){
			id = baseUser.getId();
		}
		if (id > 0) {
			// 用户角色取得
			BaseRole role = baseRoleDao.findBy("getRoleByUserId", id);

			if(role!=null){
			// 角色资源取得
				List<BaseResource> resourceList = baseResourceDao.selectList("getResourceByRoleIds", role.getId());
				BaseResource.setAllResource(resourceList);
				}
			if (null == BaseResource.allResource || BaseResource.allResource.size() <= 0) {
				msg = Constants.SYS_EXCEPTION;
				baseUser.setRemark(msg);
				return baseUser;
			}

			msg = Constants.RETURN_SUCCESS;
		} else if (id == 0) {
			msg = "账号或密码错误，请重试！";
		} else if (id == -1) {
			msg = "登录失败,您还可以尝试5次。";
		} else if (id == -2) {
			msg = "登录失败,您还可以尝试4次。";
		} else if (id == -3) {
			msg = "登录失败,您还可以尝试3次。";
		} else if (id == -4) {
			msg = "登录失败,您还可以尝试2次。";
		} else if (id == -5) {
			msg = "登录失败,您还可以尝试1次。";
		} else if (id == -8) {
			msg = "密码连续五次输入错误，请半个小时后再尝试登录。";
		} else if (id == -9) {
			msg = "操作失败，请重试！";
		}
		baseUser.setRemark(msg);
		return baseUser;
	}

}