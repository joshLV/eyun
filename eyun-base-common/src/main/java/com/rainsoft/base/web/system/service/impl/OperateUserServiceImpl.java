package com.rainsoft.base.web.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import ch.qos.logback.classic.Logger;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.dao.IOperateUserDao;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.model.OperateUser;
import com.rainsoft.base.web.system.service.IBaseCompanyService;
import com.rainsoft.base.web.system.service.IBaseRoleService;
import com.rainsoft.base.web.system.service.IOperateUserService;

@Service("operateUserService")
public class OperateUserServiceImpl extends MybatisBasePersitenceServiceImpl<OperateUser, String> implements IOperateUserService {
	private static Logger logger = (Logger)LoggerFactory.getLogger(OperateUserServiceImpl.class);
	@Resource
	private IOperateUserDao operateUserDao;
	@Resource
	private IBaseRoleService baseRoleService;
	@Resource
	private IBaseCompanyService baseCompanyService;

	@Override
	protected IMybatisPersitenceDao<OperateUser, String> getBaseDao() {
		return operateUserDao;
	}

	@Override
	public Result saveOperateUser(OperateUser operateUser, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================新增用户开始====================================");
		logger.info("处理结果含义resultType: 1:用户保存成功； 	-1：用户名重复；		-2：保存用户信息失败；		-3：保存用户角色失败；		-4：保存用户场所失败；");
		//检查名称是否存在
		Integer num = operateUserDao.findCountByKeyId("checkUserName", operateUser);
		logger.info("检查名称是否存在返回：" + num);
		if(num == null) {
			//保存用户
			operateUser.setMemberPwd(CommonUtil.getMd5(operateUser.getMemberPwd()));
			num = operateUserDao.save(operateUser);
			logger.info("保存用户返回：" + num);
			if(num != null && num > 0 && operateUser.getId() != null & operateUser.getId() > 0) {
				//保存用户角色
				num = operateUserDao.save("saveRole", operateUser);
				logger.info("保存用户角色返回：" + num);
				if(num != null && num > 0) {
					//保存用户场所
					String[] array = operateUser.getSearchValue().split(",");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", operateUser.getId());
					map.put("creator", operateUser.getCreator());
					map.put("updator", operateUser.getCreator());
					map.put("array", array);
					num = operateUserDao.save("savePlace", map);
					logger.info("保存用户场所返回：" + num);
					if(num != null && num > 0) {
						resultType = 1;
					} else {
						resultType = -4;
					}
				} else {
					resultType = -3;
				}
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		
		logger.info("新增用户处理结果resultType：" + resultType);
		logger.info("====================================新增用户结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.save"));
		} else if (resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.operateUserName"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
		}
		return result;
	}

	@Override
	public Result updateOperateUser(OperateUser operateUser, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================更新用户开始====================================");
		logger.info("处理结果含义resultType: 1:更新成功； 	-1：用户名重复；		-2：更新用户信息失败；		-3：更新用户角色失败；		-4：删除用户现有场所失败；		-5：保存用户新场所失败；");
		//检查名称是否存在
		Integer num = operateUserDao.findCountByKeyId("checkUserName", operateUser);
		logger.info("检查名称是否存在返回：" + num);
		if(num == null) {
			//更新用户信息
			operateUser.setMemberPwd(StringUtil.isNotEmpty(operateUser.getMemberPwd()) ? CommonUtil.getMd5(operateUser.getMemberPwd()) : null);
			num = operateUserDao.update(operateUser);
			logger.info("更新用户信息返回：" + num);
			if(num != null && num > 0) {
				//更新用户角色
				num = operateUserDao.update("updateRole", operateUser);
				logger.info("更新用户角色返回：" + num);
				if(num != null && num > 0) {
					//删除用户现有场所
					num = operateUserDao.update("deleteAllPlace", operateUser);
					logger.info("删除用户现有场所返回：" + num);
					if(num != null && num > 0) {
						//保存用户新场所
						String[] array = operateUser.getSearchValue().split(",");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("userId", operateUser.getId());
						map.put("creator", operateUser.getUpdator());
						map.put("updator", operateUser.getUpdator());
						map.put("array", array);
						num = operateUserDao.save("savePlace", map);
						logger.info("保存用户新场所返回：" + num);
						if(num != null && num > 0) {
							resultType = 1;
						} else{
							resultType = -5;
						}
					} else {
						resultType = -4;
					}
					
				} else {
					resultType = -3;
				}
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		logger.info("更新用户处理结果resultType：" + resultType);
		logger.info("====================================更新用户结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.edit.success"));
		} else if (resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.operateUserName"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
		}
		return result;
	}

	@Override
	public Result deleteOperate(OperateUser operateUser, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================删除用户开始====================================");
		logger.info("处理结果含义resultType: 1:删除成功； 	-1：删除用户信息失败；		-2：删除用户角色关联表关联数据失败；		-3：删除用户场所关联表数据失败！；");
		String[] array = operateUser.getSearchValue().split(",");
		//删除用户信息
		Integer num = operateUserDao.update("deleteByIds", array);
		logger.info("删除用户信息返回：" + num);
		if(num != null && num > 0) {
			//删除用户关联的角色数据
			num = operateUserDao.update("deleteRoleBatch", array);
			logger.info("删除用户关联的角色数据返回：" + num);
			if(num != null && num > 0) {
				//删除用户关联的场所数据
				num = operateUserDao.update("deletePlaceBatch", array);
				logger.info("删除用户关联的场所数据返回：" + num);
				if(num != null && num > 0) {
					resultType = 1;
				} else {
					resultType = -3;
				}
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		logger.info("删除用户处理结果resultType：" + resultType);
		logger.info("====================================删除用户结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.del"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
		}
		return result;
	}

	@Override
	public List<BaseRole> getRoleListByUserId(int userid) {
		List<BaseRole> roleList = null;
		try {
			roleList = baseRoleService.selectList("getRoleListByUserId", userid);
		} catch (Exception e) {
			roleList = new ArrayList<BaseRole>();
			logger.error("获取角色列表出错！" + e);
		}
		return roleList;
	}

	@Override
	public List<BaseCompany> getPlaceList(Map<String, Object> map) {
		List<BaseCompany> list = null;
		try {
			list = baseCompanyService.selectList("getPlaceList", map);
		} catch (Exception e) {
			list = new ArrayList<BaseCompany>();
			logger.error("获取场所列表出错！" + e);
		}
		return list;
	}

	@Override
	public BaseRole getRoleByUserId(Integer id) {
		BaseRole role = null;
		try {
			role = baseRoleService.findBy("getRoleByUserId", id);
		} catch (Exception e) {
			logger.error("获取创建者角色出错！" + e);
		}
		return role;
	}
}
