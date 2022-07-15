package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserRoleDto;

import java.io.Serializable;

public class UserRoleDto extends AbstractDto implements Serializable, IUserRoleDto {
    private IRoleDto role;
    private IUserDto user;

    public UserRoleDto(){}

    public UserRoleDto(IUserDto user, IRoleDto role) {
        this.user = user;
        this.role = role;
    }

    public IRoleDto getRole() {
        return role;
    }

    public void setRole(IRoleDto role) {
        this.role = role;
    }

    public IUserDto getUser() {
        return user;
    }

    public void setUser(IUserDto user) {
        this.user = user;
    }
}