/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.common.dto.AbstractRecordDto;
import com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 3:10:55 AM
 */
public class AbstractPhrDto extends AbstractRecordDto {
    private String phr;
    private PhrTypeCd type;

    public String getPhr() {
        return phr;
    }

    public void setPhr(String phr) {
        this.phr = phr;
    }

    public PhrTypeCd getType() {
        return type;
    }

    public void setType(PhrTypeCd type) {
        this.type = type;
    }
}
