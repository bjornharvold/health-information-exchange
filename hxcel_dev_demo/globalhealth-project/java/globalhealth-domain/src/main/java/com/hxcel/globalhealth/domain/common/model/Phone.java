/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.PhoneTypeCd;

import javax.persistence.Entity;
import java.io.Serializable;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.jasypt.hibernate.type.EncryptedStringType;

/**
 * User: Bjorn Harvold
 * Date: Jun 12, 2006
 * Time: 7:18:21 PM
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "phonetype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.PhoneTypeCd")}
        ),
        @TypeDef(
                name = "encryptedString",
                typeClass = EncryptedStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
public class Phone extends AbstractEntity implements Serializable {

    private PhoneTypeCd phoneTypeCd = null;
    private String countryCode = null;
    private String areaCode = null;
    private String number = null;

    @Type(type = "phonetype")
    public PhoneTypeCd getPhoneTypeCd() {
        return phoneTypeCd;
    }

    public void setPhoneTypeCd(PhoneTypeCd phoneTypeCd) {
        this.phoneTypeCd = phoneTypeCd;
    }

    @Type(type = "encryptedString")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Type(type = "encryptedString")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Type(type = "encryptedString")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
