package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.common.spec.model.enums.LocationTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 1:18:21 PM
 * Responsibility:
 */
public interface ILocationDto {
    CountryCd getCountryCd();

    void setCountryCd(CountryCd countryCd);

    LocationTypeCd getLocationTypeCd();

    void setLocationTypeCd(LocationTypeCd locationTypeCd);

    String getAddress1();

    void setAddress1(String address1);

    String getAddress2();

    void setAddress2(String address2);

    String getAddress3();

    void setAddress3(String address3);

    String getCity();

    void setCity(String city);

    String getState();

    void setState(String state);

    String getZip();

    void setZip(String zip);

    Boolean getPrimary();

    void setPrimary(Boolean primary);
}
