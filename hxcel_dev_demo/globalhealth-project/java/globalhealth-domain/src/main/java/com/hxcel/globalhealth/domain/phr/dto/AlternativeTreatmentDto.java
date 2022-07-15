/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.AlternativeTreatmentTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 7, 2007
 * Time: 2:38:52 PM
 */
public class AlternativeTreatmentDto extends AbstractPhrDto {
    private MedicalConditionTypeCd medicalConditionTypeCd;
    private AlternativeTreatmentTypeCd alternativeTreatmentTypeCd;
    private String practitioner;
    private String medicalConditionOther;
    private String alternativeTreatmentOther;
    private Date startDate;
    private Date endDate;

    public MedicalConditionTypeCd getMedicalConditionTypeCd() {
        return medicalConditionTypeCd;
    }

    public void setMedicalConditionTypeCd(MedicalConditionTypeCd medicalConditionTypeCd) {
        this.medicalConditionTypeCd = medicalConditionTypeCd;
    }

    public AlternativeTreatmentTypeCd getAlternativeTreatmentTypeCd() {
        return alternativeTreatmentTypeCd;
    }

    public void setAlternativeTreatmentTypeCd(AlternativeTreatmentTypeCd alternativeTreatmentTypeCd) {
        this.alternativeTreatmentTypeCd = alternativeTreatmentTypeCd;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public String getMedicalConditionOther() {
        return medicalConditionOther;
    }

    public void setMedicalConditionOther(String medicalConditionOther) {
        this.medicalConditionOther = medicalConditionOther;
    }

    public String getAlternativeTreatmentOther() {
        return alternativeTreatmentOther;
    }

    public void setAlternativeTreatmentOther(String alternativeTreatmentOther) {
        this.alternativeTreatmentOther = alternativeTreatmentOther;
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
