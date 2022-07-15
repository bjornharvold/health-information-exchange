/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd;
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
        @TypeDef(name = "medicalConditionType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd")
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
public class MedicalCondition extends AbstractPhrEntity implements Serializable {

    private MedicalConditionTypeCd medicalConditionTypeCd;
    private Practitioner practitioner;
    private Boolean currentMedicalCondition;
    private String medicalConditionOther;
    private Date diagnosisDate;
    private Date endDate;

    @Type(type = "medicalConditionType")
    public MedicalConditionTypeCd getMedicalConditionTypeCd() {
        return this.medicalConditionTypeCd;
    }

    public void setMedicalConditionTypeCd(MedicalConditionTypeCd value) {
        this.medicalConditionTypeCd = value;
    }

    @ManyToOne
    public Practitioner getPractitioner() {
        return this.practitioner;
    }

    public void setPractitioner(Practitioner value) {
        this.practitioner = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getCurrentMedicalCondition() {
        return this.currentMedicalCondition;
    }

    public void setCurrentMedicalCondition(Boolean value) {
        this.currentMedicalCondition = value;
    }

    @Type(type = "encryptedString")
    public String getMedicalConditionOther() {
        return this.medicalConditionOther;
    }

    public void setMedicalConditionOther(String value) {
        this.medicalConditionOther = value;
    }

    @Type(type = "encryptedDate")
    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    @Type(type = "encryptedDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
}
