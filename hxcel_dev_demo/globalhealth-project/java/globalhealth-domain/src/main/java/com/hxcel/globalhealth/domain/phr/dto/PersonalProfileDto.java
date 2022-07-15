/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.common.model.enums.SalutationCd;
import com.hxcel.globalhealth.domain.common.model.enums.SexCd;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.phr.model.enums.*;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 10:51:12 AM
 */
public class PersonalProfileDto extends AbstractPhrDto {
    private RaceCd raceCd;
    private LanguageCd primaryLanguageCd;
    private MaritalStatusCd maritalStatusCd;
    private ReligionCd religionCd;
    private CountryCd nationalityCd;
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
    private SexCd sexCd;
    private SalutationCd salutationCd;

    public RaceCd getRaceCd() {
        return raceCd;
    }

    public void setRaceCd(RaceCd raceCd) {
        this.raceCd = raceCd;
    }

    public LanguageCd getPrimaryLanguageCd() {
        return primaryLanguageCd;
    }

    public void setPrimaryLanguageCd(LanguageCd primaryLanguageCd) {
        this.primaryLanguageCd = primaryLanguageCd;
    }

    public MaritalStatusCd getMaritalStatusCd() {
        return maritalStatusCd;
    }

    public void setMaritalStatusCd(MaritalStatusCd maritalStatusCd) {
        this.maritalStatusCd = maritalStatusCd;
    }

    public ReligionCd getReligionCd() {
        return religionCd;
    }

    public void setReligionCd(ReligionCd religionCd) {
        this.religionCd = religionCd;
    }

    public CountryCd getNationalityCd() {
        return nationalityCd;
    }

    public void setNationalityCd(CountryCd nationalityCd) {
        this.nationalityCd = nationalityCd;
    }

    public BloodTypeCd getBloodTypeCd() {
        return bloodTypeCd;
    }

    public void setBloodTypeCd(BloodTypeCd bloodTypeCd) {
        this.bloodTypeCd = bloodTypeCd;
    }

    public String getChildrenNo() {
        return childrenNo;
    }

    public void setChildrenNo(String childrenNo) {
        this.childrenNo = childrenNo;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getIdentifyingMarks() {
        return identifyingMarks;
    }

    public void setIdentifyingMarks(String identifyingMarks) {
        this.identifyingMarks = identifyingMarks;
    }

    public String getBloodTypeOther() {
        return bloodTypeOther;
    }

    public void setBloodTypeOther(String bloodTypeOther) {
        this.bloodTypeOther = bloodTypeOther;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public SexCd getSexCd() {
        return sexCd;
    }

    public void setSexCd(SexCd sexCd) {
        this.sexCd = sexCd;
    }

    public SalutationCd getSalutationCd() {
        return salutationCd;
    }

    public void setSalutationCd(SalutationCd salutationCd) {
        this.salutationCd = salutationCd;
    }

}
