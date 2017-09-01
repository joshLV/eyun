package com.rainsoft.union.web.wxpay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rainsoft.union.web.wxpay.model.WXKeyType;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * 微信签名相关
 *
 */
public class MD5SignUtil {
    private static Logger logger = LoggerFactory.getLogger(MD5SignUtil.class);

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(charsetname == null || "".equals(charsetname)){
                charsetname = "utf-8";
            }
            resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /************************************** 签名生成 *************************************/

    /**
     * 生成签名
     * 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
     *    特别注意以下重要规则：
     *    ◆ 参数名ASCII码从小到大排序（字典序）；
     *    ◆ 如果参数的值为空不参与签名；
     *    ◆ 参数名区分大小写；
     *    ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
     *    ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
     * 第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
     *   key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     * @param wxParam
     * @return 大写的签名信息
     */
    public static String createSign(Map<String,Object> wxParam){
        StringBuilder sb = new StringBuilder("");
        ArrayList<String> keys = new ArrayList<>(wxParam.keySet());
        Collections.sort(keys);
        String key = ""; //key 键
        String value = ""; //value 值
        for(int i = 0; i < keys.size(); ++i) {
            key = (String)keys.get(i);
            value = wxParam.get(key)+"";
            if( null != value &&  !"".equals(value)
                    && !"sign".equals(key) && !"key".equals(key)){
                sb.append((i == 0?"":"&") + key + "=" + value);
            }
        }
        sb.append("&key=" + Configure.key);//获取微信的公钥
        String sign = "";
        sign = MD5Encode(sb.toString(), WXKeyType.charset.getValue()).toUpperCase();
        logger.info("MD5SignUtil.createSign-->加密前："+sb.toString() + " ，加密后：&sign="+sign);
        return sign;
    }
}
