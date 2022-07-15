/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.VisionContactLensTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 8:05:43 PM
 */
public class VisionContactLensesDto extends AbstractPhrDto {
    private VisionContactLensTypeCd lensType;
    private String practitioner;
    private Date startDate;
    private Date endDate;
    private String typeOther;
    private String lensColor;
    private String pairsOrdered;
    private String lensDuration;
    private String solutionUsed;
    private String rightEyeCylinder;
    private String rightEyeSphere;
    private String rightEyePower;
    private String rightEyeAxis;
    private String rightEyeBc;
    private String leftEyeCylinder;
    private String leftEyeSphere;
    private String leftEyePower;
    private String leftEyeAxis;
    private String leftEyeBc;

    public VisionContactLensTypeCd getLensType() {
        return lensType;
    }

    public void setLensType(VisionContactLensTypeCd lensType) {
        this.lensType = lensType;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTypeOther() {
        return typeOther;
    }

    public void setTypeOther(String typeOther) {
        this.typeOther = typeOther;
    }

    public String getLensColor() {
        return lensColor;
    }

    public void setLensColor(String lensColor) {
        this.lensColor = lensColor;
    }

    public String getPairsOrdered() {
        return pairsOrdered;
    }

    public void setPairsOrdered(String pairsOrdered) {
        this.pairsOrdered = pairsOrdered;
    }

    public String getLensDuration() {
        return lensDuration;
    }

    public void setLensDuration(String lensDuration) {
        this.lensDuration = lensDuration;
    }

    public String getSolutionUsed() {
        return solutionUsed;
    }

    public void setSolutionUsed(String solutionUsed) {
        this.solutionUsed = solutionUsed;
    }

    public String getRightEyeCylinder() {
        return rightEyeCylinder;
    }

    public void setRightEyeCylinder(String rightEyeCylinder) {
        this.rightEyeCylinder = rightEyeCylinder;
    }

    public String getRightEyeSphere() {
        return rightEyeSphere;
    }

    public void setRightEyeSphere(String rightEyeSphere) {
        this.rightEyeSphere = rightEyeSphere;
    }

    public String getRightEyePower() {
        return rightEyePower;
    }

    public void setRightEyePower(String rightEyePower) {
        this.rightEyePower = rightEyePower;
    }

    public String getRightEyeAxis() {
        return rightEyeAxis;
    }

    public void setRightEyeAxis(String rightEyeAxis) {
        this.rightEyeAxis = rightEyeAxis;
    }

    public String getRightEyeBc() {
        return rightEyeBc;
    }

    public void setRightEyeBc(String rightEyeBc) {
        this.rightEyeBc = rightEyeBc;
    }

    public String getLeftEyeCylinder() {
        return leftEyeCylinder;
    }

    public void setLeftEyeCylinder(String leftEyeCylinder) {
        this.leftEyeCylinder = leftEyeCylinder;
    }

    public String getLeftEyeSphere() {
        return leftEyeSphere;
    }

    public void setLeftEyeSphere(String leftEyeSphere) {
        this.leftEyeSphere = leftEyeSphere;
    }

    public String getLeftEyePower() {
        return leftEyePower;
    }

    public void setLeftEyePower(String leftEyePower) {
        this.leftEyePower = leftEyePower;
    }

    public String getLeftEyeAxis() {
        return leftEyeAxis;
    }

    public void setLeftEyeAxis(String leftEyeAxis) {
        this.leftEyeAxis = leftEyeAxis;
    }

    public String getLeftEyeBc() {
        return leftEyeBc;
    }

    public void setLeftEyeBc(String leftEyeBc) {
        this.leftEyeBc = leftEyeBc;
    }
}
