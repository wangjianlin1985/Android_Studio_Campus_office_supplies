package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Purchase {
    /*购置id*/
    private int purchaseId;
    public int getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /*购置物品*/
    private Goods goodObj;
    public Goods getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(Goods goodObj) {
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
    private Timestamp inDate;
    public Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(Timestamp inDate) {
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