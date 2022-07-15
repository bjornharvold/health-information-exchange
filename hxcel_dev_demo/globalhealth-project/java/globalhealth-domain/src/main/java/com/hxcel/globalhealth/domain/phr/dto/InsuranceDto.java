/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 10:15:11 AM
 */
public class InsuranceDto extends AbstractPhrDto {
    private String phrContact;
    private String name;
    private String insuranceType;
    private String insuranceNo;
    private String insuranceGroupNo;

    public String getPhrContact() {
        return phrContact;
    }

    public void setPhrContact(String phrContact) {
        this.phrContact = phrContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getInsuranceGroupNo() {
        return insuranceGroupNo;
    }

    public void setInsuranceGroupNo(String insuranceGroupNo) {
        this.insuranceGroupNo = insuranceGroupNo;
    }
}
