package com.rainsoft.union.web.businessmanage.model;

import com.rainsoft.base.common.utils.DecimalCalculate;
import com.rainsoft.base.common.utils.StringUtil;

import java.io.Serializable;

public class ShiftDetail implements Serializable {

    /**
     * 交接班详细
     */
    private static final long serialVersionUID = 6562973573792148983L;
    private String id;//记录id
    private String serialNumber;//设备序列号
    private String serviceCode;//场所id
    private String lastShiftPeople;//上次交班人员
    private String dutyEmployee;//当前交班人员
    private String lastShiftTime;//上次交班时间
    private String endShiftTime;//本次交接班时间
    private String classes;//班次
    private String console;//控制台
    private String atworkRemainingMoney;//上下班金额
    private String cardSaleIncome;//会员卡销售 本班会员卡销售收入+连锁会员卡销售收入
    private String cardUpIncome;//会员卡（开通） 本班会员卡充值收入（会员卡[开退]）+连锁会员卡充值收入
    private String atworkDepositMoney; //账户卡（开充值）上班押金（散户卡[开充退]）
    private String dutyTempIncome;//本班散户上网收入
    private String dutyTempDeposit;//本班散户押金
    private String dutyProductIncome;//商品销售收入
    private String sumCashIncome;//总收入
    private String dutyCashIncome;//实际收入
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

    public String getLastShiftPeople() {
        return lastShiftPeople;
    }

    public void setLastShiftPeople(String lastShiftPeople) {
        this.lastShiftPeople = lastShiftPeople;
    }

    public String getDutyEmployee() {
        return dutyEmployee;
    }

    public void setDutyEmployee(String dutyEmployee) {
        this.dutyEmployee = dutyEmployee;
    }

    public String getLastShiftTime() {
        return lastShiftTime;
    }

    public void setLastShiftTime(String lastShiftTime) {
        this.lastShiftTime = lastShiftTime;
    }

    public String getEndShiftTime() {
        return endShiftTime;
    }

    public void setEndShiftTime(String endShiftTime) {
        this.endShiftTime = endShiftTime;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getAtworkRemainingMoney() {
        if (StringUtil.isEmpty(atworkRemainingMoney)) {
            atworkRemainingMoney = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(atworkRemainingMoney), 100d)+"";
    }

    public void setAtworkRemainingMoney(String atworkRemainingMoney) {
        this.atworkRemainingMoney = atworkRemainingMoney;
    }

    public String getCardSaleIncome() {
        if (StringUtil.isEmpty(cardSaleIncome)) {
            cardSaleIncome = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(cardSaleIncome), 100d)+"";
    }

    public void setCardSaleIncome(String cardSaleIncome) {
        this.cardSaleIncome = cardSaleIncome;
    }

    public String getCardUpIncome() {
        if (StringUtil.isEmpty(cardUpIncome)) {
            cardUpIncome = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(cardUpIncome), 100d)+"";
    }

    public void setCardUpIncome(String cardUpIncome) {
        this.cardUpIncome = cardUpIncome;
    }

    public String getAtworkDepositMoney() {
        if (StringUtil.isEmpty(atworkDepositMoney)) {
            atworkDepositMoney = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(atworkDepositMoney), 100d)+"";
    }

    public void setAtworkDepositMoney(String atworkDepositMoney) {
        this.atworkDepositMoney = atworkDepositMoney;
    }

    public String getDutyTempIncome() {
        if (StringUtil.isEmpty(dutyTempIncome)) {
            dutyTempIncome = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(dutyTempIncome), 100d)+"";
    }

    public void setDutyTempIncome(String dutyTempIncome) {
        this.dutyTempIncome = dutyTempIncome;
    }

    public String getDutyTempDeposit() {
        if (StringUtil.isEmpty(dutyTempDeposit)) {
            dutyTempDeposit = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(dutyTempDeposit), 100d)+"";
    }

    public void setDutyTempDeposit(String dutyTempDeposit) {
        this.dutyTempDeposit = dutyTempDeposit;
    }

    public String getDutyProductIncome() {
        if (StringUtil.isEmpty(dutyProductIncome)) {
            dutyProductIncome = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(dutyProductIncome), 100d)+"";
    }

    public void setDutyProductIncome(String dutyProductIncome) {
        this.dutyProductIncome = dutyProductIncome;
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

    public String getDutyCashIncome() {
        if (StringUtil.isEmpty(dutyCashIncome)) {
            dutyCashIncome = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(dutyCashIncome), 100d)+"";
    }

    public void setDutyCashIncome(String dutyCashIncome) {
        this.dutyCashIncome = dutyCashIncome;
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
