package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Goods {
    /*��Ʒ���*/
    private String goodNo;
    public String getGoodNo() {
        return goodNo;
    }
    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }

    /*��Ʒ���*/
    private GoodClass goodClassObj;
    public GoodClass getGoodClassObj() {
        return goodClassObj;
    }
    public void setGoodClassObj(GoodClass goodClassObj) {
        this.goodClassObj = goodClassObj;
    }

    /*��Ʒ����*/
    private String goodName;
    public String getGoodName() {
        return goodName;
    }
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /*��ƷͼƬ*/
    private String goodPhoto;
    public String getGoodPhoto() {
        return goodPhoto;
    }
    public void setGoodPhoto(String goodPhoto) {
        this.goodPhoto = goodPhoto;
    }

    /*����ͺ�*/
    private String specModel;
    public String getSpecModel() {
        return specModel;
    }
    public void setSpecModel(String specModel) {
        this.specModel = specModel;
    }

    /*������λ*/
    private String measureUnit;
    public String getMeasureUnit() {
        return measureUnit;
    }
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    /*�������*/
    private int stockCount;
    public int getStockCount() {
        return stockCount;
    }
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
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

    /*�ֿ�*/
    private String storeHouse;
    public String getStoreHouse() {
        return storeHouse;
    }
    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

    /*��ע*/
    private String goodMemo;
    public String getGoodMemo() {
        return goodMemo;
    }
    public void setGoodMemo(String goodMemo) {
        this.goodMemo = goodMemo;
    }

}