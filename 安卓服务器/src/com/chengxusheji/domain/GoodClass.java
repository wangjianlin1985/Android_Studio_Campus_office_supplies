package com.chengxusheji.domain;

import java.sql.Timestamp;
public class GoodClass {
    /*物品类别id*/
    private int goodClassId;
    public int getGoodClassId() {
        return goodClassId;
    }
    public void setGoodClassId(int goodClassId) {
        this.goodClassId = goodClassId;
    }

    /*物品类别名称*/
    private String goodClassName;
    public String getGoodClassName() {
        return goodClassName;
    }
    public void setGoodClassName(String goodClassName) {
        this.goodClassName = goodClassName;
    }

}