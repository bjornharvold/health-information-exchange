/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * User: bjorn
 * Date: Nov 11, 2007
 * Time: 3:21:26 PM
 */
@Entity
public class Phr extends AbstractEntity {
    private Patient patient;

    @OneToOne(mappedBy = "phr")
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
