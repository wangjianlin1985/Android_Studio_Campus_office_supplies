package com.chengxusheji.domain;

import java.sql.Timestamp;
public class GoodUse {
    /*领用id*/
    private int useId;
    public int getUseId() {
        return useId;
    }
    public void setUseId(int useId) {
        this.useId = useId;
    }

    /*领用物品*/
    private Goods goodObj;
    public Goods getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }

    /*领用数量*/
    private int useCount;
    public int getUseCount() {
        return useCount;
    }
    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    /*单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*金额*/
    private float totalMoney;
    public float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*领用时间*/
    private Timestamp useTime;
    public Timestamp getUseTime() {
        return useTime;
    }
    public void setUseTime(Timestamp useTime) {
        this.useTime = useTime;
    }

    /*领用人*/
    private String userMan;
    public String getUserMan() {
        return userMan;
    }
    public void setUserMan(String userMan) {
        this.userMan = userMan;
    }

    /*经办人*/
    private String operatorMan;
    public String getOperatorMan() {
        return operatorMan;
    }
    public void setOperatorMan(String operatorMan) {
        this.operatorMan = operatorMan;
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