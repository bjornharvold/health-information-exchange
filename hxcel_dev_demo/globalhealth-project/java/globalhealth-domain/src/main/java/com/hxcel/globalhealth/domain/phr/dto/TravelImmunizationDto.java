/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 7:57:23 PM
 */
public class TravelImmunizationDto extends AbstractPhrDto {
    private CountryCd countryCd;
    private String vaccineName;
    private Date vaccineDate;
    private String vaccineDescription;

    public CountryCd getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(CountryCd countryCd) {
        this.countryCd = countryCd;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public Date getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(Date vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public String getVaccineDescription() {
        return vaccineDescription;
    }

    public void setVaccineDescription(String vaccineDescription) {
        this.vaccineDescription = vaccineDescription;
    }
}
