package com.rainsoft.union.web.businessmanage.model;

import java.io.Serializable;

public class CashierInfo implements Serializable {

    /**
     * 当前收银员
     */
    private static final long serialVersionUID = -5803856017502167264L;
    private String serviceCode; //场所Id
    private String cashier;//收银员姓名
    private String console;//控制台
    private String phoneNumber;//电话

    public CashierInfo() {

    }

    public CashierInfo(String cashier, String console, String phoneNumber) {
        this.cashier = cashier;
        this.console = console;
        this.phoneNumber = phoneNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
