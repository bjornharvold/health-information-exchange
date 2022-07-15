/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import com.hxcel.globalhealth.domain.phr.model.enums.AllergyReactionTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.AllergyTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.FrequencyTypeCd;
import com.hxcel.globalhealth.domain.phr.model.enums.TreatmentTypeCd;

import java.util.Date;

/**
 * User: bjorn
 * Date: Dec 7, 2007
 * Time: 1:39:17 PM
 */
public class AllergyDto extends AbstractPhrDto {
    private FrequencyTypeCd frequencyTypeCd;
    private AllergyReactionTypeCd allergyReactionTypeCd;
    private TreatmentTypeCd treatmentTypeCd;
    private AllergyTypeCd allergyTypeCd;
    private String allergyTypeOther;
    private String allergyReactionOther;
    private String treatmentTypeOther;
    private Date startDate;
    private Date endDate;

    public FrequencyTypeCd getFrequencyTypeCd() {
        return frequencyTypeCd;
    }

    public void setFrequencyTypeCd(FrequencyTypeCd frequencyTypeCd) {
        this.frequencyTypeCd = frequencyTypeCd;
    }

    public AllergyReactionTypeCd getAllergyReactionTypeCd() {
        return allergyReactionTypeCd;
    }

    public void setAllergyReactionTypeCd(AllergyReactionTypeCd allergyReactionTypeCd) {
        this.allergyReactionTypeCd = allergyReactionTypeCd;
    }

    public TreatmentTypeCd getTreatmentTypeCd() {
        return treatmentTypeCd;
    }

    public void setTreatmentTypeCd(TreatmentTypeCd treatmentTypeCd) {
        this.treatmentTypeCd = treatmentTypeCd;
    }

    public AllergyTypeCd getAllergyTypeCd() {
        return allergyTypeCd;
    }

    public void setAllergyTypeCd(AllergyTypeCd allergyTypeCd) {
        this.allergyTypeCd = allergyTypeCd;
    }

    public String getAllergyTypeOther() {
        return allergyTypeOther;
    }

    public void setAllergyTypeOther(String allergyTypeOther) {
        this.allergyTypeOther = allergyTypeOther;
    }

    public String getAllergyReactionOther() {
        return allergyReactionOther;
    }

    public void setAllergyReactionOther(String allergyReactionOther) {
        this.allergyReactionOther = allergyReactionOther;
    }

    public String getTreatmentTypeOther() {
        return treatmentTypeOther;
    }

    public void setTreatmentTypeOther(String treatmentTypeOther) {
        this.treatmentTypeOther = treatmentTypeOther;
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
}
