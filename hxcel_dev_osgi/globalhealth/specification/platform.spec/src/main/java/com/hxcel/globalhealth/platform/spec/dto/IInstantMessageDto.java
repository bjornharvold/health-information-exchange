package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.model.enums.InstantMessageTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 1:17:57 PM
 * Responsibility:
 */
public interface IInstantMessageDto {
    String getNickname();

    void setNickname(String nickname);

    InstantMessageTypeCd getInstantMessageTypeCd();

    void setInstantMessageTypeCd(InstantMessageTypeCd instantMessageTypeCd);
}
