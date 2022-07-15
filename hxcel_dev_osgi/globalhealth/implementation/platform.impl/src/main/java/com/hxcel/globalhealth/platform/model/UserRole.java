package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * User: bjorn
 * Date: Nov 13, 2008
 * Time: 1:36:28 AM
 */
@Entity
public class UserRole extends AbstractEntity implements Serializable {
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
