package com.yjhl.billpass.facade.controller;

import com.yjhl.yqb.api.IBillpassService;
import com.yjhl.yqb.common.CommonResponse;
import com.yjhl.yqb.entity.billpass.BillpassChangeParams;
import com.yjhl.yqb.entity.billpass.BillpassChangeWater;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by caijun on 2017/3/2.
 */
@RestController
@RequestMapping("/billpass1")      //此controller不再需要。
public class BillpassController extends BaseController{

    @Autowired
    private IBillpassService billpassService;

    @GetMapping(value = "/getUserIndexBillpass/{userid}")
    private CommonResponse<BillpassChangeParams> getUserIndexBillpass(@PathVariable("userid") Integer userid)
    {
        return billpassService.getUserIndexBillpass(userid);
    }

    @RequestMapping(value = "/getCapitalDetail/{userid}",method = RequestMethod.GET)
    private CommonResponse getCapitalDetail(@PathVariable("userid") Integer userid)
    {
//        List<MoneyDetail> list = new ArrayList<MoneyDetail>();
//        for (int i =0 ;i<10;i++) {
//            MoneyDetail moneyDetail = new MoneyDetail();
//            moneyDetail.setAvailbleMoney("+100");
//            moneyDetail.setFrozenMoney("-500");
//            moneyDetail.setTotalMoney("+30000");
//            moneyDetail.setTradeDate(new Date());
//            list.add(moneyDetail);
//        }
//        return CommonResponse.ok(list);
        return billpassService.getCapitalDetail(userid);
    }
//    @RequestMapping(value = "/testTransaction",method = RequestMethod.POST)
//    @ApiOperation(value = "测试",httpMethod = "POST",notes = "测试")
//    private CommonResponse testTransaction()
//    {
////        billpassService.testTransaction();
////        return CommonResponse.ok();
//    }

    @RequestMapping(value = "/addOrderBillpass",method = RequestMethod.POST)
    @ApiOperation(value = "订单扣款接口",httpMethod = "POST",notes = "订单扣款接口")
    private CommonResponse addOrderBillpass(@RequestBody BillpassChangeParams billpassChangeParams)
    {
        return billpassService.addOrderRecord(billpassChangeParams);
    }


    @RequestMapping(value = "/getChangeWater",method = RequestMethod.POST)
    @ApiOperation(value = "获得交易流水",httpMethod = "POST",notes = "交易流水接口")
    private CommonResponse getChangeWater(@RequestBody BillpassChangeWater billpassChangeWater)
    {
        String tradeStartTime = billpassChangeWater.getTradeStartTime();
        String tradeEndTime = billpassChangeWater.getTradeEndTime();

        String dateReg = "(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        if(tradeStartTime!=null&&!"".equals(tradeStartTime)&&!tradeStartTime.matches(dateReg))
        {
            return CommonResponse.fail("交易开始时间格式不正确");
        }
        if(tradeEndTime!=null&&!"".equals(tradeEndTime)&&!tradeEndTime.matches(dateReg))
        {
            return CommonResponse.fail("交易结束时间格式不正确");
        }
        return billpassService.getChangeWater(billpassChangeWater);
    }
}
