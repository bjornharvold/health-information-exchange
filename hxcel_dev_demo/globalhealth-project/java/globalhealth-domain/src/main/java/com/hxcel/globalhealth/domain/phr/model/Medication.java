/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.MedicationFrequencyCd;
import com.hxcel.globalhealth.domain.phr.model.enums.MedicationTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs(
        {
        @TypeDef(name = "medicationFrequency", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.MedicationFrequencyCd")
                }),
        @TypeDef(name = "medicalConditionType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd")
                }),
        @TypeDef(name = "medicationType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.MedicationTypeCd")
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
public class Medication extends AbstractPhrEntity implements Serializable {

    private MedicationFrequencyCd frequency;
    private Practitioner practitioner;
    private MedicalConditionTypeCd conditionType;
    private MedicationTypeCd medicationTypeCd;
    private PhrContact phrContact;
    private String medicationDosage;
    private String typeOther;
    private String frequencyOther;
    private String prescriptionNo;
    private String conditionTypeOther;

    @Type(type = "medicationType")
    public MedicationTypeCd getMedicationTypeCd() {
        return medicationTypeCd;
    }

    public void setMedicationTypeCd(MedicationTypeCd type) {
        this.medicationTypeCd = type;
    }

    @Type(type = "medicationFrequency")
    public MedicationFrequencyCd getFrequency() {
        return this.frequency;
    }

    public void setFrequency(MedicationFrequencyCd value) {
        this.frequency = value;
    }

    @ManyToOne
    public Practitioner getPractitioner() {
        return this.practitioner;
    }

    public void setPractitioner(Practitioner value) {
        this.practitioner = value;
    }

    @Type(type = "medicalConditionType")
    public MedicalConditionTypeCd getConditionType() {
        return this.conditionType;
    }


    public void setConditionType(MedicalConditionTypeCd value) {
        this.conditionType = value;
    }

    @ManyToOne
    public PhrContact getPhrContact() {
        return this.phrContact;
    }


    public void setPhrContact(PhrContact value) {
        this.phrContact = value;
    }

    @Type(type = "encryptedString")
    public String getMedicationDosage() {
        return this.medicationDosage;
    }


    public void setMedicationDosage(String value) {
        this.medicationDosage = value;
    }

    @Type(type = "encryptedString")
    public String getTypeOther() {
        return this.typeOther;
    }

    @Type(type = "encryptedString")
    public void setTypeOther(String value) {
        this.typeOther = value;
    }

    @Type(type = "encryptedString")
    public String getFrequencyOther() {
        return this.frequencyOther;
    }


    public void setFrequencyOther(String value) {
        this.frequencyOther = value;
    }

    @Type(type = "encryptedString")
    public String getPrescriptionNo() {
        return this.prescriptionNo;
    }


    public void setPrescriptionNo(String value) {
        this.prescriptionNo = value;
    }

    @Type(type = "encryptedString")
    public String getConditionTypeOther() {
        return this.conditionTypeOther;
    }


    public void setConditionTypeOther(String value) {
        this.conditionTypeOther = value;
    }

    
}
