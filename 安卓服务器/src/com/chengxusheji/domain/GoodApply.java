package com.chengxusheji.domain;

import java.sql.Timestamp;
public class GoodApply {
    /*����id*/
    private int applyId;
    public int getApplyId() {
        return applyId;
    }
    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    /*�������Ʒ*/
    private Goods goodObj;
    public Goods getGoodObj() {
        return goodObj;
    }
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }

    /*��������*/
    private int applyCount;
    public int getApplyCount() {
        return applyCount;
    }
    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    /*����ʱ��*/
    private String applyTime;
    public String getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    /*������*/
    private Person personObj;
    public Person getPersonObj() {
        return personObj;
    }
    public void setPersonObj(Person personObj) {
        this.personObj = personObj;
    }

    /*������*/
    private String handlePerson;
    public String getHandlePerson() {
        return handlePerson;
    }
    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }

    /*��ע*/
    private String applyMemo;
    public String getApplyMemo() {
        return applyMemo;
    }
    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }

}