package com.study.distirbuteLock.model;

public class DistributeLock {
    private Integer id;

    private String bussinessCode;

    private String businessName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBussinessCode() {
        return bussinessCode;
    }

    public void setBussinessCode(String bussinessCode) {
        this.bussinessCode = bussinessCode == null ? null : bussinessCode.trim();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }
}