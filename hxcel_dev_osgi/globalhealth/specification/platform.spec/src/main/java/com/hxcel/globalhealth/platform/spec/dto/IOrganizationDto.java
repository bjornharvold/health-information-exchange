package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IImageDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:31:12 PM
 * Responsibility:
 */
public interface IOrganizationDto extends IAbstractDto {
    String getName();

    void setName(String name);

    IOrganizationDto getParent();

    void setParent(IOrganizationDto parent);

    OrganizationTypeCd getOrganizationType();

    void setOrganizationType(OrganizationTypeCd organizationType);

    OrganizationStatusCd getOrganizationStatus();

    void setOrganizationStatus(OrganizationStatusCd organizationStatus);

    IImageDto getImage();

    void setImage(IImageDto image);

    ICountryDto getCountry();

    void setCountry(ICountryDto country);
}
