package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:30:51 PM
 * Responsibility:
 */
public interface ILoginDto {
    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);
}
