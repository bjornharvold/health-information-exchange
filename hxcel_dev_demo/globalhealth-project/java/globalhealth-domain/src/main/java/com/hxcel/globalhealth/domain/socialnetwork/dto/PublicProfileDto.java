package com.hxcel.globalhealth.domain.socialnetwork.dto;

import com.hxcel.globalhealth.domain.common.dto.UserInfoDto;

import java.io.Serializable;

/**
 * User: bjorn
 * Date: Aug 15, 2008
 * Time: 12:55:21 AM
 */
public class PublicProfileDto extends AbstractProfileDto implements Serializable {
    private static final long serialVersionUID = -4191479405634183640L;

    private UserInfoDto userInfo;

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }
}
