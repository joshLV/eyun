package com.rainsoft.union.web.businessmanage.model;

import com.rainsoft.base.common.utils.DecimalCalculate;
import com.rainsoft.base.common.utils.StringUtil;

import java.io.Serializable;

public class ShiftRecord implements Serializable {

    /**
     * 交接班记录
     */
    private static final long serialVersionUID = -4362484680261469874L;
    private String id;//记录id
    private String serialNumber;//设备序列号
    private String serviceCode;//场所Id
    private String endShiftTime;//交接班时间
    private String console;//控制台
    private String sumCashIncome;//总收入
    private String handOverMoney;//上缴
    private String leaveNextMoney;//留下

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getEndShiftTime() {
        return endShiftTime;
    }

    public void setEndShiftTime(String endShiftTime) {
        this.endShiftTime = endShiftTime;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getSumCashIncome() {
        if (StringUtil.isEmpty(sumCashIncome)) {
            sumCashIncome = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(sumCashIncome), 100d)+"";
    }

    public void setSumCashIncome(String sumCashIncome) {
        this.sumCashIncome = sumCashIncome;
    }

    public String getHandOverMoney() {
        if (StringUtil.isEmpty(handOverMoney)) {
            handOverMoney = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(handOverMoney), 100d)+"";
    }

    public void setHandOverMoney(String handOverMoney) {
        this.handOverMoney = handOverMoney;
    }

    public String getLeaveNextMoney() {
        if (StringUtil.isEmpty(leaveNextMoney)) {
            leaveNextMoney = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(leaveNextMoney), 100d)+"";
    }

    public void setLeaveNextMoney(String leaveNextMoney) {
        this.leaveNextMoney = leaveNextMoney;
    }
}
