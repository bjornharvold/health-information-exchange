package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IResourceBundleEntryDto;

import java.io.Serializable;

public class ResourceBundleEntryDto extends AbstractDto implements Serializable, IResourceBundleEntryDto {
    private IApplicationDto application;
    private ICountryDto country;
    private String key;
    private String value;
    private String description;

    public ResourceBundleEntryDto(){}
    public ResourceBundleEntryDto(IApplicationDto application, ICountryDto country){
        this.application = application;
        this.country = country;
    }

    public IApplicationDto getApplication() {
        return application;
    }

    public void setApplication(IApplicationDto application) {
        this.application = application;
    }

    public ICountryDto getCountry() {
        return country;
    }

    public void setCountry(ICountryDto country) {
        this.country = country;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append(" : ");
        sb.append(value);
        sb.append(" : ");

        if (country != null) {
            sb.append(country.getStatusCode());
        }

        return sb.toString();
    }
}