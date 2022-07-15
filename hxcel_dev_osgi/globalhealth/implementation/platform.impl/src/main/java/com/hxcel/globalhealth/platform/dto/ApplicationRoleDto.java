package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;

import java.io.Serializable;

public class ApplicationRoleDto extends AbstractDto implements Serializable, IApplicationRoleDto {
    private IRoleDto role;
    private IApplicationDto application;

    public ApplicationRoleDto() {
    }

    public ApplicationRoleDto(IRoleDto role, IApplicationDto application) {
        this.role = role;
        this.application = application;
    }

    public IRoleDto getRole() {
        return role;
    }

    public void setRole(IRoleDto role) {
        this.role = role;
    }

    public IApplicationDto getApplication() {
        return application;
    }

    public void setApplication(IApplicationDto application) {
        this.application = application;
    }
}