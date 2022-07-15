/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 10:26:17 AM
 */
public class MedicalConditionDto extends AbstractPhrDto {
    private MedicalConditionTypeCd medicalConditionTypeCd;
    private String practitioner;
    private Boolean currentMedicalCondition;
    private String medicalConditionOther;
    private Date diagnosisDate;
    private Date endDate;

    public MedicalConditionTypeCd getMedicalConditionTypeCd() {
        return medicalConditionTypeCd;
    }

    public void setMedicalConditionTypeCd(MedicalConditionTypeCd medicalConditionTypeCd) {
        this.medicalConditionTypeCd = medicalConditionTypeCd;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public Boolean getCurrentMedicalCondition() {
        return currentMedicalCondition;
    }

    public void setCurrentMedicalCondition(Boolean currentMedicalCondition) {
        this.currentMedicalCondition = currentMedicalCondition;
    }

    public String getMedicalConditionOther() {
        return medicalConditionOther;
    }

    public void setMedicalConditionOther(String medicalConditionOther) {
        this.medicalConditionOther = medicalConditionOther;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
