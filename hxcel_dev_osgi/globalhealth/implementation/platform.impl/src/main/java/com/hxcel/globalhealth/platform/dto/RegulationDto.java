package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.RegulationTypeCd;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import com.hxcel.globalhealth.common.dto.AbstractDto;

import java.io.Serializable;

public class RegulationDto extends AbstractDto implements Serializable, IRegulationDto {
    private String name;
    private String key;
    private String value;
    private String description;
    private RegulationTypeCd type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public RegulationTypeCd getType() {
        return type;
    }

    public void setType(RegulationTypeCd type) {
        this.type = type;
    }

    public String getLabel() {
        return key + " : " + value + " : " + type;
    }
}