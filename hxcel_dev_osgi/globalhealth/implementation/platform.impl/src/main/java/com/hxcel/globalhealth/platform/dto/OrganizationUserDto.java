package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationUserStatusCd;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationUserDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;

import java.io.Serializable;

public class OrganizationUserDto extends AbstractDto implements Serializable, IOrganizationUserDto {
    private static final long serialVersionUID = -1007136748003835218L;
    private IOrganizationDto organization;
    private IUserDto user;
    private OrganizationUserStatusCd status;

    public OrganizationUserDto() {}

    public OrganizationUserDto(IOrganizationDto organization, IUserDto user) {
        this.organization = organization;
        this.user = user;
    }

    public IOrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(IOrganizationDto organization) {
        this.organization = organization;
    }

    public IUserDto getUser() {
        return user;
    }

    public void setUser(IUserDto user) {
        this.user = user;
    }

    public OrganizationUserStatusCd getStatus() {
        return status;
    }

    public void setStatus(OrganizationUserStatusCd status) {
        this.status = status;
    }
}