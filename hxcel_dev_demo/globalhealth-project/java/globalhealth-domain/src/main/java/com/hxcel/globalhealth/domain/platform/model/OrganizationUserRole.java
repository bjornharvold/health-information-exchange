package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 24, 2008
 * Time: 3:21:53 PM
 * Description:
 */
@Entity
public class OrganizationUserRole extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -553350232284377516L;
    private OrganizationUser user;
    private Role role;

    public OrganizationUserRole(){}

    public OrganizationUserRole(OrganizationUser user, Role role){
        this.user = user;
        this.role = role;
    }

    @ManyToOne(optional = false)
    public OrganizationUser getUser() {
        return user;
    }

    public void setUser(OrganizationUser user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
