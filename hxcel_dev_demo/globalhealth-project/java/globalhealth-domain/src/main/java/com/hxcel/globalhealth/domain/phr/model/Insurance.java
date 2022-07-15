/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs({
        @TypeDef(name = "rstatus", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordStatusCd")
                }),
        @TypeDef(name = "rtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordTypeCd")
                }),
        @TypeDef(name = "entitytype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")
                }),
        @TypeDef(name = "phrtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd")
                }),
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
        })

public class Insurance extends AbstractPhrEntity implements Serializable {

    private PhrContact phrContact;
    private String name;
    private String insuranceType;
    private String insuranceNo;
    private String insuranceGroupNo;

    @ManyToOne
    public PhrContact getPhrContact() {
        return this.phrContact;
    }

    public void setPhrContact(PhrContact value) {
        this.phrContact = value;
    }

    @Type(type = "encryptedString")
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Type(type = "encryptedString")
    public String getInsuranceType() {
        return this.insuranceType;
    }

    public void setInsuranceType(String value) {
        this.insuranceType = value;
    }

    @Type(type = "encryptedString")
    public String getInsuranceNo() {
        return this.insuranceNo;
    }

    public void setInsuranceNo(String value) {
        this.insuranceNo = value;
    }

    @Type(type = "encryptedString")
    public String getInsuranceGroupNo() {
        return this.insuranceGroupNo;
    }

    public void setInsuranceGroupNo(String value) {
        this.insuranceGroupNo = value;
    }

    
}
