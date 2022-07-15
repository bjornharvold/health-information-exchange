/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws;

import java.util.ResourceBundle;

/**
 * User: Bjorn Harvold
 * Date: Jan 5, 2007
 * Time: 12:11:23 PM
 */
public abstract class AbstractWebService {
    private String name;
    private String shortName;
    private String version;
    private String description;
    private String vendorName;
    private String vendorId;

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }


    // Spring IoC
    protected ResourceBundle resourceBundle;

    public void setResourceBundle(String resourceBundle) {
        this.resourceBundle = ResourceBundle.getBundle(resourceBundle);
    }
}
