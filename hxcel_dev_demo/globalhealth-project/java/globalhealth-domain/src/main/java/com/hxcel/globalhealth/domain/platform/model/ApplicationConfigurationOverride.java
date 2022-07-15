package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

import org.hibernate.validator.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 4:32:36 PM
 */
@Entity
public class ApplicationConfigurationOverride extends AbstractEntity {
    private static final long serialVersionUID = -1115002754248019612L;
    private ApplicationConfiguration original;
    private String value;
    private String description;
    private OrganizationLicense organizationLicense;

    public ApplicationConfigurationOverride(){}

    public ApplicationConfigurationOverride(OrganizationLicense ol){
        this.organizationLicense = ol;
    }

    @ManyToOne(optional = false)
    public OrganizationLicense getOrganizationLicense() {
        return organizationLicense;
    }

    public void setOrganizationLicense(OrganizationLicense organizationLicense) {
        this.organizationLicense = organizationLicense;
    }

    @ManyToOne(optional = false)
    public ApplicationConfiguration getOriginal() {
        return original;
    }

    public void setOriginal(ApplicationConfiguration original) {
        this.original = original;
    }

    @Column(nullable = false)
    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}