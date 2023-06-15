package com.mobileclient.domain;

import java.io.Serializable;

public class Department implements Serializable {
    /*部门编号*/
    private int departmentId;
    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /*部门名称*/
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /*部门类别*/
    private String departmentType;
    public String getDepartmentType() {
        return departmentType;
    }
    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    /*备注*/
    private String departmentMemo;
    public String getDepartmentMemo() {
        return departmentMemo;
    }
    public void setDepartmentMemo(String departmentMemo) {
        this.departmentMemo = departmentMemo;
    }

}