/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.VisionNonPrescriptionGlassesTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.VisionPrescriptionGlassesTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 8:49:47 PM
 */
public class VisionGlassesDto extends AbstractPhrDto {
    private VisionNonPrescriptionGlassesTypeCd nonPrescriptionType;
    private String practitioner;
    private VisionPrescriptionGlassesTypeCd prescriptionType;
    private Date startDate;
    private String prescriptionOther;
    private String nonPrescriptionOther;
    private String rightEyeSpherePower;
    private String rightEyeCylinderPower;
    private String leftEyeSpherePower;
    private String leftEyeCylinderPower;
    private String addPowerBifocals;
    private Date endDate;

    public VisionNonPrescriptionGlassesTypeCd getNonPrescriptionType() {
        return nonPrescriptionType;
    }

    public void setNonPrescriptionType(VisionNonPrescriptionGlassesTypeCd nonPrescriptionType) {
        this.nonPrescriptionType = nonPrescriptionType;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public VisionPrescriptionGlassesTypeCd getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(VisionPrescriptionGlassesTypeCd prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPrescriptionOther() {
        return prescriptionOther;
    }

    public void setPrescriptionOther(String prescriptionOther) {
        this.prescriptionOther = prescriptionOther;
    }

    public String getNonPrescriptionOther() {
        return nonPrescriptionOther;
    }

    public void setNonPrescriptionOther(String nonPrescriptionOther) {
        this.nonPrescriptionOther = nonPrescriptionOther;
    }

    public String getRightEyeSpherePower() {
        return rightEyeSpherePower;
    }

    public void setRightEyeSpherePower(String rightEyeSpherePower) {
        this.rightEyeSpherePower = rightEyeSpherePower;
    }

    public String getRightEyeCylinderPower() {
        return rightEyeCylinderPower;
    }

    public void setRightEyeCylinderPower(String rightEyeCylinderPower) {
        this.rightEyeCylinderPower = rightEyeCylinderPower;
    }

    public String getLeftEyeSpherePower() {
        return leftEyeSpherePower;
    }

    public void setLeftEyeSpherePower(String leftEyeSpherePower) {
        this.leftEyeSpherePower = leftEyeSpherePower;
    }

    public String getLeftEyeCylinderPower() {
        return leftEyeCylinderPower;
    }

    public void setLeftEyeCylinderPower(String leftEyeCylinderPower) {
        this.leftEyeCylinderPower = leftEyeCylinderPower;
    }

    public String getAddPowerBifocals() {
        return addPowerBifocals;
    }

    public void setAddPowerBifocals(String addPowerBifocals) {
        this.addPowerBifocals = addPowerBifocals;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
