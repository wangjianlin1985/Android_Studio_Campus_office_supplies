package com.mobileclient.domain;

import java.io.Serializable;

public class Purchase implements Serializable {
    /*����id*/
    private int purchaseId;
    public int getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    /*������Ʒ*/
    private String goodObj;
    public String getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(String goodObj) {
        this.goodObj = goodObj;
    }

    /*���ü۸�*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��������*/
    private int buyCount;
    public int getBuyCount() {
        return buyCount;
    }
    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    /*���ý��*/
    private float totalMoney;
    public float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*���ʱ��*/
    private java.sql.Timestamp inDate;
    public java.sql.Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(java.sql.Timestamp inDate) {
        this.inDate = inDate;
    }

    /*������*/
    private String operatorMan;
    public String getOperatorMan() {
        return operatorMan;
    }
    public void setOperatorMan(String operatorMan) {
        this.operatorMan = operatorMan;
    }

    /*������*/
    private String keepMan;
    public String getKeepMan() {
        return keepMan;
    }
    public void setKeepMan(String keepMan) {
        this.keepMan = keepMan;
    }

    /*�ֿ�*/
    private String storeHouse;
    public String getStoreHouse() {
        return storeHouse;
    }
    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

}