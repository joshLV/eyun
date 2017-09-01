package com.rainsoft.union.web.wxpay.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rainsoft.union.web.wxpay.service.IWxCodePayService;
import com.rainsoft.union.web.wxpay.model.WXReqEntity;
import com.rainsoft.union.web.wxpay.model.WXResEntity;
import com.rainsoft.union.web.wxpay.util.Configure;
import com.rainsoft.union.web.wxpay.util.WxPayUtil;

@Service("wxCodePayService")
public class WxCodePayImpl implements IWxCodePayService
{	
	private Logger logger = LoggerFactory.getLogger(WxCodePayImpl.class);
	@Override
	public WXResEntity wxCodePay(WXReqEntity wxEntity) {
		WXResEntity wxResEntity=null;
        if(wxEntity== null){
            logger.error("WxCodePayImpl.wxCodePay-->参数对象为空");
            return wxResEntity;
        }
        String appid = wxEntity.getAppid(); //公众账号ID
        String mch_id = wxEntity.getMch_id();//商户号
        String nonce_str = wxEntity.getNonce_str(); //随机字符串
        String body = wxEntity.getBody(); //商品描述
        String out_trade_no = wxEntity.getOut_trade_no();//商户系统的订单号，与请求一致
        Integer total_fee = wxEntity.getTotal_fee();//总金额 订单总金额，单位为分，详见支付金额
        String spbill_create_ip = wxEntity.getSpbill_create_ip();//终端IP APP和网页支付提交用户端ip
        String trade_type = wxEntity.getTrade_type();
        String notify_url= wxEntity.getNotify_url();
        if(StringUtils.isEmpty(appid)
                || StringUtils.isEmpty(mch_id)
                || StringUtils.isEmpty(nonce_str)
                || StringUtils.isEmpty(body)
                || StringUtils.isEmpty(out_trade_no)
                || total_fee <= 0
                || StringUtils.isEmpty(spbill_create_ip)
                || StringUtils.isEmpty(trade_type)
                || StringUtils.isEmpty(notify_url)){
            logger.error("WxCodePayImpl.wxCodePay--> 参数字段为空");
            return wxResEntity;
        }
        String sign = WxPayUtil.getMd5Sign(wxEntity);
        wxEntity.setSign(sign);
        String payReString=null;
        try {
        	payReString = WxPayUtil.postXml(Configure.URL_UNIFIEDORDER, wxEntity,WXReqEntity.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        if(payReString!=null)
        {
        	wxResEntity = (WXResEntity)WxPayUtil.getObjectFromXML(payReString , WXResEntity.class);
        	logger.info("-----------------"+wxResEntity.toString()+"----------------");
        }
        return wxResEntity;
	}
	
	@Override
	public void create2code(HttpServletResponse response,String codeUrl) {
		if (codeUrl != null && !"".equals(codeUrl)) {
            ServletOutputStream stream = null;
            try {

                int width = 200;//图片的宽度
                int height = 200;//高度
                stream = response.getOutputStream();
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix m = writer.encode(codeUrl, BarcodeFormat.QR_CODE, height, width);
                MatrixToImageWriter.writeToStream(m, "png", stream);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
				e.printStackTrace();
			} finally {
                if (stream != null) {
                    try {
						stream.flush();
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        }
	}

}
