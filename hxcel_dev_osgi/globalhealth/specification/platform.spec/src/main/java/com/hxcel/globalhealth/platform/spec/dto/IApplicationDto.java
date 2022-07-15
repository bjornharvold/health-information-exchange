package com.hxcel.globalhealth.platform.spec.dto;

import com.hxcel.globalhealth.common.spec.dto.IAbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IImageDto;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Jan 7, 2009
 * Time: 8:29:00 PM
 * Responsibility:
 */
public interface IApplicationDto extends IAbstractDto {
    IImageDto getImage();

    void setImage(IImageDto image);

    IOrganizationDto getOwner();

    void setOwner(IOrganizationDto owner);

    String getName();

    void setName(String name);

    String getSwfUrl();

    void setSwfUrl(String swfUrl);

    String getAppVersion();

    void setAppVersion(String appVersion);

    String getDescription();

    void setDescription(String description);

    ApplicationStatusCd getApplicationStatus();

    void setApplicationStatus(ApplicationStatusCd applicationStatus);

    ApplicationTypeCd getApplicationType();

    void setApplicationType(ApplicationTypeCd applicationType);

    Boolean getPlatform();

    void setPlatform(Boolean platform);
}
