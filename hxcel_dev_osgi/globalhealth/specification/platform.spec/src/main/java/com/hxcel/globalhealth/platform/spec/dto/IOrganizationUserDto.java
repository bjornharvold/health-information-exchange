package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationUserStatusCd;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:32:06 PM
 * Responsibility:
 */
public interface IOrganizationUserDto extends IAbstractDto {
    IOrganizationDto getOrganization();

    void setOrganization(IOrganizationDto organization);

    IUserDto getUser();

    void setUser(IUserDto user);

    OrganizationUserStatusCd getStatus();

    void setStatus(OrganizationUserStatusCd status);
}
