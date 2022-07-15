/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
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
        @TypeDef(name = "language", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.LanguageCd")
                }),
        @TypeDef(name = "maritalStatus", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.MaritalStatusCd")
                }),
        @TypeDef(name = "religion", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.ReligionCd")
                }),
        @TypeDef(name = "race", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.RaceCd")
                }),
        @TypeDef(name = "bloodType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.BloodTypeCd")
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
public class PersonalProfile extends AbstractPhrEntity implements Serializable {
    private RaceCd raceCd;
    private LanguageCd primaryLanguageCd;
    private MaritalStatusCd maritalStatusCd;
    private ReligionCd religionCd;
    private Country nationalityCd;
    private BloodTypeCd bloodTypeCd;
    private String childrenNo;
    private String uniqueId;
    private String driverLicenseNo;
    private String passportNo;
    private String birthPlace;
    private String motherMaidenName;
    private String height;
    private String weight;
    private String eyeColor;
    private String hairColor;
    private String identifyingMarks;
    private String bloodTypeOther;
    private Date dob;

    @Type(type = "race")
    public RaceCd getRaceCd() {
        return this.raceCd;
    }

    public void setRaceCd(RaceCd value) {
        this.raceCd = value;
    }

    @Type(type = "language")
    public LanguageCd getPrimaryLanguageCd() {
        return this.primaryLanguageCd;
    }


    public void setPrimaryLanguageCd(LanguageCd value) {
        this.primaryLanguageCd = value;
    }

    @Type(type = "maritalStatus")
    public MaritalStatusCd getMaritalStatusCd() {
        return this.maritalStatusCd;
    }


    public void setMaritalStatusCd(MaritalStatusCd value) {
        this.maritalStatusCd = value;
    }

    @Type(type = "religion")
    public ReligionCd getReligionCd() {
        return this.religionCd;
    }


    public void setReligionCd(ReligionCd value) {
        this.religionCd = value;
    }

    @ManyToOne
    public Country getNationalityCd() {
        return this.nationalityCd;
    }


    public void setNationalityCd(Country value) {
        this.nationalityCd = value;
    }

    @Type(type = "bloodType")
    public BloodTypeCd getBloodTypeCd() {
        return this.bloodTypeCd;
    }


    public void setBloodTypeCd(BloodTypeCd value) {
        this.bloodTypeCd = value;
    }

    @Type(type = "encryptedString")
    public String getChildrenNo() {
        return this.childrenNo;
    }


    public void setChildrenNo(String value) {
        this.childrenNo = value;
    }

    @Type(type = "encryptedString")
    public String getUniqueId() {
        return this.uniqueId;
    }


    public void setUniqueId(String value) {
        this.uniqueId = value;
    }

    @Type(type = "encryptedString")
    public String getDriverLicenseNo() {
        return this.driverLicenseNo;
    }


    public void setDriverLicenseNo(String value) {
        this.driverLicenseNo = value;
    }

    @Type(type = "encryptedString")
    public String getPassportNo() {
        return this.passportNo;
    }


    public void setPassportNo(String value) {
        this.passportNo = value;
    }

    @Type(type = "encryptedString")
    public String getBirthPlace() {
        return this.birthPlace;
    }


    public void setBirthPlace(String value) {
        this.birthPlace = value;
    }

    @Type(type = "encryptedString")
    public String getMotherMaidenName() {
        return this.motherMaidenName;
    }


    public void setMotherMaidenName(String value) {
        this.motherMaidenName = value;
    }

    @Type(type = "encryptedString")
    public String getHeight() {
        return this.height;
    }


    public void setHeight(String value) {
        this.height = value;
    }

    @Type(type = "encryptedString")
    public String getWeight() {
        return this.weight;
    }


    public void setWeight(String value) {
        this.weight = value;
    }

    @Type(type = "encryptedString")
    public String getEyeColor() {
        return this.eyeColor;
    }


    public void setEyeColor(String value) {
        this.eyeColor = value;
    }

    @Type(type = "encryptedString")
    public String getHairColor() {
        return this.hairColor;
    }


    public void setHairColor(String value) {
        this.hairColor = value;
    }

    @Type(type = "encryptedString")
    public String getIdentifyingMarks() {
        return this.identifyingMarks;
    }


    public void setIdentifyingMarks(String value) {
        this.identifyingMarks = value;
    }

    @Type(type = "encryptedString")
    public String getBloodTypeOther() {
        return this.bloodTypeOther;
    }


    public void setBloodTypeOther(String value) {
        this.bloodTypeOther = value;
    }

    @Type(type = "encryptedDate")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    
}
