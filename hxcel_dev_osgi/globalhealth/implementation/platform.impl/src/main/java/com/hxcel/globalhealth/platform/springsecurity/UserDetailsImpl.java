/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.springsecurity;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.platform.model.UserRole;
import com.hxcel.globalhealth.platform.model.User;
import com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;

import java.util.List;
import java.util.ArrayList;

/**
 * User: bjorn
 * Date: Jun 9, 2008
 * Time: 12:17:35 PM
 * <p/>
 * Implementation of Spring-Security's UserDetails
 */
public class UserDetailsImpl implements UserDetails {
    private final static Logger log = LoggerFactory.getLogger(UserDetailsImpl.class);
    private IUserDto user = null;
    private List<UserRole> roles = null;

    public UserDetailsImpl(IUserDto u, List<UserRole> roles) {
        user = u;
        this.roles = roles;
    }

    public GrantedAuthority[] getAuthorities() {
        GrantedAuthority[] result = null;

        if (roles != null) {
            List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();

            for (UserRole ur : roles) {
                gas.add(new GrantedAuthorityImpl(ur.getRole().getStatusCode()));
            }

            result = gas.toArray(new GrantedAuthority[gas.size()]);
        }

        return result;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getFullName() {
        String result = null;

        if (user != null) {
            result = user.getUserInfo().getFirstName() + " " + user.getUserInfo().getLastName();
        }

        return result;
    }

    public boolean isAccountNonExpired() {
        return user.getUserStatus() == UserStatusCd.ACTIVE;
    }

    public boolean isAccountNonLocked() {
        return user.getUserStatus() != UserStatusCd.LOCKED;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return user.getUserStatus() == UserStatusCd.ACTIVE;
    }

}
