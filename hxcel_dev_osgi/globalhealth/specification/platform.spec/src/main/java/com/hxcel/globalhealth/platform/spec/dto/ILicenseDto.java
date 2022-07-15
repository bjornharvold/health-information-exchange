package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.LicenseTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;

import java.util.Date;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:30:12 PM
 * Responsibility:
 */
public interface ILicenseDto extends IAbstractDto {
    IApplicationDto getApplication();

    void setApplication(IApplicationDto application);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Float getPrice();

    void setPrice(Float price);

    Date getExpirationDate();

    void setExpirationDate(Date expirationDate);

    LicenseTypeCd getLicenseType();

    void setLicenseType(LicenseTypeCd licenseType);

    LicenseStatusCd getStatus();

    void setStatus(LicenseStatusCd status);
}
