/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.MedicalConditionTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.MedicationFrequencyCd;
import com.hxcel.globalhealth.domain.phr.model.enums.MedicationTypeCd;

/**
 * User: bjorn
 * Date: Dec 9, 2007
 * Time: 10:35:55 AM
 */
public class MedicationDto extends AbstractPhrDto {
    private MedicationFrequencyCd frequency;
    private String practitioner;
    private MedicalConditionTypeCd conditionType;
    private MedicationTypeCd medicationTypeCd;
    private String phrContact;
    private String medicationDosage;
    private String typeOther;
    private String frequencyOther;
    private String prescriptionNo;
    private String conditionTypeOther;

    public MedicationFrequencyCd getFrequency() {
        return frequency;
    }

    public void setFrequency(MedicationFrequencyCd frequency) {
        this.frequency = frequency;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public MedicalConditionTypeCd getConditionType() {
        return conditionType;
    }

    public void setConditionType(MedicalConditionTypeCd conditionType) {
        this.conditionType = conditionType;
    }

    public MedicationTypeCd getMedicationTypeCd() {
        return medicationTypeCd;
    }

    public void setMedicationTypeCd(MedicationTypeCd medicationTypeCd) {
        this.medicationTypeCd = medicationTypeCd;
    }

    public String getPhrContact() {
        return phrContact;
    }

    public void setPhrContact(String phrContact) {
        this.phrContact = phrContact;
    }

    public String getMedicationDosage() {
        return medicationDosage;
    }

    public void setMedicationDosage(String medicationDosage) {
        this.medicationDosage = medicationDosage;
    }

    public String getTypeOther() {
        return typeOther;
    }

    public void setTypeOther(String typeOther) {
        this.typeOther = typeOther;
    }

    public String getFrequencyOther() {
        return frequencyOther;
    }

    public void setFrequencyOther(String frequencyOther) {
        this.frequencyOther = frequencyOther;
    }

    public String getPrescriptionNo() {
        return prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo) {
        this.prescriptionNo = prescriptionNo;
    }

    public String getConditionTypeOther() {
        return conditionTypeOther;
    }

    public void setConditionTypeOther(String conditionTypeOther) {
        this.conditionTypeOther = conditionTypeOther;
    }
}
