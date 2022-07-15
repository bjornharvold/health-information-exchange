/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dto;

import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Jan 20, 2007
 * Time: 3:49:16 PM
 */
public class UserDto extends AbstractDto implements Serializable {
    private static final long serialVersionUID = 5446135898711387892L;
    private CountryCd country;
    private String username;
    private String password;
    private String passwordConfirm;
    private UserInfoDto userInfo = new UserInfoDto();

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

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }
}
