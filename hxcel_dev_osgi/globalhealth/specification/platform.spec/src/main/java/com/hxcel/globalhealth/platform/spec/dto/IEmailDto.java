package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.model.enums.EmailTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 1:17:18 PM
 * Responsibility:
 */
public interface IEmailDto {
    EmailTypeCd getEmailTypeCd();

    void setEmailTypeCd(EmailTypeCd emailTypeCd);

    String getEmail();

    void setEmail(String email);

    Boolean getPrimary();

    void setPrimary(Boolean primary);
}
