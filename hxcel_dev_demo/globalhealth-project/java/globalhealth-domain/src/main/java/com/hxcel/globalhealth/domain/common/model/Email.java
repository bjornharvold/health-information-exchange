/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.EmailTypeCd;

import javax.persistence.Entity;
import java.io.Serializable;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

/**
 * User: Bjorn Harvold
 * Date: Jun 12, 2006
 * Time: 7:18:21 PM
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "emailtype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.EmailTypeCd")}
        ),
        @TypeDef(
                name = "encryptedString",
                typeClass = EncryptedStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        ),
        @TypeDef(
                name = "encryptedBoolean",
                typeClass = EncryptedBooleanAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
public class Email extends AbstractEntity implements Serializable {
    private EmailTypeCd emailTypeCd;
    private String email;
    private Boolean primary = false;

    @Type(type = "emailtype")
    public EmailTypeCd getEmailTypeCd() {
        return emailTypeCd;
    }

    public void setEmailTypeCd(EmailTypeCd emailTypeCd) {
        this.emailTypeCd = emailTypeCd;
    }

    @Type(type = "encryptedString")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
