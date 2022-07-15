/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.RelativeConditionTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.RelativeTypeCd;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 5:25:13 AM
 */
public class FamilyHistoryDto extends AbstractPhrDto {
    private RelativeConditionTypeCd relativeConditionTypeCd;
    private RelativeTypeCd relativeTypeCd;
    private String relativeOther;
    private String relativeConditionOther;
    private Boolean alive;

    public RelativeConditionTypeCd getRelativeConditionTypeCd() {
        return relativeConditionTypeCd;
    }

    public void setRelativeConditionTypeCd(RelativeConditionTypeCd relativeConditionTypeCd) {
        this.relativeConditionTypeCd = relativeConditionTypeCd;
    }

    public RelativeTypeCd getRelativeTypeCd() {
        return relativeTypeCd;
    }

    public void setRelativeTypeCd(RelativeTypeCd relativeTypeCd) {
        this.relativeTypeCd = relativeTypeCd;
    }

    public String getRelativeOther() {
        return relativeOther;
    }

    public void setRelativeOther(String relativeOther) {
        this.relativeOther = relativeOther;
    }

    public String getRelativeConditionOther() {
        return relativeConditionOther;
    }

    public void setRelativeConditionOther(String relativeConditionOther) {
        this.relativeConditionOther = relativeConditionOther;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}
