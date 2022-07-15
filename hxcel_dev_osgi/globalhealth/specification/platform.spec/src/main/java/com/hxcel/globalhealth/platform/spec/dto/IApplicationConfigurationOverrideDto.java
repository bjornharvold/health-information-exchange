package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:28:12 PM
 * Responsibility:
 */
public interface IApplicationConfigurationOverrideDto extends IAbstractDto {
    IApplicationConfigurationDto getOriginal();

    void setOriginal(IApplicationConfigurationDto original);

    IOrganizationLicenseDto getOrganizationLicense();

    void setOrganizationLicense(IOrganizationLicenseDto organizationLicense);

    String getValue();

    void setValue(String value);

    String getDescription();

    void setDescription(String description);
}
