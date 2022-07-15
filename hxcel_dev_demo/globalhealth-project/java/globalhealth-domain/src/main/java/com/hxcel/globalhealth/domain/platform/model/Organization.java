/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationStatusCd;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Image;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * User: bjorn
 * Date: Nov 8, 2007
 * Time: 3:33:38 PM
 * An organization can be us, a hospital, an insurance company or a country.
 */

@TypeDefs(
        {
            @TypeDef(name = "type", typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.platform.model.enums.OrganizationTypeCd")}
            ),
            @TypeDef(name = "status", typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.platform.model.enums.OrganizationStatusCd")}
            )
                }
)
@Entity
public class Organization extends AbstractEntity implements Serializable {
    private final static Logger log = LoggerFactory.getLogger(Organization.class);
    private static final long serialVersionUID = -4778545587440863200L;
    private Organization parent;
    private OrganizationTypeCd organizationType;
    private OrganizationStatusCd organizationStatus;
    private String name;
    private Image image;
    private Country country;

    @Column(nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }
    
    @Column(nullable = false)
    @Type(type = "type")
    @NotNull
    public OrganizationTypeCd getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationTypeCd organizationType) {
        this.organizationType = organizationType;
    }

    @Type(type = "status")
    @Column(nullable = false)
    @NotNull
    public OrganizationStatusCd getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(OrganizationStatusCd organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    @ManyToOne
    @Cascade(value = CascadeType.ALL)
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @ManyToOne(optional = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
