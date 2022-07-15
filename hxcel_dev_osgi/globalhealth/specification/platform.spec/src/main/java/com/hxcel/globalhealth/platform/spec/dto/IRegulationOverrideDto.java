package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:33:16 PM
 * Responsibility:
 */
public interface IRegulationOverrideDto extends IAbstractDto {
    IRegulationDto getOriginal();

    void setOriginal(IRegulationDto original);

    ICountryDto getCountry();

    void setCountry(ICountryDto country);

    String getValue();

    void setValue(String value);

    String getDescription();

    void setDescription(String description);
}
