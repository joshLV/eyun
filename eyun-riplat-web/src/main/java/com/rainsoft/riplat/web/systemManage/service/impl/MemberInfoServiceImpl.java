package com.rainsoft.riplat.web.systemManage.service.impl;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.dao.IMemberInfoDao;
import com.rainsoft.riplat.web.systemManage.model.MemberInfo;
import com.rainsoft.riplat.web.systemManage.service.IMemberInfoService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 个人信息
 *
 */
@Service("memberInfoService")
public class MemberInfoServiceImpl extends MybatisBasePersitenceServiceImpl<MemberInfo, String> implements IMemberInfoService{
	private static Logger logger = (Logger)LoggerFactory.getLogger(MemberInfoServiceImpl.class);

	@Resource
	private IMemberInfoDao memberInfoDao;
	
	@Override
	protected IMybatisPersitenceDao<MemberInfo, String> getBaseDao() {
		return memberInfoDao;
	}

	/**
	 * 方法功能说明：密码设置
	 * @param MemberInfo 会员信息实体类
	 * @param userId 当前会员id
	 * @param newUserPassword 用户设置密码
	 * @param currentPassword 用户当前的密码
	 * @return
	 */
	public Result updateMemberPwd(MemberInfo memberInfo, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		//首先判断用户输入当前密码是否正确
		Integer count = memberInfoDao.findCountByKeyId("chcekCurrentPwd",memberInfo);
		logger.info("检查用户输入的当前密码是否正确返回记录："+ count);
		if (count == 1) {//密码正确，继续操作
			count = memberInfoDao.update("updatePwd", memberInfo);
			if (count != null && count > 0) {
				result.setMessage(requestContext.getMessage("修改成功！"));
				result.setStatus(Constants.RETURN_SUCCESS);
			} else {
				result.setMessage(requestContext.getMessage("修改失败！"));
				result.setStatus(Constants.RETURN_ERROR);
			}
		} else {
			result.setMessage(requestContext.getMessage("当前密码错误！"));
			result.setStatus(Constants.RETURN_ERROR);
		}
		return result;
	}

	/**
	 * 方法功能说明：会员信息
	 * @param userId 当前会员id
	 * @return MemberInfo 会员信息实体类
	 */
	public Result findMemberInfoById(String UserId, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		JSONObject json = new JSONObject();
		MemberInfo memberInfo = memberInfoDao.findById(UserId);
		if(memberInfo!=null){
			json.put("memberInfo", memberInfo);
			result.setMessage(requestContext.getMessage("获取数据成功！"));
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setData(JSONObject.toJSONString(memberInfo));
		}else{
			result.setMessage(requestContext.getMessage("获取数据失败！"));
			result.setStatus(Constants.RETURN_ERROR);
		}
		return result;
	}

	/**
	 * 方法功能说明：修改会员个人信息
	 * @param MemberInfo 会员信息实体类
	 * @return 
	 */
	public Result updateMemberInfo(MemberInfo memberInfo, HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result =  new Result();
		Integer count = memberInfoDao.update(memberInfo);
		if(count != null && count > 0){
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("修改成功！"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("修改失败！"));
		}
		return result;
	}
}
