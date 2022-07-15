/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * User: Bjorn Harvold
 * Date: Jan 5, 2007
 * Time: 5:18:07 PM
 */

@MappedSuperclass
public abstract class AbstractReferenceDataEntity extends AbstractEntity {
    private final static Logger log = LoggerFactory.getLogger(AbstractReferenceDataEntity.class);
    private String statusCode = null;

    @Column(unique = true, nullable = false)
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" :: AbstractReferenceDataEntity");
        sb.append("{statusCode='").append(statusCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
