package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;
import com.hxcel.globalhealth.platform.spec.dto.IResourceBundleEntryDto;
import com.hxcel.globalhealth.platform.spec.dto.IResourceBundleEntryOverrideDto;

import java.io.Serializable;

public class ResourceBundleEntryOverrideDto extends AbstractDto implements Serializable, IResourceBundleEntryOverrideDto {
    private static final long serialVersionUID = -1114002754248019612L;
    private IResourceBundleEntryDto original;
    private String value;
    private String description;
    private IOrganizationLicenseDto organizationLicense;

    public ResourceBundleEntryOverrideDto(){}

    public ResourceBundleEntryOverrideDto(IOrganizationLicenseDto ol){
        this.organizationLicense = ol;
    }

    public IOrganizationLicenseDto getOrganizationLicense() {
        return organizationLicense;
    }

    public void setOrganizationLicense(IOrganizationLicenseDto organizationLicense) {
        this.organizationLicense = organizationLicense;
    }

    public IResourceBundleEntryDto getOriginal() {
        return original;
    }

    public void setOriginal(IResourceBundleEntryDto original) {
        this.original = original;
    }

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