/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.patient.model;

import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientTypeCd;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.UserInfo;
import com.hxcel.globalhealth.domain.emr.model.Emr;
import com.hxcel.globalhealth.domain.phr.model.Phr;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Patient is the keeper of the phrs and the emrs. A patient can be created by
 * a user during registration, in which case it is a primary patient. A patient can
 * also be created by the doctor, in which case it's a quasi patient that the professional
 * can use for a user who has not yet created an account with us. When the user does create an
 * account, we can associate that patient record with the user's. That means the user will have
 * two patient record associations but it's no big deal. This way we can easily merge and unmerge
 * this information.
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "patientstatus", typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd")}
        ),
        @TypeDef(name = "patienttype", typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.patient.model.enums.PatientTypeCd")}
        )
                }
)
public class Patient extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 100166503820545509L;
    private PatientStatusCd patientStatus;
    private PatientTypeCd patientType;
    private Phr phr;
    private Emr emr;

    // this user info is only used when the professional creates the patient entity
    private UserInfo userInfo;

    @Type(type = "patientstatus")
    public PatientStatusCd getPatientStatus() {
        return this.patientStatus;
    }

    public void setPatientStatus(PatientStatusCd value) {
        this.patientStatus = value;
    }

    @OneToOne
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.DELETE_ORPHAN})
    public Phr getPhr() {
        return phr;
    }

    public void setPhr(Phr phr) {
        this.phr = phr;
    }

    @Type(type = "patienttype")
    public PatientTypeCd getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientTypeCd patientType) {
        this.patientType = patientType;
    }

    @OneToOne
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.DELETE_ORPHAN})
    public Emr getEmr() {
        return emr;
    }

    public void setEmr(Emr emr) {
        this.emr = emr;
    }

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo value) {
        this.userInfo = value;
    }
}
