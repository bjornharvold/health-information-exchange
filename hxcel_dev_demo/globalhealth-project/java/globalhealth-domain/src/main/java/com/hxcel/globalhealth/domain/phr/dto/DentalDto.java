/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.DentalExamReasonTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 7, 2007
 * Time: 3:23:09 PM
 */
public class DentalDto extends AbstractPhrDto {
    private DentalExamReasonTypeCd dentalExamReasonTypeCd;
    private String practitioner;
    private Date examDate;
    private String dentalExamReasonOther;
    private Boolean xrayTaken;

    public DentalExamReasonTypeCd getDentalExamReasonTypeCd() {
        return dentalExamReasonTypeCd;
    }

    public void setDentalExamReasonTypeCd(DentalExamReasonTypeCd dentalExamReasonTypeCd) {
        this.dentalExamReasonTypeCd = dentalExamReasonTypeCd;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getDentalExamReasonOther() {
        return dentalExamReasonOther;
    }

    public void setDentalExamReasonOther(String dentalExamReasonOther) {
        this.dentalExamReasonOther = dentalExamReasonOther;
    }

    public Boolean getXrayTaken() {
        return xrayTaken;
    }

    public void setXrayTaken(Boolean xrayTaken) {
        this.xrayTaken = xrayTaken;
    }
}
