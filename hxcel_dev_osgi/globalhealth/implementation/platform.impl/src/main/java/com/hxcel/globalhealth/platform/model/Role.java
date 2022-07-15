package com.hxcel.globalhealth.platform.model;

import org.hibernate.validator.NotNull;

import javax.persistence.Entity;
import javax.persistence.Column;

import com.hxcel.globalhealth.common.model.AbstractReferenceDataEntity;

import java.io.Serializable;

/**
 * User: bjorn
 * Date: Sep 24, 2008
 * Time: 5:21:50 PM
 */
@Entity
public class Role extends AbstractReferenceDataEntity implements Serializable {
    private static final long serialVersionUID = -5153394970374372619L;
    private String name;
    private String description;

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
