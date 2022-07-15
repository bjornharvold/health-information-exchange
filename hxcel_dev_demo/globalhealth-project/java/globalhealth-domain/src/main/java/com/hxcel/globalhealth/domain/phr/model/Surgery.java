/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.SurgeryTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate.type.EncryptedDateAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs(
        {
        @TypeDef(name = "surgerytype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.SurgeryTypeCd")
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
        ),
        @TypeDef(
                name = "encryptedDate",
                typeClass = EncryptedDateAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
public class Surgery extends AbstractPhrEntity implements Serializable {

    private SurgeryTypeCd surgeryType;
    private Practitioner practitioner;
    private String surgeryOther;
    private Date surgeryDate;
    private String reason;

    @Type(type = "surgerytype")
    public SurgeryTypeCd getSurgeryType() {
        return this.surgeryType;
    }

    public void setSurgeryType(SurgeryTypeCd value) {
        this.surgeryType = value;
    }

    @ManyToOne
    public Practitioner getPractitioner() {
        return this.practitioner;
    }

    public void setPractitioner(Practitioner value) {
        this.practitioner = value;
    }

    @Type(type = "encryptedString")
    public String getSurgeryOther() {
        return this.surgeryOther;
    }

    public void setSurgeryOther(String value) {
        this.surgeryOther = value;
    }

    @Type(type = "encryptedDate")
    public Date getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(Date surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    @Type(type = "encryptedString")
    public String getReason() {
        return this.reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }

    
}
