/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.VisionNonPrescriptionGlassesTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.VisionPrescriptionGlassesTypeCd;
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
        @TypeDef(name = "nonPrescriptionGlassesType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.VisionNonPrescriptionGlassesTypeCd")
                }),
        @TypeDef(name = "prescriptionGlassesType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.VisionPrescriptionGlassesTypeCd")
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
public class VisionGlasses extends AbstractPhrEntity implements Serializable {

    private VisionNonPrescriptionGlassesTypeCd nonPrescriptionType;
    private Practitioner practitioner;
    private VisionPrescriptionGlassesTypeCd prescriptionType;
    private Date startDate;
    private String prescriptionOther;
    private String nonPrescriptionOther;
    private String rightEyeSpherePower;
    private String rightEyeCylinderPower;
    private String leftEyeSpherePower;
    private String leftEyeCylinderPower;
    private String addPowerBifocals;
    private Date endDate;

    @Type(type = "nonPrescriptionGlassesType")
    public VisionNonPrescriptionGlassesTypeCd getNonPrescriptionType() {
        return this.nonPrescriptionType;
    }

    public void setNonPrescriptionType(VisionNonPrescriptionGlassesTypeCd value) {
        this.nonPrescriptionType = value;
    }

    @ManyToOne
    public Practitioner getPractitioner() {
        return this.practitioner;
    }


    public void setPractitioner(Practitioner value) {
        this.practitioner = value;
    }

    @Type(type = "prescriptionGlassesType")
    public VisionPrescriptionGlassesTypeCd getPrescriptionType() {
        return this.prescriptionType;
    }

    public void setPrescriptionType(
            VisionPrescriptionGlassesTypeCd value) {
        this.prescriptionType = value;
    }

    @Type(type = "encryptedString")
    public String getPrescriptionOther() {
        return this.prescriptionOther;
    }

    public void setPrescriptionOther(String value) {
        this.prescriptionOther = value;
    }

    @Type(type = "encryptedString")
    public String getNonPrescriptionOther() {
        return this.nonPrescriptionOther;
    }

    public void setNonPrescriptionOther(String value) {
        this.nonPrescriptionOther = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyeSpherePower() {
        return this.rightEyeSpherePower;
    }


    public void setRightEyeSpherePower(String value) {
        this.rightEyeSpherePower = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyeCylinderPower() {
        return this.rightEyeCylinderPower;
    }


    public void setRightEyeCylinderPower(String value) {
        this.rightEyeCylinderPower = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyeSpherePower() {
        return this.leftEyeSpherePower;
    }


    public void setLeftEyeSpherePower(String value) {
        this.leftEyeSpherePower = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyeCylinderPower() {
        return this.leftEyeCylinderPower;
    }


    public void setLeftEyeCylinderPower(String value) {
        this.leftEyeCylinderPower = value;
    }

    @Type(type = "encryptedString")
    public String getAddPowerBifocals() {
        return this.addPowerBifocals;
    }


    public void setAddPowerBifocals(String value) {
        this.addPowerBifocals = value;
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
