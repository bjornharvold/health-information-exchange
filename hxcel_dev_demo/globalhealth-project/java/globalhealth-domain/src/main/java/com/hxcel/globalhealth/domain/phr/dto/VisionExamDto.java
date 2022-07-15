/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.VisionExamReasonTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 8:37:42 PM
 */
public class VisionExamDto extends AbstractPhrDto {
    private VisionExamReasonTypeCd reasonType;
    private String practitioner;
    private Date examDate;
    private String reasonOther;
    private String visionRightEyeXy;
    private String visionLeftEyeXy;

    public VisionExamReasonTypeCd getReasonType() {
        return reasonType;
    }

    public void setReasonType(VisionExamReasonTypeCd reasonType) {
        this.reasonType = reasonType;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getReasonOther() {
        return reasonOther;
    }

    public void setReasonOther(String reasonOther) {
        this.reasonOther = reasonOther;
    }

    public String getVisionRightEyeXy() {
        return visionRightEyeXy;
    }

    public void setVisionRightEyeXy(String visionRightEyeXy) {
        this.visionRightEyeXy = visionRightEyeXy;
    }

    public String getVisionLeftEyeXy() {
        return visionLeftEyeXy;
    }

    public void setVisionLeftEyeXy(String visionLeftEyeXy) {
        this.visionLeftEyeXy = visionLeftEyeXy;
    }
}
