package com.chengxusheji.domain;

import java.sql.Timestamp;
public class GoodUse {
    /*����id*/
    private int useId;
    public int getUseId() {
        return useId;
    }
    public void setUseId(int useId) {
        this.useId = useId;
    }

    /*������Ʒ*/
    private Goods goodObj;
    public Goods getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }

    /*��������*/
    private int useCount;
    public int getUseCount() {
        return useCount;
    }
    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    /*����*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*���*/
    private float totalMoney;
    public float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*����ʱ��*/
    private Timestamp useTime;
    public Timestamp getUseTime() {
        return useTime;
    }
    public void setUseTime(Timestamp useTime) {
        this.useTime = useTime;
    }

    /*������*/
    private String userMan;
    public String getUserMan() {
        return userMan;
    }
    public void setUserMan(String userMan) {
        this.userMan = userMan;
    }

    /*������*/
    private String operatorMan;
    public String getOperatorMan() {
        return operatorMan;
    }
    public void setOperatorMan(String operatorMan) {
        this.operatorMan = operatorMan;
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