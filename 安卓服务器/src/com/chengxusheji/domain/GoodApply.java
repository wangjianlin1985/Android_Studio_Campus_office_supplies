package com.chengxusheji.domain;

import java.sql.Timestamp;
public class GoodApply {
    /*申请id*/
    private int applyId;
    public int getApplyId() {
        return applyId;
    }
    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    /*审请的用品*/
    private Goods goodObj;
    public Goods getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }

    /*申请数量*/
    private int applyCount;
    public int getApplyCount() {
        return applyCount;
    }
    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    /*申请时间*/
    private String applyTime;
    public String getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    /*申请人*/
    private Person personObj;
    public Person getPersonObj() {
        return personObj;
    }
    public void setPersonObj(Person personObj) {
        this.personObj = personObj;
    }

    /*经办人*/
    private String handlePerson;
    public String getHandlePerson() {
        return handlePerson;
    }
    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }

    /*备注*/
    private String applyMemo;
    public String getApplyMemo() {
        return applyMemo;
    }
    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }

}