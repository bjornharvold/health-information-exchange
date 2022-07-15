/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.DentalExamReasonTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedDateAsStringType;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs(
        {
        @TypeDef(name = "reasontype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.DentalExamReasonTypeCd")
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
public class Dental extends AbstractPhrEntity implements Serializable {

    private DentalExamReasonTypeCd dentalExamReasonTypeCd;
    private Practitioner practitioner;
    private Date examDate;
    private String dentalExamReasonOther;
    private Boolean xrayTaken;

    @Type(type = "reasontype")
    public DentalExamReasonTypeCd getDentalExamReasonTypeCd() {
        return this.dentalExamReasonTypeCd;
    }


    public void setDentalExamReasonTypeCd(DentalExamReasonTypeCd value) {
        this.dentalExamReasonTypeCd = value;
    }

    @ManyToOne
    public Practitioner getPractitioner() {
        return this.practitioner;
    }


    public void setPractitioner(Practitioner value) {
        this.practitioner = value;
    }

    @Type(type = "encryptedDate")
    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    @Type(type = "encryptedString")
    public String getDentalExamReasonOther() {
        return this.dentalExamReasonOther;
    }

    public void setDentalExamReasonOther(String value) {
        this.dentalExamReasonOther = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getXrayTaken() {
        return this.xrayTaken;
    }

    public void setXrayTaken(Boolean value) {
        this.xrayTaken = value;
    }


    
}
