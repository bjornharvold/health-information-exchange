package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractReferenceDataDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;

import java.io.Serializable;

public class RoleDto extends AbstractReferenceDataDto implements Serializable, IRoleDto {
    private static final long serialVersionUID = -5153394970374372619L;
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}