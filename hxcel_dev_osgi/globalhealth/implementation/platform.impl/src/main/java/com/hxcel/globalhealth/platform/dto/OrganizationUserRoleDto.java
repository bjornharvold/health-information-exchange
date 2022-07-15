package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationUserRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationUserDto;

import java.io.Serializable;

public class OrganizationUserRoleDto extends AbstractDto implements Serializable, IOrganizationUserRoleDto {
    private static final long serialVersionUID = -553350232284377516L;
    private IOrganizationUserDto user;
    private IRoleDto role;

    public OrganizationUserRoleDto(){}

    public OrganizationUserRoleDto(IOrganizationUserDto user, IRoleDto role) {
        this.user = user;
        this.role = role;
    }

    public IOrganizationUserDto getUser() {
        return user;
    }

    public void setUser(IOrganizationUserDto user) {
        this.user = user;
    }

    public IRoleDto getRole() {
        return role;
    }

    public void setRole(IRoleDto role) {
        this.role = role;
    }
}