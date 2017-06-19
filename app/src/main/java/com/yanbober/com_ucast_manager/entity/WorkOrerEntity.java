package com.yanbober.com_ucast_manager.entity;

import java.io.Serializable;

/**
 * Created by Allen on 2017/6/7.
 */

public class WorkOrerEntity implements Serializable{
    private String customerName= "";
    private String productType= "";
    private String truble= "";
    private String workOrderType= "";
    private String id= "";
    private String serviceman= "";
    private String handleWays= "";
    private String handleMsgs= "";
    private String date= "";
    private String alterman= "";
    private String zlterdate= "";
    private String zlter_msg= "";



    public WorkOrerEntity() {
    }

    public WorkOrerEntity(String customerName, String productType, String truble, String workOrderType, String id,
                          String serviceman,String handleWays, String handleMsgs,String date) {
        this.customerName = customerName;
        this.productType = productType;
        this.truble = truble;
        this.workOrderType = workOrderType;
        this.id = id;
        this.serviceman = serviceman;
        this.handleWays = handleWays;
        this.handleMsgs = handleMsgs;
        this.date = date;
    }

    public WorkOrerEntity(String customerName, String productType, String truble, String workOrderType, String id,
                          String serviceman, String handleWays, String handleMsgs, String date, String alterman,
                          String zlterdate) {
        this.customerName = customerName;
        this.productType = productType;
        this.truble = truble;
        this.workOrderType = workOrderType;
        this.id = id;
        this.serviceman = serviceman;
        this.handleWays = handleWays;
        this.handleMsgs = handleMsgs;
        this.date = date;
        this.alterman = alterman;
        this.zlterdate = zlterdate;
    }



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTruble() {
        return truble;
    }

    public void setTruble(String truble) {
        this.truble = truble;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandleWays() {
        return handleWays;
    }

    public void setHandleWays(String handleWays) {
        this.handleWays = handleWays;
    }

    public String getHandleMsgs() {
        return handleMsgs;
    }

    public void setHandleMsgs(String handleMsgs) {
        this.handleMsgs = handleMsgs;
    }

    public String getServiceman() {
        return serviceman;
    }

    public void setServiceman(String serviceman) {
        this.serviceman = serviceman;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAlterman() {
        return alterman;
    }

    public void setAlterman(String alterman) {
        this.alterman = alterman;
    }

    public String getZlterdate() {
        return zlterdate;
    }

    public void setZlterdate(String zlterdate) {
        this.zlterdate = zlterdate;
    }

    public String getZlter_msg() {
        return zlter_msg;
    }

    public void setZlter_msg(String zlter_msg) {
        this.zlter_msg = zlter_msg;
    }
}
