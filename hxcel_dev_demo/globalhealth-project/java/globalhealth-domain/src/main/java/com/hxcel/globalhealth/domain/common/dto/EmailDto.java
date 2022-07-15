/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dto;

import com.hxcel.globalhealth.domain.common.model.enums.EmailTypeCd;
import com.hxcel.globalhealth.domain.common.dto.AbstractDto;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 2:19:48 PM
 */
public class EmailDto extends AbstractDto {
    private EmailTypeCd emailTypeCd;
    private String email;
    private Boolean primary = false;

    public EmailTypeCd getEmailTypeCd() {
        return emailTypeCd;
    }

    public void setEmailTypeCd(EmailTypeCd emailTypeCd) {
        this.emailTypeCd = emailTypeCd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
