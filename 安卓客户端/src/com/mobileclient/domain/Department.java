package com.mobileclient.domain;

import java.io.Serializable;

public class Department implements Serializable {
    /*���ű��*/
    private int departmentId;
    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /*��������*/
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /*�������*/
    private String departmentType;
    public String getDepartmentType() {
        return departmentType;
    }
    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    /*��ע*/
    private String departmentMemo;
    public String getDepartmentMemo() {
        return departmentMemo;
    }
    public void setDepartmentMemo(String departmentMemo) {
        this.departmentMemo = departmentMemo;
    }

}