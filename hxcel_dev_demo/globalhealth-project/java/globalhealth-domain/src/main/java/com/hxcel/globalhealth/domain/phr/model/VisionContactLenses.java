/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.phr.model.enums.VisionContactLensTypeCd;
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
        @TypeDef(name = "lenstype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.VisionContactLensTypeCd")
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
public class VisionContactLenses extends AbstractPhrEntity implements Serializable {

    private VisionContactLensTypeCd lensType;
    private Practitioner practitioner;
    private Date startDate;
    private Date endDate;
    private String typeOther;
    private String lensColor;
    private String pairsOrdered;
    private String lensDuration;
    private String solutionUsed;
    private String rightEyeCylinder;
    private String rightEyeSphere;
    private String rightEyePower;
    private String rightEyeAxis;
    private String rightEyeBc;
    private String leftEyeCylinder;
    private String leftEyeSphere;
    private String leftEyePower;
    private String leftEyeAxis;
    private String leftEyeBc;

    @Type(type = "lenstype")
    public VisionContactLensTypeCd getLensType() {
        return this.lensType;
    }


    public void setLensType(VisionContactLensTypeCd value) {
        this.lensType = value;
    }


    @ManyToOne
    public Practitioner getPractitioner() {
        return this.practitioner;
    }


    public void setPractitioner(Practitioner value) {
        this.practitioner = value;
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

    @Type(type = "encryptedString")
    public String getTypeOther() {
        return this.typeOther;
    }

    public void setTypeOther(String value) {
        this.typeOther = value;
    }

    @Type(type = "encryptedString")
    public String getLensColor() {
        return this.lensColor;
    }

    public void setLensColor(String value) {
        this.lensColor = value;
    }

    @Type(type = "encryptedString")
    public String getPairsOrdered() {
        return this.pairsOrdered;
    }

    public void setPairsOrdered(String value) {
        this.pairsOrdered = value;
    }

    @Type(type = "encryptedString")
    public String getLensDuration() {
        return this.lensDuration;
    }

    public void setLensDuration(String value) {
        this.lensDuration = value;
    }

    @Type(type = "encryptedString")
    public String getSolutionUsed() {
        return this.solutionUsed;
    }

    public void setSolutionUsed(String value) {
        this.solutionUsed = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyeCylinder() {
        return this.rightEyeCylinder;
    }

    public void setRightEyeCylinder(String value) {
        this.rightEyeCylinder = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyeSphere() {
        return this.rightEyeSphere;
    }

    public void setRightEyeSphere(String value) {
        this.rightEyeSphere = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyePower() {
        return this.rightEyePower;
    }

    public void setRightEyePower(String value) {
        this.rightEyePower = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyeAxis() {
        return this.rightEyeAxis;
    }

    public void setRightEyeAxis(String value) {
        this.rightEyeAxis = value;
    }

    @Type(type = "encryptedString")
    public String getRightEyeBc() {
        return this.rightEyeBc;
    }

    public void setRightEyeBc(String value) {
        this.rightEyeBc = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyeCylinder() {
        return this.leftEyeCylinder;
    }

    public void setLeftEyeCylinder(String value) {
        this.leftEyeCylinder = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyeSphere() {
        return this.leftEyeSphere;
    }

    public void setLeftEyeSphere(String value) {
        this.leftEyeSphere = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyePower() {
        return this.leftEyePower;
    }

    public void setLeftEyePower(String value) {
        this.leftEyePower = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyeAxis() {
        return this.leftEyeAxis;
    }

    public void setLeftEyeAxis(String value) {
        this.leftEyeAxis = value;
    }

    @Type(type = "encryptedString")
    public String getLeftEyeBc() {
        return this.leftEyeBc;
    }

    public void setLeftEyeBc(String value) {
        this.leftEyeBc = value;
    }
}
