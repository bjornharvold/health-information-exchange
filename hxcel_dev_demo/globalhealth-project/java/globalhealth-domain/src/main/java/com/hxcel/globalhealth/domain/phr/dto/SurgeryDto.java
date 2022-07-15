/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.SurgeryTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 7:40:52 PM
 */
public class SurgeryDto extends AbstractPhrDto {
    private SurgeryTypeCd surgeryType;
    private String practitioner;
    private String surgeryOther;
    private Date surgeryDate;
    private String reason;

    public SurgeryTypeCd getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(SurgeryTypeCd surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public String getSurgeryOther() {
        return surgeryOther;
    }

    public void setSurgeryOther(String surgeryOther) {
        this.surgeryOther = surgeryOther;
    }

    public Date getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(Date surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
