/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.model;

import com.hxcel.globalhealth.domain.common.model.AbstractRecordEntity;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.NotNull;


import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;

/**
 * User: bjorn
 * Date: Nov 7, 2007
 * Time: 10:04:11 PM
 *
 * Any emr record object should extend this as it gets the permissioning record
 */
@MappedSuperclass
public abstract class AbstractEmrEntity extends AbstractRecordEntity {
    private Emr emr;
    private EmrTypeCd emrType;

    @ManyToOne
    public Emr getEmr() {
        return emr;
    }

    public void setEmr(Emr emr) {
        this.emr = emr;
    }

    @Type(type = "emrtype")
    @Column(nullable = false)
    @NotNull
    public EmrTypeCd getEmrType() {
        return emrType;
    }

    public void setEmrType(EmrTypeCd emrType) {
        this.emrType = emrType;
    }
}