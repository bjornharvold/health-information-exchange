/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.common.model.AbstractRecordEntity;
import com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd;
import org.hibernate.annotations.*;


import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * User: bjorn
 * Date: Nov 7, 2007
 * Time: 10:04:11 PM
 *
 * Any phr record object should extends this as it gets the permissioning record
 */
@MappedSuperclass
public abstract class AbstractPhrEntity extends AbstractRecordEntity {
    private Phr phr;
    private PhrTypeCd phrType;

    @ManyToOne
    public Phr getPhr() {
        return phr;
    }

    public void setPhr(Phr phr) {
        this.phr = phr;
    }

    @Column(length = 50, nullable = false)
    @Type(type = "phrtype")
    public PhrTypeCd getPhrType() {
        return phrType;
    }

    public void setPhrType(PhrTypeCd phrType) {
        this.phrType = phrType;
    }
}
