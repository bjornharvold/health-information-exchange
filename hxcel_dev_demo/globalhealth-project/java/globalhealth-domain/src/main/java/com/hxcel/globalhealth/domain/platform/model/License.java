/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseTypeCd;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import java.util.Date;
import java.io.Serializable;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;


/**
 * User: bjorn
 * Date: Apr 30, 2008
 * Time: 8:35:25 PM
 */
@Entity
@TypeDefs(
        {
            @TypeDef(name = "licenseType",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.platform.model.enums.LicenseTypeCd")}
            ),
            @TypeDef(name = "status",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd")}
            )
                }
)
public class License extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -7350335561414228490L;
    private String name = null;
    private String description = null;
    private Float price = null;
    private Date expirationDate = null;
    private LicenseTypeCd licenseType = null;
    private Application application;
    private LicenseStatusCd status = null;

    public License() {}

    public License(Application application, LicenseStatusCd status) {
        this.application = application;
        this.status = status;
    }

    @ManyToOne(optional = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Column(nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @NotNull
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

    @Type(type = "licenseType")
    @Column(nullable = false)
    @NotNull
    public LicenseTypeCd getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseTypeCd licenseType) {
        this.licenseType = licenseType;
    }

    @Type(type = "status")
    @NotNull
    @Column(nullable = false)
    public LicenseStatusCd getStatus() {
        return status;
    }

    public void setStatus(LicenseStatusCd status) {
        this.status = status;
    }
}
