package com.rainsoft.union.web.wxpay.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.rainsoft.union.web.wxpay.model.WXReqEntity;
import com.thoughtworks.xstream.XStream;


public class WxPayUtil {
	private static Logger logger = LoggerFactory.getLogger(WxPayUtil.class);
	public static Map<String ,Object> readClassAttr2Map(WXReqEntity wxEntity) {
        Map<String ,Object> map = new HashMap<>();
        if(wxEntity == null){
            logger.error("WxEntity.readClassAttr2Str-->对象为空，wxEntity:" + wxEntity);
            return null;
        }
        Class cls = wxEntity.getClass();
        Field[] fields = cls.getDeclaredFields();//属性集合

        //读取当前对象属性
        fillFields2Map(map , fields , wxEntity);

        return map;
    }

    private static void fillFields2Map(Map<String ,Object> fieldsMap,Field[] fields,WXReqEntity wxEntity){
        Field temp = null;//属性对象
        PropertyDescriptor pd = null;//属性描述对象
        Method getMethod = null;//获得get方法
        String name = null; //属性名称
        Object value = null;//执行get方法返回一个属性值Object
        if(fields != null && fields.length >0) {
            try {
                for (int i = 0; i < fields.length; i++) {
                    temp = fields[i];
                    name = temp.getName();
                    pd = new PropertyDescriptor(name, WXReqEntity.class);
                    getMethod = pd.getReadMethod();//获得get方法
                    if (getMethod == null) {
                        value = null;
                        logger.error("getMethod is null,name:" + name + ",getMethod:" + getMethod);
                    } else {
                        value = getMethod.invoke(wxEntity);//执行get方法返回一个Object
                    }
                    //将非空数据放到map中
                    if (value != null && !"".equals(value) && !"null".equals(value)) {
                        fieldsMap.put(name, value);
                    }
                }
            } catch (IntrospectionException e) {
                logger.error("WxEntity.readClassAttr2Str-->获取属性描述失败，" + e.toString());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.error("WxEntity.readClassAttr2Str-->执行get方法失败，" + e.toString());
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                logger.error("WxEntity.readClassAttr2Str-->执行gat方法失败，" + e.toString());
                e.printStackTrace();
            }catch (Exception e){
                logger.error("WxEntity.readClassAttr2Str-->执行gat方法失败，" + e.toString());
                e.printStackTrace();
            }
        }
    }
    
    public static String postXml(String urlStr,Object xmlObj , Class tClass) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder("");

		if(StringUtils.isEmpty(urlStr) || xmlObj == null){
			logger.error("地址或参数为空,urlStr:"+urlStr+",xmlInfo:"+xmlObj);
			return null;
		}
		String xmlInfo = XmlUtils.getXmlStr4Bean(xmlObj, tClass);
		/*************************** 发送xml请求 **************************/
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String result = "";
		try{
			httpPost =  new HttpPost(urlStr);
			httpPost.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
			//得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(xmlInfo, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(postEntity);

			HttpResponse response = httpclient.execute(httpPost);
			response.getStatusLine().getStatusCode();
			result = EntityUtils.toString(response.getEntity(),"utf-8");
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			httpPost.abort();
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
    
    public static Object getObjectFromXML(String xml, Class tClass) {
        Object obj = null;
        if(!StringUtils.isEmpty(xml)){
            //将从API返回的XML数据映射到Java对象
            XStream xStreamForResponseData = new XStream();
            xStreamForResponseData.alias("xml", tClass);
            xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
            obj = xStreamForResponseData.fromXML(xml);
        }
        return obj;
    }
    
    /**
     * 随机获取字符串
     * 
     * @param length
     *            随机字符串长度
     * 
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
		if (length <= 0) {
		    return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
			'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
			'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
		    stringBuffer.append(randomChar[Math.abs(random.nextInt())
			    % randomChar.length]);
		}
		return stringBuffer.toString();
    }
    
    /**
     * 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
     *    特别注意以下重要规则：
     *    ◆ 参数名ASCII码从小到大排序（字典序）；
     *    ◆ 如果参数的值为空不参与签名；
     *    ◆ 参数名区分大小写；
     *    ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
     *    ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
     * 第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
     *   key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     * @param wxEntity
     * @return
     */
	public static String getMd5Sign(WXReqEntity wxEntity)
	{
		String sign = "";
        Map<String ,Object> paramMap = WxPayUtil.readClassAttr2Map(wxEntity);
        sign = MD5SignUtil.createSign(paramMap);
        return sign;
	}
}
