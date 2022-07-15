package com.hxcel.globalhealth.platform.controller.organization.form;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 18, 2008
 * Time: 6:30:44 PM
 * Description:
 */
public class OrganizationUserForm {
    private String organizationId;
    private List<String> userIds;

    public OrganizationUserForm(){}

    public OrganizationUserForm(String organizationId){
        this.organizationId = organizationId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public void addUserId(String id) {
        if (userIds == null) {
            userIds = new ArrayList<String>();
        }

        userIds.add(id);
    }
}