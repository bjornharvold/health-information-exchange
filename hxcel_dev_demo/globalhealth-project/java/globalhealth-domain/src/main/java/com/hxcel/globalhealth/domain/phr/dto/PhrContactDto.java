/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.common.model.enums.ContactTypeCd;
import com.hxcel.globalhealth.domain.common.dto.*;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 3:15:56 AM
 */
public class PhrContactDto extends AbstractPhrDto {
    private ContactDto contact;
    private ContactTypeCd contactType;
    private String contactTypeOther;

    public ContactDto getContact() {
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
    }

    public ContactTypeCd getContactType() {
        return contactType;
    }

    public void setContactType(ContactTypeCd contactType) {
        this.contactType = contactType;
    }

    public String getContactTypeOther() {
        return contactTypeOther;
    }

    public void setContactTypeOther(String contactTypeOther) {
        this.contactTypeOther = contactTypeOther;
    }
    
}
