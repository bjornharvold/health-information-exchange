package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;

import java.io.Serializable;

public class ApplicationConfigurationDto extends AbstractDto implements Serializable, IApplicationConfigurationDto {

    private String key;
    private String value;
    private String description;
    private Boolean exportable;
    private IApplicationDto application;

    public IApplicationDto getApplication() {
        return application;
    }

    public void setApplication(IApplicationDto application) {
        this.application = application;
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

    public Boolean getExportable() {
        return exportable;
    }

    public void setExportable(Boolean exportable) {
        this.exportable = exportable;
    }

    public String getLabel() {
        return key + " : " + value;
    }
}