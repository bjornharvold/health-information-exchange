package com.hxcel.globalhealth.domain.common.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * User: bjorn
 * Date: Nov 13, 2008
 * Time: 1:36:28 AM
 */
@Entity
public class UserRole extends AbstractEntity {
    private Role role;
    private User user;

    public UserRole(){}

    public UserRole(User user, Role role) {
        this.user = user;
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
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
