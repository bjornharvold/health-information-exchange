package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:32:30 PM
 * Responsibility:
 */
public interface IOrganizationUserRoleDto extends IAbstractDto {
    IOrganizationUserDto getUser();

    void setUser(IOrganizationUserDto user);

    IRoleDto getRole();

    void setRole(IRoleDto role);
}
