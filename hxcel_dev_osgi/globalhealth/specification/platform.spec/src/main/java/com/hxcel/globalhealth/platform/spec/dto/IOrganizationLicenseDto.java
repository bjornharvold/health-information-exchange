package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:31:42 PM
 * Responsibility:
 */
public interface IOrganizationLicenseDto extends IAbstractDto {
    IOrganizationDto getOrganization();

    void setOrganization(IOrganizationDto organization);

    ILicenseDto getLicense();

    void setLicense(ILicenseDto license);

    OrganizationLicenseStatusCd getStatus();

    void setStatus(OrganizationLicenseStatusCd status);
}
