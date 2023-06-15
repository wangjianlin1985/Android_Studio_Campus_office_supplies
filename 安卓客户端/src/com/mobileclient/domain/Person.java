package com.mobileclient.domain;

import java.io.Serializable;

public class Person implements Serializable {
    /*人员编号*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*所在部门*/
    private int departmentObj;
    public int getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(int departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    private java.sql.Timestamp bornDate;
    public java.sql.Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(java.sql.Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}