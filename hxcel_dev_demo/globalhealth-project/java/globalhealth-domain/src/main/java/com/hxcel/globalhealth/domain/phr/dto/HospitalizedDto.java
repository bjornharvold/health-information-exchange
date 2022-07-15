/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 5:54:07 AM
 */
public class HospitalizedDto extends AbstractPhrDto {
    private MedicalConditionTypeCd medicalConditionTypeCd;
    private String medicalConditionOther;
    private Date startDate;
    private Date endDate;

    public MedicalConditionTypeCd getMedicalConditionTypeCd() {
        return medicalConditionTypeCd;
    }

    public void setMedicalConditionTypeCd(MedicalConditionTypeCd medicalConditionTypeCd) {
        this.medicalConditionTypeCd = medicalConditionTypeCd;
    }

    public String getMedicalConditionOther() {
        return medicalConditionOther;
    }

    public void setMedicalConditionOther(String medicalConditionOther) {
        this.medicalConditionOther = medicalConditionOther;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
