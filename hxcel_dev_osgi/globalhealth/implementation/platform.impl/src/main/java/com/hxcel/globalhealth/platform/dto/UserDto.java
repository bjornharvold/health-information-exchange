/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import com.hxcel.globalhealth.platform.spec.dto.IUserInfoDto;
import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.dto.UserInfoDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Jan 20, 2007
 * Time: 3:49:16 PM
 */
public class UserDto extends AbstractDto implements Serializable, IUserDto {
    private static final long serialVersionUID = 5446135898711387892L;
    private CountryCd country;
    private String username;
    private String password;
    private String passwordConfirm;
    private IUserInfoDto userInfo = new UserInfoDto();
    private UserStatusCd userStatus;

    public UserStatusCd getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(UserStatusCd value) {
        this.userStatus = value;
    }

    public CountryCd getCountry() {
        return country;
    }

    public void setCountry(CountryCd country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public IUserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(IUserInfoDto userInfo) {
        this.userInfo = userInfo;
    }
}
