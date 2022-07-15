/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.dto.IApplicationDto;
import com.hxcel.globalhealth.platform.spec.dto.ILicenseDto;

import java.util.Date;
import java.io.Serializable;

public class LicenseDto extends AbstractDto implements Serializable, ILicenseDto {
    private static final long serialVersionUID = -7350335561414228490L;
    private String name = null;
    private String description = null;
    private Float price = null;
    private Date expirationDate = null;
    private LicenseTypeCd licenseType = null;
    private IApplicationDto application;
    private LicenseStatusCd status = null;

    public LicenseDto() {}

    public LicenseDto(IApplicationDto application, LicenseStatusCd status) {
        this.application = application;
        this.status = status;
    }

    public IApplicationDto getApplication() {
        return application;
    }

    public void setApplication(IApplicationDto application) {
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LicenseTypeCd getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseTypeCd licenseType) {
        this.licenseType = licenseType;
    }

    public LicenseStatusCd getStatus() {
        return status;
    }

    public void setStatus(LicenseStatusCd status) {
        this.status = status;
    }
}