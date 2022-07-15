/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.spec.model.enums.PhoneTypeCd;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.utils.EnumUserType;

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
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.common.spec.model.enums.PhoneTypeCd")}
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

    private PhoneTypeCd phoneType = null;
    private String countryCode = null;
    private String areaCode = null;
    private String number = null;

    @Type(type = "phonetype")
    public PhoneTypeCd getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneTypeCd phoneType) {
        this.phoneType = phoneType;
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
