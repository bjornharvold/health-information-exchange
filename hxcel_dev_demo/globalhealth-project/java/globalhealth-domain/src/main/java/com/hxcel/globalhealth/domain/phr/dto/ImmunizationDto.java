/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.ImmunizationTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 9:58:27 AM
 */
public class ImmunizationDto extends AbstractPhrDto {
    private ImmunizationTypeCd immunizationTypeCd;
    private Date date;

    public ImmunizationTypeCd getImmunizationTypeCd() {
        return immunizationTypeCd;
    }

    public void setImmunizationTypeCd(ImmunizationTypeCd immunizationTypeCd) {
        this.immunizationTypeCd = immunizationTypeCd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
