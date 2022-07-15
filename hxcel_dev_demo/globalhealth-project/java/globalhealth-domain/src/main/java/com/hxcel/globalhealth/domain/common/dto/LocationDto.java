/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dto;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.LocationTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 5:42:01 PM
 */
public class LocationDto extends AbstractDto {
    private CountryCd countryCd;
    private LocationTypeCd locationTypeCd;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String zip;
    private Boolean primary;

    public CountryCd getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(CountryCd countryCd) {
        this.countryCd = countryCd;
    }

    public LocationTypeCd getLocationTypeCd() {
        return locationTypeCd;
    }

    public void setLocationTypeCd(LocationTypeCd locationTypeCd) {
        this.locationTypeCd = locationTypeCd;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
