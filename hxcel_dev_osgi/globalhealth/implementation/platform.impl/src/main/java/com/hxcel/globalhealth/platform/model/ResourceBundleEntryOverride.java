package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

import org.hibernate.validator.NotNull;

import java.io.Serializable;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 4:32:36 PM
 */
@Entity
public class ResourceBundleEntryOverride extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -1114002754248019612L;
    private ResourceBundleEntry original;
    private String value;
    private String description;
    private OrganizationLicense organizationLicense;

    public ResourceBundleEntryOverride(){}

    public ResourceBundleEntryOverride(OrganizationLicense ol){
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
    public ResourceBundleEntry getOriginal() {
        return original;
    }

    public void setOriginal(ResourceBundleEntry original) {
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