package com.rainsoft.base.web.system.service.impl;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONArray;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.dao.IBaseRoleDao;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.model.ResourcePrivilege;
import com.rainsoft.base.web.system.service.IBaseResourceService;
import com.rainsoft.base.web.system.service.IBaseRoleService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baseRoleService")
public class BaseRoleServiceImpl extends MybatisBasePersitenceServiceImpl<BaseRole, String> implements IBaseRoleService {
	private static Logger logger = (Logger)LoggerFactory.getLogger(BaseRoleServiceImpl.class);
	@Resource
	private IBaseRoleDao baseRoleDao;
	@Resource
	private IBaseResourceService baseResourceService;

	@Override
	protected IMybatisPersitenceDao<BaseRole, String> getBaseDao() {

		return baseRoleDao;

	}

	/**
	 * 
	 * @Description: TODO
	 * @param @param baseRole
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月2日下午4:42:09
	 */
	@Override
	public Result saveRole(BaseRole baseRole, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================保存角色开始====================================");
		logger.info("处理结果含义resultType: 1:角色保存成功； 	-1：角色名重复；		-2：保存角色失败;		-3:分配默认资源权限失败");
		//角色名称是否存在
		Integer num = baseRoleDao.findCountByKeyId("checkRoleExist", baseRole);
		logger.info("角色名称是否存在返回：" + num);
		if(num == null) {
			//保存资源
			num = baseRoleDao.save(baseRole);
			logger.info("保存资源返回：" + num);
			if(num != null && num > 0 && baseRole.getId() != null && baseRole.getId() > 0) {
				//角色设置的默认权限
				String defaultPrivilege = baseRole.getPrivilege();
				//查出创建角色者的菜单及权限
				List<Map<String, Object>> list = baseRoleDao.selectListMap("getMenuPrivilege", baseRole);
				//角色的资源权限list
				List<ResourcePrivilege> privilegeList = new ArrayList<ResourcePrivilege>();
				//角色资源实例
				ResourcePrivilege resourcePrivilege = new ResourcePrivilege();
				//用来存放资源权限
				StringBuffer privilege = null;
				//系统资源权限
				String resPrivilege = "";
				//系统资源的单个权限
				String resCode = "";
				//角色设置的默认单个权限
				String defaultCode = "";
				for(Map<String, Object> map : list) {
					//每设置一个资源权限初始化权限变量
					privilege = new StringBuffer("");
					resourcePrivilege = new ResourcePrivilege();
					//系统资源权限（oracle的key为大写，mysql的key（若有别名key与别名一致（大小写），否则与sql中的字段一致（大小写）））
					resPrivilege = map.get("RESPRIVILEGE").toString();
					if(resPrivilege != null && !"#".equals(resPrivilege)) {
						for(int i = 0; i < resPrivilege.length(); i++) {
							resCode = resPrivilege.substring(i, i + 1);
							defaultCode = defaultPrivilege.substring(i, i + 1);
							if(resCode.equals(defaultCode)) {
								privilege.append(resCode);
							} else if("Y".equals(resCode)) {
								privilege.append(defaultCode);
							} else {
								privilege.append(resCode);
							}
						}
						
					}
					resourcePrivilege.setId(Integer.valueOf(map.get("ID").toString()));
					resourcePrivilege.setPrivilege(privilege.toString());
					privilegeList.add(resourcePrivilege);
				}
				baseRole.setList(privilegeList);
				//分配默认资源权限失败
				num = baseRoleDao.save("saveAuthority", baseRole);
				logger.info("分配默认资源权限返回：" + num);
				if(num != null && num >= 0) {
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
		logger.info("保存角色处理结果resultType：" + resultType);
		logger.info("====================================保存角色结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.save"));
		} else if(resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.rolename"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
		}
		
//		baseRoleDao.save(baseRole);
//		if (baseRole.getId() > 0) {
//			result.setStatus(Constants.RETURN_SUCCESS);
////			result.setMessage("保存成功！");
//			result.setMessage(requestContext.getMessage("info.save"));
//		} else {
//			result.setStatus(Constants.RETURN_ERROR);
//			if (baseRole.getId() == -2) {
////				result.setMessage("角色名已存在！");
//				result.setMessage(requestContext.getMessage("errors.system.rolename"));
//			} else if (baseRole.getId() == -3) {
////				result.setMessage("角色初始化！");
//				result.setMessage(requestContext.getMessage("errors.system.roleinitialization"));
//			} else {
////				result.setMessage("操作失败！");
//				result.setMessage(requestContext.getMessage("errors.opt.fail"));
//			}
//		}
		return result;
	}

	/**
	 * 
	 * @Description: TODO
	 * @param @param baseRole
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月2日下午4:42:01
	 */
	@Override
	public Result updateRole(BaseRole baseRole, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================修改角色开始====================================");
		logger.info("处理结果含义resultType: 1:角色修改成功； 	-1：角色已更新请刷新；		-2：角色名重复；		-3：角色修改失败			-4:该角色在使用中");
		BaseRole baseRoleOld = baseRoleDao.findById(baseRole.getId().toString());
		if(baseRole.equals(baseRoleOld)) {
			//角色名称是否存在
			Integer num = baseRoleDao.findCountByKeyId("checkRoleExist", baseRole);
			logger.info("角色名称是否存在返回：" + num);
			if(num == null) {
				if(!baseRoleOld.getStatus().equals(baseRole.getStatus())) {
					if("d".equals(baseRole.getStatus())) {
						num = baseRoleDao.findCountByKeyId("checkOperateUserByRoleId", baseRole);
						if(num != null) {
							resultType = -4;
						}
					}
				}
				if(resultType == null) {
					//更新角色
					num = baseRoleDao.update(baseRole);
					logger.info("更新角色返回：" + num);
					if(num != null && num > 0) {
						resultType = 1;
					} else {
						resultType = -3;
					}
				}
			} else {
				resultType = -2;
			}
//			
//			baseRoleDao.update(baseRole);
//			if (baseRole.getId() == 1) {
//				result.setStatus(Constants.RETURN_SUCCESS);
////				result.setMessage("修改成功！");
//				result.setMessage(requestContext.getMessage("info.edit.success"));
//			} else {
//				result.setStatus(Constants.RETURN_ERROR);
//				if (baseRole.getId() == -2) {
////					result.setMessage("角色名已存在！");
//					result.setMessage(requestContext.getMessage("errors.system.rolename"));
//				} else if (baseRole.getId() == -3) {
////					result.setMessage("原角色不存在！");
//					result.setMessage(requestContext.getMessage("errors.system.roleNodata"));
//				} else {
////					result.setMessage("操作失败！");
//					result.setMessage(requestContext.getMessage("errors.opt.fail"));
//				}
//			}
		} else {
			resultType = -1;
		}
		logger.info("修改角色处理结果resultType：" + resultType);
		logger.info("====================================修改角色结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.edit.success"));
		} else if(resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.delerror"));
		} else if(resultType == -2) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.rolename"));
		} else if(resultType == -4) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.roleUsed"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
		}
		return result;
	}

	/**
	 * 删除角色
	 * 
	 * @Description: TODO
	 * @param @param baseRole
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月3日下午4:30:47
	 */
	@Override
	public Result delRole(BaseRole baseRole, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================删除角色开始====================================");
		logger.info("处理结果含义resultType: 1:分配资源成功； 	-1:数据已被更新，请刷新；		-2：角色是否被使用；	-3:删除角色失败；	-4：删除角色资源失败");
		List<BaseRole> baseRoleList = JSONArray.parseArray(baseRole.getSearchValue(), BaseRole.class);
		String[] array = new String[baseRoleList.size()];
		for (int i = 0; i < baseRoleList.size(); i++) {
			array[i] = baseRoleList.get(i).getId().toString();
		}
		List<BaseRole> baseRoleOldList = baseRoleDao.selectList("findListByIds", array);
		boolean flag = false;//false：数据未被修改，true：数据已修改
		if (baseRoleOldList != null) {
			for (BaseRole br : baseRoleList) {
				//------------------------------------------
				//@author qianna
				//解决生产环境上前端传递searchValue 值太复杂，导致ajax请求返回461()
				//------------------------------------------
				/*if (!baseRoleOldList.contains(br)) {
					flag = true;
					break;
				}*/
				for(BaseRole oldOne : baseRoleOldList){
					if(!br.getId().equals(oldOne.getId())){
						flag = true;
						break;
					}
				}
				if(flag){
					break;
				}
			}
		} else {
			flag = true;
		}
		if(!flag) {
			//角色是否被使用
			Integer num = baseRoleDao.findCountByKeyId("checkRoleUse", array);
			logger.info("角色是否被使用返回：" + num);
			if(num == null) {
				//删除角色
				num = baseRoleDao.deleteBy("delRole", array);
				logger.info("删除角色返回：" + num);
				if(num != null && num > 0) {
					//删除角色资源
					num = baseRoleDao.deleteBy("deleteAuthority", array);
					logger.info("删除角色资源返回：" + num);
					if(num != null && num >= 0) {
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
		
		logger.info("删除角色处理结果resultType：" + resultType);
		logger.info("====================================删除角色结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.del"));
		} else if(resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.delerror"));
		} else if(resultType == -2) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.roleused"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
		}
		return result;
	}

	@Override
	public List<BaseResource> getResource() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", SpringMvcUtil.getUserId());
		return baseResourceService.selectList("getResourceByUserId", map);
	}

	@Override
	public Result updateAuthority(BaseRole baseRole, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================分配资源开始====================================");
		logger.info("处理结果含义resultType: 1:分配资源成功； 	-1：删除先前资源；		-2：分配资源失败");
		//删除角色现有资源
		String[] array = new String[]{baseRole.getId().toString()};
		Integer num = baseRoleDao.update("deleteAuthority", array);
		logger.info("删除角色现有资源返回：" + num);
		if(num != null && num >= 0) {
			String valueStr = baseRole.getSearchValue();
			String[] valueArr = valueStr.split(",");
			List<ResourcePrivilege> list = new ArrayList<ResourcePrivilege>();
			ResourcePrivilege rp = null;
			for (String value : valueArr) {
				Integer id = Integer.valueOf(value.substring(0, value.lastIndexOf("_")));
				//-1是一级菜单的parent_id，资源表中没有，插入关联表中时去掉
				if(id == -1) {
					continue;
				}
				rp = new ResourcePrivilege();
				rp.setId(id);
				rp.setPrivilege(value.substring(value.lastIndexOf("_") + 1));
				list.add(rp);
			}
			baseRole.setList(list);
			//分配角色资源
			num = baseRoleDao.update("saveAuthority", baseRole);
			logger.info("分配角色资源返回：" + num);
			if(num != null && num > 0) {
				resultType = 1;
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		logger.info("分配资源处理结果resultType：" + resultType);
		logger.info("====================================分配资源结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.system.roleallocation"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.roleallocation"));
		}
		return result;
	}
}
