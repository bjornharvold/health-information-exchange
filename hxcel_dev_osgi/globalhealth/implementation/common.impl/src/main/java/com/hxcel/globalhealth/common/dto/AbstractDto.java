/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Feb 5, 2007
 * Time: 1:06:46 AM
 */
public abstract class AbstractDto implements IAbstractDto {
    private final static Logger log = LoggerFactory.getLogger(AbstractDto.class);
    private String id = null;
    private Integer version = null;
    private Date createdDate = null;
    private Date lastUpdate = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdate() {
        if (lastUpdate == null) {
            lastUpdate = new Date(new Date().getTime());
        }
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
