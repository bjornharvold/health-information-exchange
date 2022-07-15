/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.RelativeConditionTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.RelativeTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs(
        {
        @TypeDef(name = "relativeConditionType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.RelativeConditionTypeCd")
                }),
        @TypeDef(name = "relativeType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.RelativeTypeCd")
                }),
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
                }
)
public class FamilyHistory extends AbstractPhrEntity implements Serializable {

    private RelativeConditionTypeCd relativeConditionTypeCd;
    private RelativeTypeCd relativeTypeCd;
    private String relativeOther;
    private String relativeConditionOther;
    private Boolean alive;

    @Type(type = "relativeConditionType")
    public RelativeConditionTypeCd getRelativeConditionTypeCd() {
        return this.relativeConditionTypeCd;
    }

    public void setRelativeConditionTypeCd(RelativeConditionTypeCd value) {
        this.relativeConditionTypeCd = value;
    }

    @Type(type = "relativeType")
    public RelativeTypeCd getRelativeTypeCd() {
        return this.relativeTypeCd;
    }


    public void setRelativeTypeCd(RelativeTypeCd value) {
        this.relativeTypeCd = value;
    }

    @Type(type = "encryptedString")
    public String getRelativeOther() {
        return this.relativeOther;
    }

    public void setRelativeOther(String value) {
        this.relativeOther = value;
    }

    @Type(type = "encryptedString")
    public String getRelativeConditionOther() {
        return this.relativeConditionOther;
    }

    public void setRelativeConditionOther(String value) {
        this.relativeConditionOther = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getAlive() {
        return this.alive;
    }

    public void setAlive(Boolean value) {
        this.alive = value;
    }


    
}
