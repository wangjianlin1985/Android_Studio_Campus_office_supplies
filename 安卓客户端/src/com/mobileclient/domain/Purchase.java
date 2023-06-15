package com.mobileclient.domain;

import java.io.Serializable;

public class Purchase implements Serializable {
    /*购置id*/
    private int purchaseId;
    public int getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /*购置物品*/
    private String goodObj;
    public String getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(String goodObj) {
        this.goodObj = goodObj;
    }

    /*购置价格*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*购置数量*/
    private int buyCount;
    public int getBuyCount() {
        return buyCount;
    }
    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    /*购置金额*/
    private float totalMoney;
    public float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*入库时间*/
    private java.sql.Timestamp inDate;
    public java.sql.Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(java.sql.Timestamp inDate) {
        this.inDate = inDate;
    }

    /*经办人*/
    private String operatorMan;
    public String getOperatorMan() {
        return operatorMan;
    }
    public void setOperatorMan(String operatorMan) {
        this.operatorMan = operatorMan;
    }

    /*保管人*/
    private String keepMan;
    public String getKeepMan() {
        return keepMan;
    }
    public void setKeepMan(String keepMan) {
        this.keepMan = keepMan;
    }

    /*仓库*/
    private String storeHouse;
    public String getStoreHouse() {
        return storeHouse;
    }
    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

}