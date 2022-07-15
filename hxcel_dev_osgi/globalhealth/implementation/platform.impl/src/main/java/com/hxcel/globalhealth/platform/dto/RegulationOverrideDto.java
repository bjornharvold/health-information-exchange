package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationOverrideDto;

import java.io.Serializable;

public class RegulationOverrideDto extends AbstractDto implements Serializable, IRegulationOverrideDto {
    private IRegulationDto original;
    private ICountryDto country;
    private String value;
    private String description;

    public IRegulationDto getOriginal() {
        return original;
    }

    public void setOriginal(IRegulationDto original) {
        this.original = original;
    }

    public ICountryDto getCountry() {
        return country;
    }

    public void setCountry(ICountryDto country) {
        this.country = country;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}