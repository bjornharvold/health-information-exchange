package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:34:03 PM
 * Responsibility:
 */
public interface IResourceBundleEntryOverrideDto extends IAbstractDto {
    IOrganizationLicenseDto getOrganizationLicense();

    void setOrganizationLicense(IOrganizationLicenseDto organizationLicense);

    IResourceBundleEntryDto getOriginal();

    void setOriginal(IResourceBundleEntryDto original);

    String getValue();

    void setValue(String value);

    String getDescription();

    void setDescription(String description);
}
