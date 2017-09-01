package com.rainsoft.union.web.sysmanage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.qos.logback.classic.Logger;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.dao.IAccountManageDao;
import com.rainsoft.union.web.sysmanage.model.AccountManage;
import com.rainsoft.union.web.sysmanage.service.IAccountManageService;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 账号管理
 *
 */
@Service("accountManageService")
public class AccountManageServiceImpl extends MybatisBasePersitenceServiceImpl<AccountManage, String> implements IAccountManageService{
	private static Logger logger = (Logger)LoggerFactory.getLogger(AccountManageServiceImpl.class);
	
	@Resource
	private IAccountManageDao accountManageDao;
	
	@Override
	protected IMybatisPersitenceDao<AccountManage, String> getBaseDao() {
		return accountManageDao;
	}
	
	/**
	 * 方法功能说明：根据当前账户id解绑账号
	 * @param accountManage userId会员id
	 * @return
	 */
//	@Override
//	public Result updateUnbindingAccount(AccountManage accountManage, HttpServletRequest request) throws Exception{
//		RequestContext requestContext =new RequestContext(request);
//		Result result = new Result();
//		Integer count = null;
//		try {
//			//解绑
//			count = accountManageDao.update("delBindAccount",accountManage);
//			if(count != null && count > 0){
//				//获取当前绑定账号的userId ，placeCode
//				//AccountManage aManage =  accountManageDao.findBy("getUserIdPlaceCode", accountManage);
//				//aManage.setUserId(SpringMvcUtil.getUserId());//设置关联用户的id为子账户id删除子账号的场所
//				//if(aManage != null){
//					//设置主账号id
//					accountManage.setUserId(SpringMvcUtil.getUserId());
//					//解绑时根据主账号Id 获取子账号对应的所有场所   并解除关联关系
//					List<AccountManage> aManageList=  accountManageDao.selectList("findCodeIds", accountManage);
//					String[] array = new String[aManageList.size()];
//					//临时存放placeCode的数组
//					for (int i = 0; i < aManageList.size(); i++) {
//						array[i] = aManageList.get(i).getPlaceCode().toString();
//					}
//					Map<String, Object> map = new HashMap<String, Object>();  
//				    map.put("userId", SpringMvcUtil.getUserId());  
//				    map.put("array", array); 
//					//解绑时删除 场所用户的关联关系
//					count = accountManageDao.deleteBy("deleteBindPlaceCode", map);
//					logger.info("解除关联场所返回的结果：" + count);
//				}
//				if(count != null && count > 0){
//					result.setStatus(Constants.RETURN_SUCCESS);
//					result.setMessage(requestContext.getMessage("解绑成功！"));
//				//}
//			} else {
//				result.setStatus(Constants.RETURN_ERROR);
//				result.setMessage(requestContext.getMessage("解绑失败！"));
//			}
//		} catch (Exception e) {
//			logger.error("解绑失败："+ e.getMessage());
//			throw new Exception("解绑失败！");
//		}
//		return result;
//	}
	
	/**
	 * 检查账号绑定前提条件
	 * 返回结果说明：
	 * 1、绑定成功；-1 -2、账号密码不匹配；-3、此账号已被绑定；-4、不能绑定自己；-5、绑定失败；0：check通过
	 * @param accountManage 账户管理实体
	 * @param requestContext
	 * @return  Result
	 * @throws Exception
	 */
	private Result checkBind(AccountManage accountManage, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		// 条件检查状态值
		Integer rs = null;
		Integer count = null;
		// 根据账号和密码得到绑定的实例
		AccountManage acManage = accountManageDao.findBy("getBindAccount", accountManage);
		if (acManage == null || acManage.getId() < 0) {
			rs = -1; // 账号或密码错误
		} else {
			Integer parentUserId = SpringMvcUtil.getUserId();
			if (acManage.getId().equals(parentUserId)) {
				rs = -2;// 不能绑定自己
			} else {
				// 获取父账号的placeCode列表
				List<AccountManage> manageParentList = accountManageDao.selectList("getParentUserIdCodes", accountManage);
				// 获取子账号的placeCode列表
				List<AccountManage> manageBindList = accountManageDao.selectList("getCurrentUserIdCodes", acManage);
				boolean flag = false;// false：未有父场所，true有父场所
				if (manageBindList != null) {
					for (AccountManage aManage : manageParentList) {
						if (manageBindList.contains(aManage)) {
							flag = true;
							break;
						}
					}
				} else {
					flag = true;
				}
				if (!flag) {
					if (acManage != null && acManage.getId() > 0) {
						logger.info("检查账号密码是否匹配返回记录：" + acManage);
						// 检查子账号是否已经绑定过
						count = accountManageDao.findCountByKeyId("checkIsNotBind", acManage);
						logger.info("检查子账号是否已被绑定：" + count);
						if (count == null || count <= 0) {
							// 检查是否已经绑定过其它账号成为父账号
							count = accountManageDao.findCountByKeyId("checkSelfByOtherBind", acManage);
							logger.info("检查账号是否已被绑定：" + count);
							if (count == null || count <= 0) {
								rs = 0;// 检查通过
							} else {
								rs = -4;// 该账号绑定了其它账号
							}
						} else {
							rs = -3;// 此账号已被绑定
						}
					}
				} else {
					rs = -5;
				}
			}
		}
		if (rs != null) {
			result.setCount(rs);
			if (rs == -1) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("账号或密码错误！"));
			} else if (rs == -2) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("不能绑定自己！"));
			} else if (rs == -3) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("此账号已被绑定！"));
			} else if (rs == -4) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("该账号绑定过其它账号！"));
			} else if (rs == -5) {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("子账号不能拥有主账号的场所！"));
			}
		}
		return result;
	}
	/**
	 * 方法功能说明：绑定账号
	 * @param accountManage 当前账号id userId
	 * @param 绑定账号id currentMemberId
	 * @param 绑定账号密码 currentPassword（MD5加密32位字符）
	 * @return
	 */
	@Override
	public Result updateBindingAccount(AccountManage accountManage, HttpServletRequest request) throws Exception{
		RequestContext requestContext = new RequestContext(request);
		Integer count = null;
		Result result = null;
		logger.info("rs 结果返回的说明： 0:检查通过； 1：绑定成功； -1：账号或密码错误； -2：不能绑定自己； -3：此账号已被绑定；-4：该账号绑定过其它账号；-5：子账号不能拥有主账号的场所；");
		//绑定前的check操作
		result = checkBind(accountManage, request);
		if(result != null && result.getCount() < 0) {
			return result;
		}
		//绑定子账号
		count = accountManageDao.update("bindingAccount", accountManage);
		if(count != null && count > 0){
			// 根据账号和密码得到绑定的实例
			AccountManage acManage = accountManageDao.findBy("getBindAccount", accountManage);
			//根据获取子账号Id获取该账号对应的所有场所
			List<AccountManage> aManageList=  accountManageDao.selectList("findCodeIds", acManage);
			String[] array = new String[aManageList.size()];
			//临时存放placeCode的数组
			for (int i = 0; i < aManageList.size(); i++) {
				array[i] = aManageList.get(i).getPlaceCode().toString();
			}
			//保存用户场所
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", SpringMvcUtil.getUserId());
			map.put("creator", SpringMvcUtil.getUserId());
			map.put("updator", SpringMvcUtil.getUserId());
			map.put("array", array);
			//向场所用户关联表添加绑定记录
			count = accountManageDao.save("addRelated", map);
			if(count !=null && count > 0){
				result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage(requestContext.getMessage("绑定成功!"));
			}
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("绑定失败!"));
		}
		return result;
	}
}
