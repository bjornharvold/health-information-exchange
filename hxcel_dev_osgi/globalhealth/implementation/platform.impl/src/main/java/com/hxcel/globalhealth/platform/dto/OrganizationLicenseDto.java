package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.dto.ILicenseDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationLicenseDto;

import java.io.Serializable;

public class OrganizationLicenseDto extends AbstractDto implements Serializable, IOrganizationLicenseDto {
    private static final long serialVersionUID = -7965479129099578005L;
    private IOrganizationDto organization;
    private ILicenseDto license;
    private OrganizationLicenseStatusCd status;

    public OrganizationLicenseDto(){}

    public OrganizationLicenseDto(IOrganizationDto organization, ILicenseDto license) {
        this.organization = organization;
        this.license = license;
    }

    public OrganizationLicenseDto(IOrganizationDto organization, ILicenseDto license, OrganizationLicenseStatusCd status) {
        this.organization = organization;
        this.license = license;
        this.status = status;
    }

    public IOrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(IOrganizationDto organization) {
        this.organization = organization;
    }

    public ILicenseDto getLicense() {
        return license;
    }

    public void setLicense(ILicenseDto license) {
        this.license = license;
    }

    public OrganizationLicenseStatusCd getStatus() {
        return status;
    }

    public void setStatus(OrganizationLicenseStatusCd status) {
        this.status = status;
    }
}