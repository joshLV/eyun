package com.rainsoft.union.web.account.service.impl;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.model.PayStatusEnum;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.AmtFormat;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.base.common.utils.UUIDGenerator;
import com.rainsoft.union.web.account.dao.IAccAccountDao;
import com.rainsoft.union.web.account.model.AccBuyRecord;
import com.rainsoft.union.web.account.model.AccAccount;
import com.rainsoft.union.web.account.model.AccYfbBuy;
import com.rainsoft.union.web.account.model.AccZfbBuy;
import com.rainsoft.union.web.account.service.IAccAccountService;
import com.rainsoft.union.web.wxpay.model.WXReqEntity;
import com.rainsoft.union.web.wxpay.model.WXResEntity;
import com.rainsoft.union.web.wxpay.service.IWxCodePayService;
import com.rainsoft.union.web.wxpay.util.Configure;
import com.rainsoft.union.web.wxpay.util.MD5SignUtil;
import com.rainsoft.union.web.wxpay.util.WxPayUtil;
import com.rainsoft.union.web.yeepay.service.IPaymentForOnlineService;
import com.rainsoft.union.web.yeepay.service.impl.Configuration;
import com.rainsoft.union.web.zfbpay.service.IAlipayNotifyService;
import com.rainsoft.union.web.zfbpay.service.impl.AlipayConfig;

@Service("accAccountService")
public class AccAccountServiceImpl extends MybatisBasePersitenceServiceImpl<AccAccount, String> implements IAccAccountService {
    
	
	private static Log logger = LogFactory.getLog(AccAccountServiceImpl.class);
	@Resource
	private IAccAccountDao accAccountDao;
	
	@Autowired
    private IPaymentForOnlineService paymentForOnlineService;
	
	@Autowired
    private IAlipayNotifyService alipayNotifyService;
	
	@Autowired
	private IWxCodePayService wxCodePayService;
	
	@Override
	protected IMybatisPersitenceDao<AccAccount, String> getBaseDao() {
		return accAccountDao;
	}
	
	String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
	
