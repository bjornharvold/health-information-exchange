package com.hxcel.globalhealth.domain.common.model;

import javax.persistence.Entity;

/**
 * User: bjorn
 * Date: Oct 1, 2008
 * Time: 2:43:27 PM
 * A manager can manage a user's account
 */
@Entity
public class Manager extends AbstractEntity {
    private static final long serialVersionUID = -4869865775456129475L;
    private String username;
    private String password;
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
