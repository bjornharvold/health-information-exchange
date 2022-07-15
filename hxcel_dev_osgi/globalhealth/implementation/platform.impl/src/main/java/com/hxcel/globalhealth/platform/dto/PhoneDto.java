/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.spec.model.enums.PhoneTypeCd;
import com.hxcel.globalhealth.platform.spec.dto.IPhoneDto;
import com.hxcel.globalhealth.common.dto.AbstractDto;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 5:19:42 PM
 */
public class PhoneDto extends AbstractDto implements IPhoneDto {
    private PhoneTypeCd phoneTypeCd = null;
    private String countryCode = null;
    private String areaCode = null;
    private String number = null;

    public PhoneTypeCd getPhoneTypeCd() {
        return phoneTypeCd;
    }

    public void setPhoneTypeCd(PhoneTypeCd phoneTypeCd) {
        this.phoneTypeCd = phoneTypeCd;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
