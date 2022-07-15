/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.professional.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class ProfessionalSettings extends AbstractEntity implements Serializable {

    private Integer dateTimeIncrement = 30;
    private Boolean available = true;
    private Boolean rated = true;
    private Boolean listed = true;
    private String description = "DEFAULT";

    public ProfessionalSettings() {
        super();
    }

    public Integer getDateTimeIncrement() {
        return this.dateTimeIncrement;
    }


    public void setDateTimeIncrement(Integer value) {
        this.dateTimeIncrement = value;
    }


    public Boolean getAvailable() {
        return this.available;
    }


    public void setAvailable(Boolean value) {
        this.available = value;
    }


    public Boolean getRated() {
        return this.rated;
    }


    public void setRated(Boolean value) {
        this.rated = value;
    }


    public Boolean getListed() {
        return this.listed;
    }


    public void setListed(Boolean value) {
        this.listed = value;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
