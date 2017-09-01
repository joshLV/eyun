package com.rainsoft.union.web.yeepay.service;

import com.rainsoft.union.web.yeepay.model.QueryResult;
import com.rainsoft.union.web.yeepay.model.RefundResult;


public interface IPaymentForOnlineService {

    /**
     * 生成hmac方法
     * 业务类型
     * @param p0_Cmd
     * 商户编号
     * @param p1_MerId
     * 商户订单号
     * @param p2_Order
     * 支付金额
     * @param p3_Amt
     * 交易币种
     * @param p4_Cur
     * 商品名称
     * @param p5_Pid
     * 商品种类
     * @param p6_Pcat
     * 商品描述
     * @param p7_Pdesc
     * 商户接收支付成功数据的地址
     * @param p8_Url
     * 送货地址
     * @param p9_SAF
     * 商户扩展信息
     * @param pa_MP
     * 银行编码
     * @param pd_FrpId
     * 应答机制
     * @param pr_NeedResponse
     * 商户密钥
     * @param keyValue
     * @return
     */
    public String getReqMd5HmacForOnlinePayment(String p0_Cmd,String p1_MerId,
                                                       String p2_Order, String p3_Amt, String p4_Cur,String p5_Pid, String p6_Pcat,
                                                       String p7_Pdesc,String p8_Url, String p9_SAF,String pa_MP,String pd_FrpId,
                                                       String pr_NeedResponse,String keyValue);



    /**
     * 订单查询请求参数
     * 该方法是根据《易宝支付产品通用接口（HTML版）文档 v3.0》怎样查询订单进行的封装
     * 具体参数含义请仔细阅读《易宝支付产品通用接口（HTML版）文档 v3.0》
     * 商户订单号
     * @param p2_Order
     * @return queryResult
     */
    public QueryResult queryByOrder(String p2_Order);

    /**
     * 订单退款请求参数
     * 方法是根据《易宝支付产品通用接口（HTML版）文档 v3.0》退款如何操作进行的封装
     * 具体参数含义请仔细阅读《易宝支付产品通用接口（HTML版）文档 v3.0》
     * 易宝支付交易流水号
     * @param pb_TrxId
     * 退款金额
     * @param p3_Amt
     * 交易币种
     * @param p4_Cur
     * 退款说明
     * @param p5_Desc
     * @return refundResult
     */
    public RefundResult refundByTrxId(String pb_TrxId,String p3_Amt,String p4_Cur,String p5_Desc);

    /**
     * 返回校验hmac方法
     *
     * @param hmac
     * 商户编号
     * @param p1_MerId
     * 业务类型
     * @param r0_Cmd
     * 支付结果
     * @param r1_Code
     * 易宝支付交易流水号
     * @param r2_TrxId
     * 支付金额
     * @param r3_Amt
     * 交易币种
     * @param r4_Cur
     * 商品名称
     * @param r5_Pid
     * 商户订单号
     * @param r6_Order
     * 易宝支付会员ID
     * @param r7_Uid
     * 商户扩展信息
     * @param r8_MP
     * 交易结果返回类型
     * @param r9_BType
     * 交易结果返回类型
     * @param keyValue
     * @return
     */
    public boolean verifyCallback(String hmac, String p1_MerId,
                                         String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt,
                                         String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid,
                                         String r8_MP, String r9_BType, String keyValue);

}