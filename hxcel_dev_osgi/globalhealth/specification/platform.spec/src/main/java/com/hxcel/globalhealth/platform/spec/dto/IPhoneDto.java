package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.model.enums.PhoneTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 1:18:43 PM
 * Responsibility:
 */
public interface IPhoneDto {
    PhoneTypeCd getPhoneTypeCd();

    void setPhoneTypeCd(PhoneTypeCd phoneTypeCd);

    String getCountryCode();

    void setCountryCode(String countryCode);

    String getAreaCode();

    void setAreaCode(String areaCode);

    String getNumber();

    void setNumber(String number);
}
