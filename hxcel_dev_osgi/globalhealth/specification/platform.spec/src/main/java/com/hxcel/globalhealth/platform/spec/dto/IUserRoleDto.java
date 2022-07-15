package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:35:23 PM
 * Responsibility:
 */
public interface IUserRoleDto extends IAbstractDto {
    IRoleDto getRole();

    void setRole(IRoleDto role);

    IUserDto getUser();

    void setUser(IUserDto user);
}
