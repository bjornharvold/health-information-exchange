package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserInfoDto;
import com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:35:03 PM
 * Responsibility:
 */
public interface IUserDto extends IAbstractDto {
    UserStatusCd getUserStatus();

    void setUserStatus(UserStatusCd status);

    CountryCd getCountry();

    void setCountry(CountryCd country);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getPasswordConfirm();

    void setPasswordConfirm(String passwordConfirm);

    IUserInfoDto getUserInfo();

    void setUserInfo(IUserInfoDto userInfo);

}
