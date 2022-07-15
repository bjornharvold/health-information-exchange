/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.common.dto.*;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 7:25:40 PM
 */
public class PractitionerDto extends AbstractPhrDto {
    private String title;
    private ContactDto contact;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContactDto getContact() {
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
    }
}
