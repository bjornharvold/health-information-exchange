package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Role;

import javax.persistence.ManyToOne;
import javax.persistence.Entity;

/**
 * User: Bjorn Harvold
 * Date: Oct 23, 2008
 * Time: 6:36:12 PM
 * Description:
 */
@Entity
public class ApplicationRole extends AbstractEntity {
    private Role role;
    private Application application;

    public ApplicationRole() {
    }

    public ApplicationRole(Application app, Role role) {
        this.application = app;
        this.role = role;
    }

    @ManyToOne(optional = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(optional = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
