/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.spec.model.enums.InstantMessageTypeCd;
import com.hxcel.globalhealth.platform.spec.dto.IInstantMessageDto;
import com.hxcel.globalhealth.common.dto.AbstractDto;

/**
 * User: bjorn
 * Date: Dec 21, 2007
 * Time: 5:19:42 PM
 */
public class InstantMessageDto extends AbstractDto implements IInstantMessageDto {
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