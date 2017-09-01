package com.rainsoft.union.web.account.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.AmtFormat;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.account.model.AccAccount;
import com.rainsoft.union.web.account.service.IAccAccountService;
import com.rainsoft.union.web.wxpay.model.WXResEntity;
import com.rainsoft.union.web.wxpay.service.IWxCodePayService;
import com.rainsoft.union.web.wxpay.util.MD5SignUtil;
import com.rainsoft.union.web.wxpay.util.XmlUtils;



/**
 * 
 * @Description:旺旺币controller类
 * @author:王乾
 * @date:2016年5月17日上午9:31:57
 * 
 */
@Controller
@Log(clazzDesc = "账户信息")
@RequestMapping("/account/accAccount")
@SessionAttributes({"creator"})
public class AccAccountController extends SpringBaseController<AccAccount, String>{

	private final String PREFIX = "/account/acc";
	
	@Resource
	private IAccAccountService accAccountService;
	
	@Autowired
	private IWxCodePayService wxCodePayService;
	
	@Override
	protected IMybatisBasePersitenceService<AccAccount, String> getBaseService() {
		
		return accAccountService;
	}

	@Override
	protected String getPrefix() {
		
		return PREFIX;
	}
	
	/**
	 * @Description: 跳转到账户管理页面
	 * @author sh_j_wangqian
	 * @created 2016年4月7日 下午4:09:45
	 *
	 */
	@RequestMapping("/toAccAccount")
	public String toAccAccount(Model model) {
		
		model.addAttribute("creator", SpringMvcUtil.getUserId());
		AccAccount w = accAccountService.findBy("getAccountBal",SpringMvcUtil.getUserId());
		
		if(null != w){
			model.addAttribute("accountBal",AmtFormat.commAmtFormat(w.getAccountBal()));
		}
		
		return PREFIX + "Buy";
	}
	
	
	/**
	 * 
	 * @Description:易付宝支付
	 * @author:王乾
	 * @date:2016年5月18日下午5:10:25
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Log(methodDesc = "易付宝支付")
	@RequestMapping(value = "/yfbPay")
	public String yfbPay(HttpServletRequest request,Model model) throws Exception{
		String userkey = request.getParameter("userKey");
		String r = accAccountService.yfbPay(userkey,model);
		return PREFIX+r;
	}
	
	/**
	 * 
	 * @Description:易付宝支付（重新支付）
	 * @author:王乾
	 * @date:2016年5月19日下午3:25:12
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Log(methodDesc = "易付宝重新支付")
	@RequestMapping(value = "/againYfbPay")
	public String againYfbPay(HttpServletRequest request,Model model) throws Exception{
		String r = accAccountService.againYfbPay(request,model);
		return PREFIX+r;
	}
	
	/**
	 * 
	 * @Description:易付宝支付的回调
	 * @author:王乾
	 * @date:2016年5月20日上午10:28:08
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Log(methodDesc = "易付宝支付的回调")
	@RequestMapping(value = "/yfbCallBack")
	public void yfbCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		accAccountService.yfbCallBack(request,response);
	}
	
	
	
	/**
	 * 
	 * @Description:支付宝支付
	 * @author:王乾
	 * @date:2016年5月18日下午5:10:42
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Log(methodDesc = "支付宝支付")
	@RequestMapping(value = "/zfbPay")
	public String zfbPay(HttpServletRequest request,Model model) throws Exception{
		String userkey = request.getParameter("userKey");
		String r = accAccountService.zfbPay(userkey,model);
		return PREFIX+r;
	}
	
	
	/**
	 * 
	 * @Description:支付宝（重新支付）
	 * @author:王乾
	 * @date:2016年5月19日下午3:24:45
	 * @param request
	 * @param model
	 * @return 
	 * @return
	 * @throws Exception
	 */
	@Log(methodDesc = "支付宝重新支付")
	@RequestMapping(value = "/againZfbPay")
	public String againZfbPay(HttpServletRequest request,Model model) throws Exception{
		String r = accAccountService.againZfbPay(request,model);
		return PREFIX+r;
	}
	
	
	/**
	 * 
	 * @Description:支付宝回调
	 * @author:王乾
	 * @date:2016年5月20日下午1:13:42
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Log(methodDesc = "支付宝回调")
	@RequestMapping(value = "/zfbCallBack")
	public void zfbCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		accAccountService.zfbCallBack(request,response);
	}
	

	@Log(methodDesc = "微信支付")
	@RequestMapping(value = "/wxPay")
	public String wxPay(HttpServletRequest request,Model model) throws Exception{
		String userkey = request.getParameter("userKey");
		String r = accAccountService.wxPay(userkey,model);
		return PREFIX+r;
	}
	
	@Log(methodDesc = "微信重新支付")
	@RequestMapping(value = "/againWxPay")
	public String againWxPay(HttpServletRequest request,Model model) throws Exception{
		String r = accAccountService.againWxPay(request,model);
		return PREFIX+r;
	}
	
	@RequestMapping(value = "/wxCallBack")
	public void wxCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		logger.info("----------------微信回调开始---------------------");
		StringBuffer data = new StringBuffer();
		InputStream in = request.getInputStream();
        BufferedInputStream buf = new BufferedInputStream(in);
        byte[] buffer = new byte[1024];
        int i;
        while ((i = buf.read(buffer)) != -1) {
            data.append(new String(buffer, 0, i, "UTF-8"));
        }
//		Map<String,String> params = new HashMap<String,String>();
//		Map<String, String[]> requestParams = request.getParameterMap();
//		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = iter.next();
//			String[] values = requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i]
//						: valueStr + values[i] + ",";
//			}
//			params.put(name, valueStr);
//		}
        Map<String, String> params = XmlUtils.getMapStringFromXml(data.toString());
		accAccountService.wxCallBack(params,response);
		
		
	}
	
	
	@Log(methodDesc = "微信下单")
	@RequestMapping(value = "/wxSend")
	public void wxSend(HttpServletRequest request,Model model,HttpServletResponse response) throws Exception{
		WXResEntity wxResEntity = accAccountService.wxSend(request, model);
		logger.info("----"+wxResEntity+"-----");
		Result result = new Result();
		if(wxResEntity!=null) {
			result.setStatus(Constants.RETURN_SUCCESS);
			JSONObject obj = (JSONObject) JSONObject.toJSON(wxResEntity);
			result.setData(obj);
		} else {
			result.setStatus(Constants.RETURN_ERROR);
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), result);
		
	}
	@Log(methodDesc = "生成微信二维码")
	@RequestMapping(value="/create2code")
	public void create2code(HttpServletResponse response,@RequestParam String codeUrl)
	{	
		wxCodePayService.create2code(response, codeUrl);

	}
}
