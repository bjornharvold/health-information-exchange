package com.hxcel.globalhealth.admin.controller.organization.form;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Bjorn Harvold
 * Date: Oct 18, 2008
 * Time: 6:30:44 PM
 * Description:
 */
public class OrganizationLicenseForm {
    private String organizationId;
    private List<String> licenseIds;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public List<String> getLicenseIds() {
        return licenseIds;
    }

    public void setLicenseIds(List<String> licenseIds) {
        this.licenseIds = licenseIds;
    }

    public void addLicenseId(String id) {
        if (licenseIds == null) {
            licenseIds = new ArrayList<String>();
        }

        licenseIds.add(id);
    }
}
