/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.AdvanceDirectiveTypeCd;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 3:08:50 AM
 */
public class AdvanceDirectiveDto extends AbstractPhrDto {
    private String phrContact;
    private AdvanceDirectiveTypeCd advanceDirectiveType;

    public String getPhrContact() {
        return phrContact;
    }

    public void setPhrContact(String phrContact) {
        this.phrContact = phrContact;
    }

    public AdvanceDirectiveTypeCd getAdvanceDirectiveType() {
        return advanceDirectiveType;
    }

    public void setAdvanceDirectiveType(AdvanceDirectiveTypeCd advanceDirectiveType) {
        this.advanceDirectiveType = advanceDirectiveType;
    }
}
