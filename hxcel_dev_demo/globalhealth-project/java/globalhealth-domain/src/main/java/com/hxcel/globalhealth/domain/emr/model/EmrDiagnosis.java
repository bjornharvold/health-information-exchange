/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.model;

import com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "diagnoserType",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd")}
        ),
        @TypeDef(name = "emrtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.emr.model.enums.EmrTypeCd")
                }),
        @TypeDef(name = "rstatus", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordStatusCd")
                }),
        @TypeDef(name = "rtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordTypeCd")
                }),
        @TypeDef(name = "entitytype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")
                })
                }
)
public class EmrDiagnosis extends AbstractEmrEntity implements Serializable {

    private Diagnosis diagnosis;
    private DiagnoserTypeCd diagnoserType;

    @ManyToOne
    public Diagnosis getDiagnosis() {
        return this.diagnosis;
    }

    public void setDiagnosis(Diagnosis value) {
        this.diagnosis = value;
    }

    @Type(type = "diagnoserType")
    public DiagnoserTypeCd getDiagnoserType() {
        return this.diagnoserType;
    }


    public void setDiagnoserType(DiagnoserTypeCd value) {
        this.diagnoserType = value;
    }

}
