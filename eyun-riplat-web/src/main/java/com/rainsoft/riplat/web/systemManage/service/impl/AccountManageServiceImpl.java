package com.rainsoft.riplat.web.systemManage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.dao.IAccountManageDao;
import com.rainsoft.riplat.web.systemManage.model.AccountManage;
import com.rainsoft.riplat.web.systemManage.service.IAccountManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("accountManageService")
public class AccountManageServiceImpl extends MybatisBasePersitenceServiceImpl<AccountManage, String> implements IAccountManageService{
	@Resource
	private IAccountManageDao accountManageDao;
	
	@Override
	protected IMybatisPersitenceDao<AccountManage, String> getBaseDao() {
		return accountManageDao;
	}
	
	/**
	 * 方法功能说明：根据当前会员id解绑账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage userId会员id
	 * @return
	 */
	@Override
	public String updateRelieveById(AccountManage accountManage){
		Integer msg = accountManageDao.update(accountManage);
		String result = "";
		if(msg<=0 || msg==null){
			result = Constants.RETURN_ERROR;
		} else {//增加else 以前默认成功
			result = Constants.RETURN_SUCCESS;
		}
		return result;
	}
	
	/**
	 * 方法功能说明：绑定账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 当前账号id userId
	 * @param 绑定账号id currentMemberId
	 * @param 绑定账号密码 currentPassword（MD5加密32位字符）
	 * @return
	 */
	@Override
	public Result callBindingAccount(AccountManage accountManage){
		accountManageDao.update("callBindingAccount",accountManage);
		Result result = new Result();
		if(0 == accountManage.getResult()){
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("绑定成功!");
		}else if(-1 == accountManage.getResult()){
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("操作失败!");
		}else if(-2 == accountManage.getResult()){
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("此账号已被绑定!");
		}else if(-3 == accountManage.getResult()){
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("账号密码不匹配!");
		}else if(-4 == accountManage.getResult()){
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("不能绑定自己!");
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("操作失败!");
		}
		return result;
	}
	
	/**
	 * 方法功能说明：设置用户别名
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 
	 * @param nickName 用户别名
	 * @param userId 当前会员Id
	 * @return
	 */
	@Override
	public Result updateNickName(AccountManage accountManage){
		Result result = new Result();
		Integer sta = accountManageDao.update("updateNickName", accountManage);
		if(sta>0){
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("修改成功!");
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("修改失败!");
		}
		return result;
	}
	
	/**
	 * 方法功能说明：密码设置
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param payPassword 密码设置实体类
	 * @param userId 当前会员id
	 * @param payPassword 用户设置的支付密码
	 * @param currentPwd 用户当前的支付密码
	 * @param
	 * @return
	 */
	@Override
	public Result updatePayPwdSet(AccountManage accountManage){
		Result result = new Result();
		AccountManage aManage = new AccountManage();
		aManage.setUserId(accountManage.getUserId());
		aManage.setPayPassword(CommonUtil.getMd5(accountManage.getPayPassword()));
		//当前密码不为空，需判断密码输入是否正确
		if(accountManage.getCurrentPwd()!=null && !"".equals(accountManage.getCurrentPwd())){
			aManage.setCurrentPwd(CommonUtil.getMd5(accountManage.getCurrentPwd()));
			aManage.setRemark("修改会员账户信息表记录，用户修改密码");
			Integer count = accountManageDao.findCountBy(aManage);
			if(count>0){//当前密码输入正确,修改密码
				 payPwdParameter(result,aManage);//,String setBuyPassword
			}else{
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage("密码错误!");
			}
		}else{//没有设置过支付密码，则添加会员账户信息表记录
			aManage.setRemark("新增会员账户信息表记录，用户设置密码");
			Integer msg = accountManageDao.save(aManage);
			if(msg>0){
				result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage("密码设置成功!");
			}else{
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage("密码设置失败!");
			}
		}
		return result;
	}

	/**
	 * 方法功能说明： 查询支付密码
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param payPassword 密码设置实体类
	 * @param userId 当前会员id
	 * @return
	 */
	@Override
	public Result findPayPwd(AccountManage accountManage){
		Result result = new Result();
		AccountManage accountManage1 = new AccountManage();
		accountManage1.setUserId(accountManage.getUserId());
		AccountManage payPwd =  accountManageDao.findBy("findPayPwd",accountManage1);
		if(payPwd!=null && !"".equals(payPwd)){
			//修改支付密码
//			if(payPwd.getPayPassword()!=null && !"".equals(payPwd.getPayPassword())){
			result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage("会员账户信息表有对应数据，可修改");
//			}else{
//				json.put("date", "ERROR");
//				result.setStatus(Constants.RETURN_ERROR);
//				//没有用户当前密码
//				result.setMessage("密码未设置，可设置");
//				result.setData(json);
//			}
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			//没有用户当前密码
			result.setMessage("密码未设置，可设置");
		}
		return result;
	}

	/**
	 * 密码设置 子方法
	 * @param json
	 * @param result
	 * @param payPwd
	 */
	public void payPwdParameter(Result result,AccountManage accountManage){
		//设置支付密码
		Integer msg = accountManageDao.update("updatePayPwd",accountManage);
		if(msg>0){
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("密码设置成功!");
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("密码设置失败!");
		}
	}
}
