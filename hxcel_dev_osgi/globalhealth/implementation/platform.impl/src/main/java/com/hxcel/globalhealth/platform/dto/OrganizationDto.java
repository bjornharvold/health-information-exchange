/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class OrganizationDto extends AbstractDto implements Serializable, IOrganizationDto {
    private final static Logger log = LoggerFactory.getLogger(OrganizationDto.class);
    private static final long serialVersionUID = -4778545587440863200L;
    private IOrganizationDto parent;
    private OrganizationTypeCd organizationType;
    private OrganizationStatusCd organizationStatus;
    private String name;
    private IImageDto image;
    private ICountryDto country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IOrganizationDto getParent() {
        return parent;
    }

    public void setParent(IOrganizationDto parent) {
        this.parent = parent;
    }

    public OrganizationTypeCd getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationTypeCd organizationType) {
        this.organizationType = organizationType;
    }

    public OrganizationStatusCd getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(OrganizationStatusCd organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public IImageDto getImage() {
        return image;
    }

    public void setImage(IImageDto image) {
        this.image = image;
    }

    public ICountryDto getCountry() {
        return country;
    }

    public void setCountry(ICountryDto country) {
        this.country = country;
    }
}