	/**
	 * 
	 * 易付宝支付
	 */
	@Override
	public String yfbPay(String userkey,Model model) throws Exception {
		//logger.info("易宝---："+request.getMethod());
		String userId = SpringMvcUtil.getUserId().toString();
		//Base64解密
		byte[] userKeyCode = Base64.decodeBase64(userkey.getBytes("UTF-8"));
		String userKeyStr = new String(userKeyCode);
		String[] userKeyStrArr = userKeyStr.split(",");
		String wwbiFee = userKeyStrArr[0];
		String rmbFee = userKeyStrArr[1];
		//旺旺币数量
		String wwbQtyFee = userKeyStrArr[2];
		// 业务类型  购买旺旺币为  0购买旺旺币 1、购买短信
		String useType = userKeyStrArr[3];
		//验证金额和旺旺币数量
		try{
		   
		   Integer.parseInt(wwbiFee);
		   AmtFormat.commAmtFormat(rmbFee);
		   Integer.parseInt(wwbQtyFee);
		   
		} catch (Exception e){
			
			e.printStackTrace();
			logger.info("验证失败");
			return "OrderFail";
		}
		
		//设置订单参数 
		AccBuyRecord wwbRecord = new AccBuyRecord();
		wwbRecord.setUserId(Integer.parseInt(userId));
		wwbRecord.setRelatingId("");
		wwbRecord.setPayChannel('6');
		wwbRecord.setRmbFee(AmtFormat.commAmtFormat(rmbFee));
		wwbRecord.setWwbiFee(AmtFormat.commAmtFormat(wwbiFee));
		wwbRecord.setUseType(useType);
		wwbRecord.setRemark("");
		wwbRecord.setPayStatus(PayStatusEnum.UNPAID.getCode());
		wwbRecord.setCreator(userId);
		wwbRecord.setUpdator(userId);
        wwbRecord.setWwbQtyFee(wwbQtyFee);
   //易宝支付
	      accAccountDao.save("saveAccBuyRecord", wwbRecord);
	  
	      int orderid = wwbRecord.getBuyWWBiRecordId();
	      
	    if(orderid >0){
	    	 
	    	logger.info("联盟业务系统生成订单："+orderid);
				//订单生成成功后，调用支付接口
				//payEntity = buyWWBiService.getPayWWBi(entity);
				 //"Buy"
			     String nodeAuthorizationURL  	= formatString(Configuration.getInstance().getValue("yeepayCommonReqURL"));

				 String p0_Cmd = "Buy";
				 //通过配置文件获取商户编号
				 String p1_MerId = formatString(Configuration.getInstance().getValue("p1_MerId")); 
				 //生成订单号
				 String p2_Order = String.valueOf(orderid);
				 String p3_Amt = rmbFee;
				 String p4_Cur = "CNY";
				 String p5_Pid = "购买旺旺币";
				 String p6_Pcat = "producttype";
				 String p7_Pdesc = "productdesc";
				 String url =""; 
					try{
						 url = SystemConfigUtil.getValue("baseCallbackurl");
					}catch(Exception e){
						e.printStackTrace();
						logger.debug("通过配置文件，获取回调地址异常！");
					}
	             //回调地址
				 String p8_Url = url+"/eyunion/account/accAccount/yfbCallBack.do";
				 String p9_SAF = "0";
				 String pa_MP = "userId or other";
				 String pd_FrpId = "";
				 String pr_NeedResponse = "1";
				 String hmac = "";
				// 通过配置获取商家密钥
				 String keyValue   = formatString(Configuration.getInstance().getValue("keyValue"));   
				 AccYfbBuy yfb = new AccYfbBuy();
				 yfb.setNodeAuthorizationURL(nodeAuthorizationURL);
				 yfb.setP0_Cmd(p0_Cmd);
				 yfb.setP1_MerId(p1_MerId);
				 yfb.setP2_Order(p2_Order);
				 yfb.setP3_Amt(p3_Amt);
				 yfb.setP4_Cur(p4_Cur);
				 yfb.setP5_Pid(p5_Pid);
				 yfb.setP6_Pcat(p6_Pcat);
				 yfb.setP7_Pdesc(p7_Pdesc);
				 yfb.setP8_Url(p8_Url);
				 yfb.setP9_SAF(p9_SAF);
				 yfb.setPa_MP(pa_MP);
				 yfb.setPd_FrpId(pd_FrpId);
				 yfb.setPr_NeedResponse(pr_NeedResponse);
				//字符串拼接获得hmac
				String sNewString = paymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd, p1_MerId,
						p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId,
						pr_NeedResponse, keyValue);
				yfb.setHmac(sNewString);
				 model.addAttribute("yfb", yfb);
			}else{
				logger.info("业务系统生成订单失败，状态码："+orderid);
				return "OrderFail";
			}
	    model.addAttribute("wwbRecord", wwbRecord);
	    
