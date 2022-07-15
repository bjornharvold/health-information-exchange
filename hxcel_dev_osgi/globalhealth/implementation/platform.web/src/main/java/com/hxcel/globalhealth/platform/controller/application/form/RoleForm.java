package com.hxcel.globalhealth.platform.controller.application.form;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 16, 2008
 * Time: 5:22:54 PM
 * Description:
 */
public class RoleForm {
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String id) {
        if (roles == null) {
            roles = new ArrayList<String>();
        }

        roles.add(id);
    }
}
