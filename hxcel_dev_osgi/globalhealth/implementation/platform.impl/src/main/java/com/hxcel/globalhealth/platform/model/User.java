/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.*;
import com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd;
import com.hxcel.globalhealth.platform.utils.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.NotNull;

import org.jasypt.hibernate.type.EncryptedStringType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@TypeDefs(
        {
                @TypeDef(name = "salutation",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.SalutationCd")}
                ),
                @TypeDef(name = "sex",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.SexCd")}
                ),
                @TypeDef(name = "status",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.UserStatusCd")}
                ),
                @TypeDef(
                        name = "encryptedString",
                        typeClass = EncryptedStringType.class,
                        parameters = {
                                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
                )
        }
)

@NamedQueries(
        {
                @NamedQuery(name = "user_authenticate_user",
                        query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"),
                @NamedQuery(name = "user_get_user_by_username",
                        query = "SELECT u FROM User u WHERE u.username = :username"),
                @NamedQuery(name = "user_get_user_by_username_exclude_user_id",
                        query = "SELECT u FROM User u WHERE u.username = :username AND u.id <> :userId"),
                @NamedQuery(name = "user_get_clients",
                        query = "SELECT u FROM User u WHERE u.id in (:ids)")

        }
)
public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -7316150740030244318L;
    private UserStatusCd userStatus;
    private String username;
    private String password;
    private Country country;
    private String identifier;
    private String passwordConfirm;
    private UserInfo userInfo;

    public User() {
        this.userInfo = new UserInfo();
    }

    @ManyToOne(optional = false)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Type(type = "status")
    @Column(nullable = false)
    @NotNull
    public UserStatusCd getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(UserStatusCd value) {
        this.userStatus = value;
    }

    @Column(unique = true, nullable = false)
    @NotNull
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    @Column(nullable = false)
    @NotNull
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    @ManyToOne(optional = false)
    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country value) {
        this.country = value;
    }

    @Column(nullable = false)
    @NotNull
    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String value) {
        this.identifier = value;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

}