		return "YfbAffirmPay";
	}
	
	
	/**
	 * 
	 * 易付宝重新支付
	 */
	@Override
	public String againYfbPay(HttpServletRequest request, Model model) throws Exception {
		AccBuyRecord wwbRecord = new AccBuyRecord();
		//会员id
		String userId = SpringMvcUtil.getUserId().toString();
		String userkey = request.getParameter("userKey");
		
		byte[] userKeyCode = Base64.decodeBase64(userkey.getBytes("UTF-8"));
		String userKeyStr = new String(userKeyCode);
		String[] userKeyStrArr = userKeyStr.split(",");
		String buyWWBiRecordId = userKeyStrArr[0];
		String wwbiFee = userKeyStrArr[1];
		
		String rmbFee = userKeyStrArr[2];
		//旺旺币数量
		String wwbQtyFee = userKeyStrArr[2];

		
		//验证金额和旺旺币数量
				try{
				   
				   Integer.parseInt(wwbiFee);
				   AmtFormat.commAmtFormat(rmbFee);
				   Integer.parseInt(wwbQtyFee);
				   
				} catch (Exception e){
					
					e.printStackTrace();
					logger.info("验证失败");
					return "OrderFail";
				}
		
		String orderid = "";
		try{
			//设置订单参数 
			wwbRecord.setRmbFee(AmtFormat.commAmtFormat(rmbFee));
			wwbRecord.setWwbiFee(AmtFormat.commAmtFormat(wwbiFee));
			wwbRecord.setWwbQtyFee(wwbQtyFee);
			wwbRecord.setUserId(Integer.parseInt(userId));
			wwbRecord.setBuyWWBiRecordId(Integer.parseInt(buyWWBiRecordId));
			orderid = buyWWBiRecordId;
		}catch(Exception e){
			logger.info("旺旺币业购买务系统生成订单失败，请求参数异常！");
			return "OrderFail";
		}
		if(orderid != null && orderid != ""){
			logger.info("联盟业务系统生成订单："+orderid);
			//订单生成成功后，调用支付接口
			//payEntity = buyWWBiService.getPayWWBi(entity);
		     String nodeAuthorizationURL  	= formatString(Configuration.getInstance().getValue("yeepayCommonReqURL"));

			 String p0_Cmd = "Buy";
			 //通过配置文件获取商户编号
			 String p1_MerId = formatString(Configuration.getInstance().getValue("p1_MerId")); 
			 //生成订单号
			 String p2_Order = String.valueOf(orderid);
			 String p3_Amt = rmbFee;
			 String p4_Cur = "CNY";
			 String p5_Pid = "购买旺旺币";
			 String p6_Pcat = "producttype";
			 String p7_Pdesc = "productdesc";
			 String url =""; 
				try{
					 url = SystemConfigUtil.getValue("baseCallbackurl");
				}catch(Exception e){
					e.printStackTrace();
					logger.debug("通过配置文件，获取回调地址异常！");
				}
             //回调地址
			 String p8_Url = url+"/eyun-union-web/account/accAccount/yfbCallBack.do";
			 String p9_SAF = "0";
			 String pa_MP = "userId or other";
			 String pd_FrpId = "";
			 String pr_NeedResponse = "1";
			 String hmac = "";
			// 通过配置获取商家密钥
			 String keyValue   = formatString(Configuration.getInstance().getValue("keyValue"));   
			 
			 AccYfbBuy yfb = new AccYfbBuy();
			 yfb.setNodeAuthorizationURL(nodeAuthorizationURL);
			 yfb.setP0_Cmd(p0_Cmd);
			 yfb.setP1_MerId(p1_MerId);
			 yfb.setP2_Order(p2_Order);
			 yfb.setP3_Amt(p3_Amt);
			 yfb.setP4_Cur(p4_Cur);
			 yfb.setP5_Pid(p5_Pid);
			 yfb.setP6_Pcat(p6_Pcat);
			 yfb.setP7_Pdesc(p7_Pdesc);
			 yfb.setP8_Url(p8_Url);
			 yfb.setP9_SAF(p9_SAF);
			 yfb.setPa_MP(pa_MP);
			 yfb.setPd_FrpId(pd_FrpId);
			 yfb.setPr_NeedResponse(pr_NeedResponse);
			//字符串拼接获得hmac
			String sNewString = paymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd, p1_MerId,
					p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId,
					pr_NeedResponse, keyValue);
			yfb.setHmac(sNewString);
			model.addAttribute("yfb", yfb);
		}else{
			logger.info("业务系统生成订单失败，状态码："+orderid);
			return "OrderFail";
		}
		 model.addAttribute("wwbRecord", wwbRecord);
		 
		return "YfbAffirmPay";
	}
	
	
	/**
	 * 易付宝支付回调
	 * 调用支付接口后，调用的方法
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String yfbCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info("易宝开始回调！");
		String data ="";
		logger.info("易宝回调方式："+request.getMethod());
		try{
			 data = request.getQueryString();
			 logger.info("易宝返回的数据字符串data:"+data);
		}catch(Exception e){
			e.printStackTrace();
		}
		//测试用
//		String r1_Code    = formatString("1");// 支付结果
//		String r2_TrxId   = formatString("321321321321");// 易宝支付交易流水号
//		//String r5_Pid     = new String(formatString(request.getParameter("r5_Pid")).getBytes("iso-8859-1"),"utf-8");// 商品名称
//		String r6_Order   = formatString(request.getParameter("p2_Order"));// 商户订单号
//		String r9_BType   = formatString("2");// 交易结果返回类型

		String keyValue   = formatString(Configuration.getInstance().getValue("keyValue"));   // 商家密钥
		String r0_Cmd 	  = formatString(request.getParameter("r0_Cmd")); // 业务类型
		String p1_MerId   = formatString(Configuration.getInstance().getValue("p1_MerId"));   // 商户编号
		String r1_Code    = formatString(request.getParameter("r1_Code"));// 支付结果
		String r2_TrxId   = formatString(request.getParameter("r2_TrxId"));// 易宝支付交易流水号
		String r3_Amt     = formatString(request.getParameter("r3_Amt"));// 支付金额
		String r4_Cur     = formatString(request.getParameter("r4_Cur"));// 交易币种
		//String r5_Pid     = new String(formatString(request.getParameter("r5_Pid")).getBytes("iso-8859-1"),"utf-8");// 商品名称
		String r5_Pid     =  "购买旺旺币";
		String r6_Order   = formatString(request.getParameter("r6_Order"));// 商户订单号
		String r7_Uid     = formatString(request.getParameter("r7_Uid"));// 易宝支付会员ID
		String r8_MP      = new String(formatString(request.getParameter("r8_MP")).getBytes("iso-8859-1"),"gbk");// 商户扩展信息
		String r9_BType   = formatString(request.getParameter("r9_BType"));// 交易结果返回类型
		String hmac       = formatString(request.getParameter("hmac"));// 签名数据
		boolean isOK = false;
		//测试用
//		boolean isOK = true;
		
		response.setCharacterEncoding("GBK");
 		PrintWriter out = response.getWriter(); 
		
		// 校验返回数据包
		isOK = paymentForOnlineService.verifyCallback(hmac,p1_MerId,r0_Cmd,r1_Code, 
				r2_TrxId,r3_Amt,r4_Cur,r5_Pid,r6_Order,r7_Uid,r8_MP,r9_BType,keyValue);
		logger.info("商品名称："+r5_Pid+",校验返回数据:"+isOK+",支付结果:"+r1_Code+",商家订单号:" + r6_Order + ",支付金额:" + r3_Amt + ",易宝支付交易流水号:" + r2_TrxId+
				",签名数据"+hmac);
		   
		if(isOK) {
			//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			if(r1_Code.equals("1")) {
				// 产品通用接口支付成功返回-浏览器重定向
				if(r9_BType.equals("1")) {
					out.println("callback方式:产品通用接口支付成功返回-浏览器重定向");
					// 产品通用接口支付成功返回-服务器点对点通讯
				} else if(r9_BType.equals("2")) {
					// 如果在发起交易请求时	设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
					//联盟的业务逻辑
					//调用业务方法
					
					//int recordId = accAccountDao.(r6_Order, r2_TrxId, 1);
						//查询该订单的状态
					AccAccount orderObj = accAccountDao.findBy("getWwbiRecord", r6_Order);
				
					if(!orderObj.getPayStatus().equals("8")){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("orderId",r6_Order);
						map.put("relatingId", r2_TrxId);
						map.put("payStatus", PayStatusEnum.ALREADYAID.getCode());
						
						//更新该订单状态
						int result = accAccountDao.update("uptWwbiRecord", map);
						if(result > 0){
							map.put("userId", orderObj.getUserId());
							map.put("RMBFee", AmtFormat.commAmtFormat(orderObj.getRmbFee()));
							map.put("WWBiFee", AmtFormat.commAmtFormat(orderObj.getWwbiFee()));
							//交易成功将金额累加
							int accObj = accAccountDao.update("uptUserAccInfo", map);
							if(accObj >= 0){
								logger.info("易宝回调联盟系统付款成功！");
							}else{
								logger.info("易宝回调联盟系统付款失败！状态码："+r6_Order);
							}
						}else{
							logger.info("易宝回调联盟系统更新订单状态失败！");
						}
					}else{
						logger.info("易宝回调联盟系统重复付款！");
					}
					
					
				  // 产品通用接口支付成功返回-电话支付返回		
				}
				// 下面页面输出是测试时观察结果使用
			}
		} else {
			logger.info("易宝回调联盟系统，交易签名被篡改!");
		}
		out.flush();
		out.close();
		
		return "succ";
	}
	
    
	
	/**
	 * 支付宝付款
	 */
	@Override
	public String zfbPay(String userkey, Model model) throws Exception {
		AccBuyRecord wwbRecord = new AccBuyRecord();
		String userId = SpringMvcUtil.getUserId().toString();
		//Base64解密
      		byte[] userKeyCode = Base64.decodeBase64(userkey.getBytes("UTF-8"));
		String userKeyStr = new String(userKeyCode);
		String[] userKeyStrArr = userKeyStr.split(",");
		String wwbiFee = userKeyStrArr[0];
		String rmbFee = userKeyStrArr[1];
		//旺旺币数量
		String wwbQtyFee = userKeyStrArr[2];
		// 业务类型  购买旺旺币为  0购买旺旺币 1、购买短信
		String useType = userKeyStrArr[3];
		//验证金额和旺旺币数量
				try{
				   
				   Integer.parseInt(wwbiFee);
				   AmtFormat.commAmtFormat(rmbFee);
				   Integer.parseInt(wwbQtyFee);
				   
				} catch (Exception e){
					
					e.printStackTrace();
					logger.info("验证失败");
					return "OrderFail";
				}
		//设置订单参数 
		wwbRecord.setUuId(UUIDGenerator.generate());
		wwbRecord.setUserId(Integer.parseInt(userId));
		wwbRecord.setRelatingId("");
		wwbRecord.setPayChannel('7');
		wwbRecord.setRmbFee(AmtFormat.commAmtFormat(rmbFee));
		wwbRecord.setWwbiFee(AmtFormat.commAmtFormat(wwbiFee));
		wwbRecord.setUseType(useType);
		wwbRecord.setRemark("");
		wwbRecord.setPayStatus(PayStatusEnum.UNPAID.getCode());
		wwbRecord.setCreator(userId);
		wwbRecord.setUpdator(userId);
        wwbRecord.setWwbQtyFee(wwbQtyFee);

        accAccountDao.save("saveAccBuyRecord", wwbRecord);
  	  
	      int orderid = wwbRecord.getBuyWWBiRecordId();
	      
	    if(orderid >0){
			AccZfbBuy zfb = new AccZfbBuy();
			logger.info("联盟业务系统生成订单："+orderid);
			zfb.setService("create_direct_pay_by_user");
			zfb.setPartner(AlipayConfig.partner);
			zfb.set_input_charset(AlipayConfig.input_charset);
			zfb.setPayment_type("1");
			zfb.setTotal_fee(Double.parseDouble(rmbFee));
			zfb.setOut_trade_no(orderid+"");
			zfb.setSeller_id(AlipayConfig.partner);
			zfb.setSubject("旺旺币");
			//zfb.setNotify_url(SystemConfigUtil.getValue("zfbCallBackUrl") + "/eyun-union-web/account/accAccount/zfbCallBack.do");
			zfb.setNotify_url(SystemConfigUtil.getValue("zfbCallBackUrl") + SystemConfigUtil.getValue("zfbPayAction"));
			zfb.setSign_type(AlipayConfig.sign_type);
			String mysign = alipayNotifyService.getSignResult(zfb);
			zfb.setSign(mysign);
			zfb.setQuantity(wwbQtyFee);
			
			model.addAttribute("zfb", zfb);
		}else{
			logger.info("业务系统生成订单失败，状态码："+orderid);
			return "OrderFail";
		}
		
		model.addAttribute("wwbRecord", wwbRecord);
		
		return "ZfbAffirmPay";
	}

	
	/**
	 * 支付宝重新支付
	 */
	@Override
	public String againZfbPay(HttpServletRequest request, Model model)
			throws Exception {
		AccBuyRecord wwbRecord = new AccBuyRecord();
		String userId = SpringMvcUtil.getUserId().toString();
		//获取ueserKey加密参数
		String userkey = request.getParameter("userKey");
		//获取请求参数
		
		byte[] userKeyCode = Base64.decodeBase64(userkey.getBytes("UTF-8"));
		String userKeyStr = new String(userKeyCode);
		String[] userKeyStrArr = userKeyStr.split(",");
		String buyWWBiRecordId = userKeyStrArr[0];
		String wwbiFee = userKeyStrArr[1];
		
		String rmbFee = userKeyStrArr[2];
		//旺旺币数量
		String wwbQtyFee = userKeyStrArr[2];
		
		String orderid = "";
		//验证金额和旺旺币数量
				try{
				   
				   Integer.parseInt(wwbiFee);
				   AmtFormat.commAmtFormat(rmbFee);
				   Integer.parseInt(wwbQtyFee);
				   
				} catch (Exception e){
					
					e.printStackTrace();
					logger.info("验证失败");
					return "OrderFail";
				}
		//设置订单参数 
		wwbRecord.setRmbFee(AmtFormat.commAmtFormat(rmbFee));
		wwbRecord.setWwbiFee(AmtFormat.commAmtFormat(wwbiFee));
		wwbRecord.setWwbQtyFee(wwbQtyFee);
		wwbRecord.setUserId(Integer.parseInt(userId));
		//支付宝
		wwbRecord.setPayChannel('7');
//		buyWWBiService.getWWBiOrder(entity);
		orderid = buyWWBiRecordId;
		if(orderid != null && orderid != ""){
			logger.info("联盟业务系统生成订单："+orderid);
			AccZfbBuy zfb = new AccZfbBuy();
			zfb.setService("create_direct_pay_by_user");
			zfb.setPartner(AlipayConfig.partner);
			zfb.set_input_charset(AlipayConfig.input_charset);
			zfb.setPayment_type("1");
			zfb.setTotal_fee(Double.parseDouble(rmbFee));
			zfb.setOut_trade_no(orderid);
			zfb.setSeller_id(AlipayConfig.partner);
			zfb.setSubject("旺旺币");
			//zfb.setNotify_url(SystemConfigUtil.getValue("zfbCallBackUrl") + "/eyunion/callWWBi/callwwbi!getWWBiOrderZfbPay.do");
			zfb.setNotify_url(SystemConfigUtil.getValue("zfbCallBackUrl") + SystemConfigUtil.getValue("zfbRePayAction"));
			zfb.setSign_type(AlipayConfig.sign_type);
			String mysign = alipayNotifyService.getSignResult(zfb);
			zfb.setSign(mysign);
			zfb.setQuantity(wwbQtyFee);
			model.addAttribute("zfb", zfb);
		}else{
			logger.info("业务系统生成订单失败，状态码："+orderid);
			return "OrderFail";
		}
		model.addAttribute("wwbRecord", wwbRecord);
		return "ZfbAffirmPay";
	}
	
	
	/**
	 * 支付宝付款后回调
	 */
	@Override
	public String zfbCallBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html;charset=UTF-8");
 		PrintWriter out = response.getWriter();
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if(alipayNotifyService.verify(params)){//验证成功
			if (trade_status.equals("TRADE_FINISHED")) {
				//暂时无退款
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				//int recordId = accAccountDao.getWWBiOrderYeePay(out_trade_no, trade_no, 1);
				AccAccount orderObj = accAccountDao.findBy("getWwbiRecord", out_trade_no);
				
				if(!orderObj.getPayStatus().equals("8")){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("orderId",out_trade_no);
					map.put("relatingId", trade_no);
					map.put("payStatus", PayStatusEnum.ALREADYAID.getCode());
					
					//更新该订单状态
					int result = accAccountDao.update("uptWwbiRecord", map);
					if(result > 0){
						map.put("userId", orderObj.getUserId());
						map.put("RMBFee", AmtFormat.commAmtFormat(orderObj.getRmbFee()));
						map.put("WWBiFee", AmtFormat.commAmtFormat(orderObj.getWwbiFee()));
						//交易成功将金额累加
						int accObj = accAccountDao.update("uptUserAccInfo", map);
						if(accObj >= 0){
							logger.info("支付宝回调联盟系统付款成功！");
						}else{
							logger.info("支付宝回调联盟系统付款失败！状态码："+out_trade_no);
						}
					}else{
						logger.info("支付宝回调联盟系统更新订单状态失败！");
					}
				}else{
					logger.info("支付宝回调联盟系统重复付款！");
				}
			}
			out.println();
			out.println();
			out.println();
			out.println();
			out.println();
			out.write("success");
			out.println();
			out.println();
		} else {
			out.println();
			out.println();
			out.println();
			out.println();
			out.println();
			out.println("fail");
			out.println();
			out.println();
		}
		out.flush();
		out.close();
		return "succ";
	}
	
	/**
	 * 根据userid查询会员账户余额
	 */
	public AccAccount getAccountBal(AccAccount acc){
		
		return accAccountDao.findBy("getAccountBal", acc);
	}
	


	@Override
	public String againWxPay(HttpServletRequest request, Model model)
			throws Exception {
		AccBuyRecord wwbRecord = new AccBuyRecord();
		String userId = SpringMvcUtil.getUserId().toString();
		//获取ueserKey加密参数
		String userkey = request.getParameter("userKey");
		//获取请求参数
		
		byte[] userKeyCode = Base64.decodeBase64(userkey.getBytes("UTF-8"));
		String userKeyStr = new String(userKeyCode);
		String[] userKeyStrArr = userKeyStr.split(",");
		String buyWWBiRecordId = userKeyStrArr[0];
		String wwbiFee = userKeyStrArr[1];
		
		String rmbFee = userKeyStrArr[2];
		//旺旺币数量
		String wwbQtyFee = userKeyStrArr[2];
		//验证金额和旺旺币数量
				try{
				   
				   Integer.parseInt(wwbiFee);
				   AmtFormat.commAmtFormat(rmbFee);
				   Integer.parseInt(wwbQtyFee);
				   
				} catch (Exception e){
					
					e.printStackTrace();
					logger.info("验证失败");
					return "OrderFail";
				}
		//设置订单参数 
		wwbRecord.setRmbFee(AmtFormat.commAmtFormat(rmbFee));
		wwbRecord.setWwbiFee(AmtFormat.commAmtFormat(wwbiFee));
		wwbRecord.setWwbQtyFee(wwbQtyFee);
		wwbRecord.setUserId(Integer.parseInt(userId));
		wwbRecord.setBuyWWBiRecordId(Integer.parseInt(buyWWBiRecordId));
		//微信
		wwbRecord.setPayChannel('8');
		model.addAttribute("wwbRecord", wwbRecord);
		return "WxAffirmPay";
	}

	@Override
	public void wxCallBack(Map<String, String> map,
			HttpServletResponse response) throws Exception {
		String return_code = map.get("return_code");
		logger.info("-------微信回调开始！---------订单号："+map.get("out_trade_no"));
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html;charset=UTF-8");
 		PrintWriter out = response.getWriter();
		if("SUCCESS".equals(return_code))
		{	
//			WXReqEntity wxReqEntity = new WXReqEntity();
//			
//			wxReqEntity.setAppid(map.get("appid"));
//			wxReqEntity.setMch_id(map.get("mch_id"));
//			wxReqEntity.setNonce_str(map.get("nonce_str"));
//			wxReqEntity.setBody("充值");
//			wxReqEntity.setOut_trade_no(map.get("out_trade_no"));
//			//wxReqEntity.setTotal_fee(new BigDecimal(map.get("total_fee")).intValue());
//			wxReqEntity.setTotal_fee(1);
//			wxReqEntity.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress());
//			//wxReqEntity.setNotify_url(SystemConfigUtil.getValue("baseCallbackurl") + SystemConfigUtil.getValue("wxCallBackPayAction"));
//			wxReqEntity.setNotify_url("http://3204d76c.nat123.net"+SystemConfigUtil.getValue("wxCallBackPayAction"));
//			wxReqEntity.setTrade_type("NATIVE");
			String wxSign = map.get("sign");
			if(verifyWxSign(map,wxSign))
			{
				String result_code = map.get("result_code");
				String out_trade_no = map.get("out_trade_no");
				String trade_no = map.get("transaction_id");
				if("SUCCESS".equals(result_code))
				{

					//int recordId = accAccountDao.getWWBiOrderYeePay(out_trade_no, trade_no, 1);
					AccAccount orderObj = accAccountDao.findBy("getWwbiRecord", out_trade_no);
					
					if(!orderObj.getPayStatus().equals("8")){
						Map<String,Object> newMap = new HashMap<String,Object>();
						newMap.put("orderId",out_trade_no);
						newMap.put("relatingId", trade_no);
						newMap.put("payStatus", PayStatusEnum.ALREADYAID.getCode());
						
						//更新该订单状态
						int result = accAccountDao.update("uptWwbiRecord", newMap);
						if(result > 0){
							newMap.put("userId", orderObj.getUserId());
							newMap.put("RMBFee", AmtFormat.commAmtFormat(orderObj.getRmbFee()));
							newMap.put("WWBiFee", AmtFormat.commAmtFormat(orderObj.getWwbiFee()));
							//交易成功将金额累加
							int accObj = accAccountDao.update("uptUserAccInfo", newMap);
							if(accObj >= 0){
								logger.info("微信回调成功！");
								out.print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
							}else{
								logger.info("微信回调失败！订单号："+out_trade_no);
							}
						}else{
							logger.info("微信回调失败！");
						}
					}
				}
			} 
			else {
				logger.info("微信回调验证签名失败！");
			}
			
		}
	}

	@Override
	public String wxPay(String userkey, Model model) throws Exception {
		AccBuyRecord wwbRecord = new AccBuyRecord();
		String userId = SpringMvcUtil.getUserId().toString();
		//Base64解密
      		byte[] userKeyCode = Base64.decodeBase64(userkey.getBytes("UTF-8"));
		String userKeyStr = new String(userKeyCode);
		String[] userKeyStrArr = userKeyStr.split(",");
		String wwbiFee = userKeyStrArr[0];
		String rmbFee = userKeyStrArr[1];
		//旺旺币数量
		String wwbQtyFee = userKeyStrArr[2];
		// 业务类型  购买旺旺币为  0购买旺旺币 1、购买短信
		String useType = userKeyStrArr[3];
		//验证金额和旺旺币数量
				try{
				   
				   Integer.parseInt(wwbiFee);
				   AmtFormat.commAmtFormat(rmbFee);
				   Integer.parseInt(wwbQtyFee);
				   
				} catch (Exception e){
					
					e.printStackTrace();
					logger.info("验证失败");
					return "OrderFail";
				}
		//设置订单参数 
		wwbRecord.setUuId(UUIDGenerator.generate());
		wwbRecord.setUserId(Integer.parseInt(userId));
		wwbRecord.setRelatingId("");
		//微信支付为8
		wwbRecord.setPayChannel('8');
		wwbRecord.setRmbFee(AmtFormat.commAmtFormat(rmbFee));
		wwbRecord.setWwbiFee(AmtFormat.commAmtFormat(wwbiFee));
		wwbRecord.setUseType(useType);
		wwbRecord.setRemark("");
		wwbRecord.setPayStatus(PayStatusEnum.UNPAID.getCode());
		wwbRecord.setCreator(userId);
		wwbRecord.setUpdator(userId);
        wwbRecord.setWwbQtyFee(wwbQtyFee);

        accAccountDao.save("saveAccBuyRecord", wwbRecord);
		
		model.addAttribute("wwbRecord", wwbRecord);
		
		return "WxAffirmPay";
	}

	@Override
	public WXResEntity wxSend(HttpServletRequest request, Model model) {
		WXResEntity wxResEntity = null;
		try {
			String buyWWBiRecordId = request.getParameter("buyWWBiRecordId");
			String rmbFee = request.getParameter("rmbFee");
			logger.info("------rmbFee------"+ rmbFee);
			WXReqEntity wxReqEntity = new WXReqEntity();
			wxReqEntity.setAppid(Configure.wx_app_id);
			wxReqEntity.setMch_id(Configure.mchId);
			wxReqEntity.setNonce_str(WxPayUtil.getRandomString(32));
			wxReqEntity.setBody("充值");
			wxReqEntity.setOut_trade_no(buyWWBiRecordId);
			//wxReqEntity.setTotal_fee(new BigDecimal(rmbFee).multiply(new BigDecimal(100)).intValue());
			wxReqEntity.setTotal_fee(1);
			wxReqEntity.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress());
			//wxReqEntity.setNotify_url(SystemConfigUtil.getValue("baseCallbackurl") + SystemConfigUtil.getValue("wxCallBackPayAction"));
			wxReqEntity.setNotify_url("http://3204d76c.nat123.net"+SystemConfigUtil.getValue("wxCallBackPayAction"));
			wxReqEntity.setTrade_type("NATIVE");
			wxResEntity=wxCodePayService.wxCodePay(wxReqEntity);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wxResEntity;
	}

	
	
	public boolean verifyWxSign(Map<String, String> map,String wxSign)
	{	
		Map<String, Object> objMap = new HashMap<String, Object>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if(!"sign".equals(entry.getKey())) {
				objMap.put(entry.getKey(), (Object)entry.getValue());
			} 
		}
		String newSign = MD5SignUtil.createSign(objMap);
		return wxSign.equals(newSign)? true : false;

	}

}