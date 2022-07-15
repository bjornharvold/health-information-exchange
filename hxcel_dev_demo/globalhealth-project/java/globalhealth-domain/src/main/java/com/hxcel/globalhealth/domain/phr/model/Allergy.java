/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.AllergyReactionTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.AllergyTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.FrequencyTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.TreatmentTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate.type.EncryptedDateAsStringType;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs(
        {
        @TypeDef(name = "frequencyType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.FrequencyTypeCd")
                }),
        @TypeDef(name = "allergyReactionType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.AllergyReactionTypeCd")
                }),
        @TypeDef(name = "treatmentType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.TreatmentTypeCd")
                }),
        @TypeDef(name = "allergyType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.AllergyTypeCd")
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
public class Allergy extends AbstractPhrEntity implements Serializable {

    private FrequencyTypeCd frequencyTypeCd;
    private AllergyReactionTypeCd allergyReactionTypeCd;
    private TreatmentTypeCd treatmentTypeCd;
    private AllergyTypeCd allergyTypeCd;
    private String allergyTypeOther;
    private String allergyReactionOther;
    private String treatmentTypeOther;
    private Date startDate;
    private Date endDate;

    @Type(type = "frequencyType")
    public FrequencyTypeCd getFrequencyTypeCd() {
        return this.frequencyTypeCd;
    }


    public void setFrequencyTypeCd(FrequencyTypeCd value) {
        this.frequencyTypeCd = value;
    }

    @Type(type = "allergyReactionType")
    public AllergyReactionTypeCd getAllergyReactionTypeCd() {
        return this.allergyReactionTypeCd;
    }


    public void setAllergyReactionTypeCd(AllergyReactionTypeCd value) {
        this.allergyReactionTypeCd = value;
    }

    @Type(type = "treatmentType")
    public TreatmentTypeCd getTreatmentTypeCd() {
        return this.treatmentTypeCd;
    }


    public void setTreatmentTypeCd(TreatmentTypeCd value) {
        this.treatmentTypeCd = value;
    }

    @Type(type = "allergyType")
    public AllergyTypeCd getAllergyTypeCd() {
        return this.allergyTypeCd;
    }


    public void setAllergyTypeCd(AllergyTypeCd value) {
        this.allergyTypeCd = value;
    }

    @Type(type = "encryptedString")
    public String getAllergyTypeOther() {
        return this.allergyTypeOther;
    }


    public void setAllergyTypeOther(String value) {
        this.allergyTypeOther = value;
    }

    @Type(type = "encryptedString")
    public String getAllergyReactionOther() {
        return this.allergyReactionOther;
    }


    public void setAllergyReactionOther(String value) {
        this.allergyReactionOther = value;
    }

    @Type(type = "encryptedString")
    public String getTreatmentTypeOther() {
        return this.treatmentTypeOther;
    }


    public void setTreatmentTypeOther(String value) {
        this.treatmentTypeOther = value;
    }

    @Type(type = "encryptedDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Type(type = "encryptedDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
}
