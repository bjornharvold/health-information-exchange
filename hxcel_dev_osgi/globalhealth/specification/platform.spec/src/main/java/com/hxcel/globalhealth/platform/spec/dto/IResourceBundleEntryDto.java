package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:33:40 PM
 * Responsibility:
 */
public interface IResourceBundleEntryDto extends IAbstractDto {
    IApplicationDto getApplication();

    void setApplication(IApplicationDto application);

    ICountryDto getCountry();

    void setCountry(ICountryDto country);

    String getKey();

    void setKey(String key);

    String getValue();

    void setValue(String value);

    String getDescription();

    void setDescription(String description);

    String getLabel();
}
