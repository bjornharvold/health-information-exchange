package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationOverrideDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationConfigurationDto;

import java.io.Serializable;

public class ApplicationConfigurationOverrideDto extends AbstractDto implements Serializable, IApplicationConfigurationOverrideDto {
    private static final long serialVersionUID = -1115002754248019612L;
    private IApplicationConfigurationDto original;
    private String value;
    private String description;
    private IOrganizationLicenseDto organizationLicense;

    public ApplicationConfigurationOverrideDto(){}

    public ApplicationConfigurationOverrideDto(IOrganizationLicenseDto organizationLicense) {
        this.organizationLicense = organizationLicense;
    }

    public IApplicationConfigurationDto getOriginal() {
        return original;
    }

    public void setOriginal(IApplicationConfigurationDto original) {
        this.original = original;
    }

    public IOrganizationLicenseDto getOrganizationLicense() {
        return organizationLicense;
    }

    public void setOrganizationLicense(IOrganizationLicenseDto organizationLicense) {
        this.organizationLicense = organizationLicense;
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