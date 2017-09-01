package com.rainsoft.union.web.wxpay.util;


//import com.rainsoft.common.utils.SystemConfigUtil;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class Configure {
    //这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	//微信appid
	public static String wx_app_id = "wx180c24d579ecda51";


	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	public static String mchId = "1387324902";

	public static String URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";


	//微信安全秘钥
	public static String key = "ZsUOrKT25M45tTH3ke5tZ3rjICyDiQzR";



}
