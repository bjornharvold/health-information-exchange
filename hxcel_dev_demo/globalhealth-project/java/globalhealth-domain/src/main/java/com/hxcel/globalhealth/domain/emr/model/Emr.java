/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd;
import com.hxcel.globalhealth.domain.professional.model.Professional;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "status",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd")}
        )
                }
)
public class Emr extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -2665149447579623450L;
    private Patient patient;

    @OneToOne(mappedBy = "emr")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
