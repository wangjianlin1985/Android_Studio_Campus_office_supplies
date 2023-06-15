package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Goods {
    /*物品编号*/
    private String goodNo;
    public String getGoodNo() {
        return goodNo;
    }
    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }

    /*商品类别*/
    private GoodClass goodClassObj;
    public GoodClass getGoodClassObj() {
        return goodClassObj;
    }
    public void setGoodClassObj(GoodClass goodClassObj) {
        this.goodClassObj = goodClassObj;
    }

    /*物品名称*/
    private String goodName;
    public String getGoodName() {
        return goodName;
    }
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /*物品图片*/
    private String goodPhoto;
    public String getGoodPhoto() {
        return goodPhoto;
    }
    public void setGoodPhoto(String goodPhoto) {
        this.goodPhoto = goodPhoto;
    }

    /*规格型号*/
    private String specModel;
    public String getSpecModel() {
        return specModel;
    }
    public void setSpecModel(String specModel) {
        this.specModel = specModel;
    }

    /*计量单位*/
    private String measureUnit;
    public String getMeasureUnit() {
        return measureUnit;
    }
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    /*库存数量*/
    private int stockCount;
    public int getStockCount() {
        return stockCount;
    }
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
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

    /*仓库*/
    private String storeHouse;
    public String getStoreHouse() {
        return storeHouse;
    }
    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

    /*备注*/
    private String goodMemo;
    public String getGoodMemo() {
        return goodMemo;
    }
    public void setGoodMemo(String goodMemo) {
        this.goodMemo = goodMemo;
    }

}