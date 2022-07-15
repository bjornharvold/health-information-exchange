/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dto;

import com.hxcel.globalhealth.domain.common.model.enums.InstantMessageTypeCd;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 5:19:42 PM
 */
public class InstantMessageDto extends AbstractDto {
    private String nickname = null;
    private InstantMessageTypeCd instantMessageTypeCd = null;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public InstantMessageTypeCd getInstantMessageTypeCd() {
        return instantMessageTypeCd;
    }

    public void setInstantMessageTypeCd(InstantMessageTypeCd instantMessageTypeCd) {
        this.instantMessageTypeCd = instantMessageTypeCd;
    }
}