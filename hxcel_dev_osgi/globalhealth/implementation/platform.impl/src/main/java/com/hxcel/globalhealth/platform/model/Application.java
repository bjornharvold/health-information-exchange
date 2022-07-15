/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.model.Image;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.platform.utils.EnumUserType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * User: bjorn
 * Date: Apr 26, 2008
 * Time: 6:40:53 PM
 * An application entity defines a single flash widget
 */

@TypeDefs(
        {
            @TypeDef(name = "applicationStatus",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd")}
            ),
            @TypeDef(name = "applicationType",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd")}
            )
                }
)
@Entity
public class Application extends AbstractEntity implements Serializable {
    private final static Logger log = LoggerFactory.getLogger(Application.class);
    private String name = null;
    private Image image = null;
    private String swfUrl = null;
    private String appVersion = null;
    private Organization owner = null;
    private String description = null;
    private ApplicationStatusCd applicationStatus = null;
    private ApplicationTypeCd applicationType = null;
    private Boolean platform;

    @Column(nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @Cascade(value = CascadeType.ALL)
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSwfUrl() {
        return swfUrl;
    }

    public void setSwfUrl(String swfUrl) {
        this.swfUrl = swfUrl;
    }

    @Column(nullable = false)
    @NotNull
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @ManyToOne
    public Organization getOwner() {
        return owner;
    }

    public void setOwner(Organization owner) {
        this.owner = owner;
    }

    @Column(length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Type(type = "applicationStatus")
    @Column(nullable = false)
    @NotNull
    public ApplicationStatusCd getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatusCd applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Type(type = "applicationType")
    @Column(nullable = false)
    @NotNull
    public ApplicationTypeCd getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationTypeCd applicationType) {
        this.applicationType = applicationType;
    }

    @Column(nullable = false)
    @NotNull
    public Boolean getPlatform() {
        return platform;
    }

    public void setPlatform(Boolean platform) {
        this.platform = platform;
    }
}
