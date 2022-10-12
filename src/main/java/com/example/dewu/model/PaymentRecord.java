package com.example.dewu.model;

import java.util.Date;

public class PaymentRecord {
    /**
     * 主键id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 外部支付渠道主键id
     */
    private String channelPaymentId;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 支付金额
     */
    private Double amount;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 支付状态
     */
    private PaymentStatus payStatus;

    /**
     * 订单额外信息
     */
    private String extendStr;

    /**
     * 支付完成时间
     */
    private String payEndTime;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getChannelPaymentId() {
        return channelPaymentId;
    }

    public void setChannelPaymentId(String channelPaymentId) {
        this.channelPaymentId = channelPaymentId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PaymentStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PaymentStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getExtendStr() {
        return extendStr;
    }

    public void setExtendStr(String extendStr) {
        this.extendStr = extendStr;
    }

    public String getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(String payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}