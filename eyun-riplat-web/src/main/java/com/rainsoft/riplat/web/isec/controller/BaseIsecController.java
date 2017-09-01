package com.rainsoft.riplat.web.isec.controller;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.YiYunMessageDigest;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.PagedListResult;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.isec.service.IBaseIsecService;

@Controller
public class BaseIsecController extends SpringBaseController<IdEntity, Serializable>{

    @Resource
    IBaseIsecService baseIsecService;

    @Override
    protected IMybatisBasePersitenceService<IdEntity, String> getBaseService() {
        return null;
    }

    @Override
    protected String getPrefix() {
        return null;
    }
    
	/**
     * 解密
     * @param params 字符串
     * @return 字符串
     */
	protected String desDecrypt(String params) {
        String desStr = "";
        try {
            logger.info("解密前Params：" + params);
            desStr = DesUtil.decrypt(params);
            logger.info("解密后Params：" + desStr);
        } catch (Exception e) {
            logger.error("解密失败");
        }
        return desStr;
    }
	
	/**
     * 加密
     *
     * @param params 字符串
     * @return 字符串
     */
	protected String desEncrypt(String params) {
        String desStr = "";
        try {
            logger.info("加密前Params：" + params);
            desStr = DesUtil.encrypt(params);
            logger.info("加密后Params：" + desStr);
        } catch (Exception e) {
            logger.error("加密失败");
        }
        return desStr;
    }
	
	 /**
     * 出现异常情况 同样需要增加签名
     * @param result 返回结果
     */
    public void addSignature(Result result) {
        Map<String, String> map = YiYunMessageDigest.getInstance().getSignature();//获取签名
        result.setData(map);
    }
    
    @RequestMapping(value = "/getIsecData", method=RequestMethod.POST)
    public void getIsecData(@RequestParam(name="params", required=true) String params, @RequestParam(name="method", required=true) String method){
        PagedListResult result = new PagedListResult();
        
        try {
            //统一解密
            String param = DesUtil.decrypt(params);
            if(param != null){
                result = baseIsecService.processIsecMsg(param, method);
                if(result == null){
                    result = new PagedListResult();
                    result.setMessage("调用接口失败！");
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {
                result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
            }
        } catch (Exception e) {
            result.setMessage("调用接口失败！");
            result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }
}
