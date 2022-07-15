/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IImageDto;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationDto extends AbstractDto implements Serializable, IApplicationDto {
    private final static Logger log = LoggerFactory.getLogger(ApplicationDto.class);
    private String name = null;
    private IImageDto image = null;
    private String swfUrl = null;
    private String appVersion = null;
    private IOrganizationDto owner = null;
    private String description = null;
    private ApplicationStatusCd applicationStatus = null;
    private ApplicationTypeCd applicationType = null;
    private Boolean platform;

    public IImageDto getImage() {
        return image;
    }

    public void setImage(IImageDto image) {
        this.image = image;
    }

    public IOrganizationDto getOwner() {
        return owner;
    }

    public void setOwner(IOrganizationDto owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwfUrl() {
        return swfUrl;
    }

    public void setSwfUrl(String swfUrl) {
        this.swfUrl = swfUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApplicationStatusCd getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatusCd applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public ApplicationTypeCd getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationTypeCd applicationType) {
        this.applicationType = applicationType;
    }

    public Boolean getPlatform() {
        return platform;
    }

    public void setPlatform(Boolean platform) {
        this.platform = platform;
    }
}