package com.rainsoft.union.web.sysmanage.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.model.AuthMethodEnum;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.*;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.dao.IWhiteDao;
import com.rainsoft.union.web.sysmanage.model.ParamFilter;
import com.rainsoft.union.web.sysmanage.model.WhiteEntity;
import com.rainsoft.union.web.sysmanage.model.WhiteEnum;
import com.rainsoft.union.web.sysmanage.service.IWhiteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("whiteServiceImpl")
public class WhiteServiceImpl extends MybatisBasePersitenceServiceImpl implements IWhiteService{
	private Log logger = LogFactory.getLog(WhiteServiceImpl.class);
	@Resource
	private IWhiteDao whiteDaoImpl;
	private static String AUTH_OPTTYPE_ADD = "E";
	private static String AUTH_OPTTYPE_DEL = "D";
	private static String ENUMTYPE_DB_ADD = "DB_ADD";
	private static String ENUMTYPE_DB_DEL = "DB_DEL";
	private static String ENUMTYPE_AUTH = "AUTH";
	/**
	 * 获取白名单列表
	 * @param param 参数
	 * @param page 分页对象
	 * @return List<WhiteEntity>
	 */
	@Override
	public JSONObject getWhiteList(ParamFilter param, PageBean page) {
		List<WhiteEntity> list = null;
		//使用map作为参数传入dao层
		Map<String, Object> paramMap =  param.toMap();

		// 排序字段
		paramMap.put("SORT", page.getSort());
		// 排序顺序
		paramMap.put("DIR", page.getOrder());

		// mybatis 分页插件
		PageHelper.startPage(page.getCurrPage(), page.getPageSize());
		try {
			list = whiteDaoImpl.getWhiteList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setTotal(((Page<WhiteEntity>) list).getTotal());

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("curPage", page.getCurrPage()); // 当前页
		jsonObj.put("totalPages", page.getPageCount()); // 总页数
		jsonObj.put("totalRecords", page.getTotal()); // 总记录数
		// 页面显示记录数设置
		jsonObj.put("rowNum", page.getPageSize());
		jsonObj.put("dataRows", JSONArray.toJSON(list));
		return jsonObj;
	}
	/**
	 * 保存白名单列表
	 * 说明：保存白名单需要先调用认证中心“网吧场所易韵账户白名单接口”设置白名单，
	 * 		a.设置成功后，将数据保存到我们本地数据库
	 * 		b.设置失败不保存
	 * 		c.设置成功后，保存到本地失败，调用认证中心“网吧场所易韵账户白名单接口”接口，传入D，删除设置
	 * 修改：1. 先保存到本地，
	 * 		 2. 保存成功然后连同主键全部传递给认证中心，
	 * 		 3. 通知认证成功需要，更新本地数据库状态
	 * 		 4. 保存失败则给通知
	 * @param param {
	 * 		placeCode [场所编码]
	 * 	    ,surfUserType [上网账号类型]
	 *      ,eyunId  [易韵ID]
	 * 		,mobile [手机号]
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * @return { "status":'SUCCESS/ERROR'
	 * 			'msg':'成功'
	 * }
	 *
	 * 新白名单ID: 成功，用大于0判断
	 *    		-1: 操作失败;
	 *    		-2: 该账号已经存在;
	 *    		-9：认证中心操作失败
	 * }
	 */
	@Override
	public Result saveWhiteList(ParamFilter param,Integer memberId) throws Exception {
		logger.debug("添加白名单开始***************************");
		String placeCode = param.getPlaceCode();

		String memberName = param.getMemberName();
		String idCard = param.getIdCard();
		String mobile = param.getMobile();
		String devMac = param.getDevMac();
		String eyunId = param.getEyunId();
		//定义返回值
		Result result = new Result();
		String status = Constants.RETURN_ERROR;
		String msg = "保存失败";
		if(!CommonUtil.isEmpty(placeCode)
				&& !CommonUtil.isEmpty(memberName) && !CommonUtil.isEmpty(idCard) && !CommonUtil.isEmpty(mobile) //实名认证所需信息
				&& memberId!=null && memberId>0){
			//------------------------------------------------------------------
			//1.先保存数据到本地数据库
			//------------------------------------------------------------------
			WhiteEntity entity = new WhiteEntity(placeCode , memberName,idCard , mobile , devMac);
			entity.setSurfUserType("MOBILE");//根据手机号免认证
			entity.setOptorType("1");
			entity.setOptorID(memberId);
			int dbRs = whiteDaoImpl.saveWhiteList(entity);
			//------------------------------------------------------------------
			// 2. 然后连同主键全部传递给认证中心，
			//------------------------------------------------------------------
			if(dbRs > 0){
				entity.setId(dbRs);//设置记录主键
//				String authRs = syncWhite2Auth(entity, AUTH_OPTTYPE_ADD);
				String authRs = "100";
				if("100".equals(authRs)){//成功
					msg = "保存成功";
					entity.setSync2Auth("1"); //同步成功
					int row = whiteDaoImpl.updateSync2AuthState(entity);
					if(row > 0 ){
						status = Constants.RETURN_SUCCESS ;
						logger.info("更新数据库，同步认证状态成功");
					}else{
						logger.error("更新数据库，同步认证状态失败");
					}
				}else{
					msg = "同步到认证设备失败";
					logger.info(WhiteEnum.getDesByTypeCode(ENUMTYPE_AUTH,authRs));
				}
			}else{
				msg = WhiteEnum.getDesByTypeCode(ENUMTYPE_DB_ADD, dbRs + "");
				logger.error("WhiteServiceImpl.saveWhiteList-->保存到数据库失败");
			}
		}else{
			logger.error("WhiteServiceImpl.saveWhiteList-->参数不合法,params:"+JSONObject.toJSONString(param));
		}
		result.setStatus(status);
		result.setMessage(msg);
		return result;
	}

	@Override
	public Result updateWhiteInfo(ParamFilter param, Integer memberId) throws Exception {
		logger.debug("添加白名单开始***************************");
		Integer id = param.getId();
		String placeCode = param.getPlaceCode();//场所编号

		String memberName = param.getMemberName();//用户名称
		String idCard = param.getIdCard(); //身份证号
		String mobile = param.getMobile(); //手机号
		String devMac = param.getDevMac(); //设备mac地址
		//定义返回值
		Result result = new Result();
		String status = Constants.RETURN_ERROR;
		String msg = "更新失败";
		if(!CommonUtil.isEmpty(placeCode)
				&& !CommonUtil.isEmpty(memberName) && !CommonUtil.isEmpty(idCard) && !CommonUtil.isEmpty(mobile) //实名认证所需信息
				&& memberId!=null && memberId>0){
			//------------------------------------------------------------------
			//1.先更新本地数据
			//------------------------------------------------------------------
			WhiteEntity entity = new WhiteEntity(placeCode , memberName,idCard , mobile , devMac );
			entity.setId(id);   //设置记录主键
			entity.setOptorType("1"); //操作类型 1:管理员
			entity.setOptorID(memberId); //操作人
			int dbRs = whiteDaoImpl.updateWhiteInfo(entity);
			//------------------------------------------------------------------
			// 2. 然后通知认证中心，修改数据
			//------------------------------------------------------------------
			if(dbRs == 0){
//				String authRs = syncWhite2Auth(entity, AUTH_OPTTYPE_ADD);
				String authRs = "100";
				if("100".equals(authRs)){//成功
					msg = "保存成功";
					status = Constants.RETURN_SUCCESS ;
					logger.info("同步认证状态成功");
				}else{
					msg = "同步到认证设备失败";
					logger.info(WhiteEnum.getDesByTypeCode(ENUMTYPE_AUTH,authRs));
				}
			}else{
				msg = WhiteEnum.getDesByTypeCode(ENUMTYPE_DB_ADD, dbRs + "");
				logger.error("WhiteServiceImpl.saveWhiteList-->保存到数据库失败");
			}
		}else{
			logger.error("WhiteServiceImpl.saveWhiteList-->参数不合法,params:"+JSONObject.toJSONString(param));
			logger.error("WhiteServiceImpl.saveWhiteList-->参数不合法,memberId:"+memberId);
		}
		result.setStatus(status);
		result.setMessage(msg);
		return result;
	}

	/**
	 * 删除白名单
	 * 删除白名单需要先调用认证中心“网吧场所易韵账户白名单”接口删除白名单，
	 * 		a.删除成功后，将本地数据库中的数据删除
	 * 		b.删除失败，则不做后续操作，返回删除失败提示
	 * 	是不是不需要ename，idCard，和mobile
	 * @param param{
	 * 		id [白名单主键]
	 * 	    ,eyunId [易韵号]
	 * 	    ,mobile [手机号]
	 * 	    ,placeCode [场所编号]
	 * }
	 * @return	{ "status":'SUCCESS/ERROR'
	 * 			'msg':'成功'
	 * }
	 */
	@Override
	public Result delWhiteList(ParamFilter param,Integer memberId) throws Exception {
		logger.debug("删除白名单开始***************************");
		int id = param.getEntityId();
		String eyunId = param.getEyunId();
		String placeCode = param.getPlaceCode();

		String memberName = param.getMemberName();
		String idCard = param.getIdCard();
		String mobile = param.getMobile();
		String devMac = param.getDevMac();
		//定义返回值
		Result result = new Result();
		String status = Constants.RETURN_ERROR;
		String msg = "删除失败";

		/*
		 * id 必须存在，本地操作需要
		 * 手机号和易韵账号不可同时为空，场所编号必须存在，这三个参数为认证中心需要
		 */
		if(id > 0
				&& StringUtil.isNotEmpty(placeCode)
				&& StringUtil.isNotEmpty(memberName)
				&& StringUtil.isNotEmpty(idCard)
				&& StringUtil.isNotEmpty(mobile) //实名认证所需信息
				&& (memberId!=null && memberId>0)){
			//先调用认证中心“网吧场所易韵账户白名单接口”删除白名单
			WhiteEntity entity = new WhiteEntity(placeCode , memberName,idCard , mobile , devMac );
			entity.setId(id);
			entity.setOptorType("1");
			entity.setOptorID(memberId);
//			String authRs = syncWhite2Auth(entity, AUTH_OPTTYPE_DEL);
			String authRs = "100";
			if("100".equals(authRs)){//成功
				int dbRs = whiteDaoImpl.delWhiteList(entity);
				if(dbRs==-1){//添加到数据库失败，回滚认证中心的操作
					authRs = syncWhite2Auth(entity, AUTH_OPTTYPE_ADD);
					logger.info("WhiteServiceImpl.delWhiteList-->回滚操作认证返回："+WhiteEnum.getDesByTypeCode(ENUMTYPE_AUTH, authRs));
				}else{
					status = Constants.RETURN_SUCCESS;
					msg = "删除成功";
				}
			}else{
				msg = "删除认证设备数据失败";
				logger.info("WhiteServiceImpl.delWhiteList-->"+WhiteEnum.getDesByTypeCode(ENUMTYPE_DB_DEL, authRs));
			}
		}else{
			logger.error("WhiteServiceImpl.delWhiteList-->参数不合法,params: "+JSONObject.toJSONString(param));
		}
		result.setStatus(status);
		result.setMessage(msg);
		return result;
	}


	/**
	 * 调用认证中心“添加/删除白名单”接口，并返回结果
	 * @param entity,
	 * @param optType{ E :(新增), D:(删除)}
	 * @return String{
	 *     100：成功
	 *	   110：失败，系统异常
	 *	   111：失败，易韵ID/手机号未注册
	 *	   112：失败，易韵ID与手机号不匹配
	 * }
	 */
	private String syncWhite2Auth(WhiteEntity entity,String optType){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("serNo",entity.getId());
		jsonObject.put("mobile",entity.getMobile());
		jsonObject.put("serviceCode",entity.getPlaceCode());
		jsonObject.put("userMac",entity.getDevMac());
		jsonObject.put("ename",entity.getUserName());
		jsonObject.put("idCard",entity.getIdCard());
		jsonObject.put("type",optType);

		String params  = jsonObject.toJSONString();
		String url = SystemConfigUtil.getValue("AUTHlOGIN_CENTER") + AuthMethodEnum.netBarYiyunVip.getValue();
		logger.info("WhiteServiceImpl.syncWhite2Auth-->调用认证中心的接口：-->"+url);
		logger.info("WhiteServiceImpl.syncWhite2Auth-->同步免认证人员信息参数为：-->"+params);
		try {
			params = DesUtil.encrypt(params);
		} catch (Exception e) {
			logger.error("WhiteServiceImpl.syncWhite2Auth-->调用认证中心添加白名单接口加密出错了"+e.getMessage());
		}
		String authRs = HttpUtil.portConnect(url, params);
		logger.info("WhiteServiceImpl.syncWhite2Auth-->调用认证中心添加白名单接口返回加密结果："+authRs);
		//解密结果集
		try {
			authRs = DesUtil.decrypt(authRs);
			logger.info("WhiteServiceImpl.syncWhite2Auth-->调用认证中心添加白名单接口解密返回值："+authRs);
		} catch (Exception e) {
			logger.error("WhiteServiceImpl.syncWhite2Auth-->调用认证中心添加白名单接口解密出错了"+e.getMessage());
		}
		//接口返回成功状态码
		JSONObject resultCode = null;
		try{
			resultCode = JSONObject.parseObject(authRs);
			authRs = resultCode.getString("result");
		}catch(Exception e){
			logger.debug("WhiteServiceImpl.syncWhite2Auth-->认证中心返回类型错误--》"+authRs);
			authRs="";//解析出错
		}
		logger.debug("WhiteServiceImpl.syncWhite2Auth-->调用《添加白名单接口》返回值：--》"+authRs);
		return authRs;
	}



	@Override
	protected IMybatisPersitenceDao getBaseDao() {
		return whiteDaoImpl;
	}
}
