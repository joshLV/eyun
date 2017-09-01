//package com.yjhl.billpass.facade.consumers;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.aliyun.openservices.ons.api.Action;
//import com.aliyun.openservices.ons.api.ConsumeContext;
//import com.aliyun.openservices.ons.api.Message;
//import com.yjhl.yqb.aliyunmq.AbstractConsumer;
//import com.yjhl.yqb.aliyunmq.annotations.Tag;
//import com.yjhl.yqb.api.IBillpassService;
//import com.yjhl.yqb.entity.billpass.BillpassChangeParams;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Service;
//
///**
// * Created by caijun on 2017/3/7.
// */
//@Tag("ADD_ORDER_FAIL")
//public class AddOrderFailConsumer extends AbstractConsumer {
//    private static final Log log = LogFactory.getLog(AddOrderConsumer.class);
//    private IBillpassService billpassService;
//
//    public Action consume(Message message, ConsumeContext consumeContext) {
//
//        try {
//            log.info("Receive: " + message.getMsgID() + " tag:" + message.getTag());
//            String content = new String(message.getBody(), "utf-8");
//            JSONObject jsonObject = JSON.parseObject(content);
//            BillpassChangeParams billpassChangeParams = JSONObject.parseObject(jsonObject.toJSONString(), BillpassChangeParams.class);
//            billpassService.addOrderFailRecord(billpassChangeParams);
//            return Action.CommitMessage;
//        } catch (Exception e) {
//            log.error(message.getMsgID() + "消费失败：" + e.getMessage());
//            e.printStackTrace();
//            return Action.ReconsumeLater;
//        }
//    }
//}
