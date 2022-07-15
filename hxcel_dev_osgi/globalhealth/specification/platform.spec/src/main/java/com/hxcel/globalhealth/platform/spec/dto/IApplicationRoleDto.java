package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:29:38 PM
 * Responsibility:
 */
public interface IApplicationRoleDto extends IAbstractDto {
    IRoleDto getRole();

    void setRole(IRoleDto role);

    IApplicationDto getApplication();

    void setApplication(IApplicationDto application);
}
