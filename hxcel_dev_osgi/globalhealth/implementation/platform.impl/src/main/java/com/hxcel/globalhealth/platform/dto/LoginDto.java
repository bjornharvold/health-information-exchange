/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.platform.spec.dto.ILoginDto;

import java.io.Serializable;

/**
 * User: bjorn
 * Date: Jun 8, 2008
 * Time: 7:05:00 PM
 */
public class LoginDto implements Serializable, ILoginDto {
    private String username;
    private String password;

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
}
