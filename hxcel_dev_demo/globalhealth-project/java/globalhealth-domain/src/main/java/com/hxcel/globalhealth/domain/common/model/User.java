/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.UserStatusCd;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.NotNull;

import org.jasypt.hibernate.type.EncryptedStringType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.List;

@Entity
@TypeDefs(
        {
            @TypeDef(name = "status",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.UserStatusCd")}
            ),
            @TypeDef(name = "country",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.CountryCd")}
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
    private String securityCode;
    private Boolean managed;
    private List<Manager> managers;
    private UserInfo userInfo;
    private String passwordConfirm;

    public User() {
        userInfo = new UserInfo();
    }

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo value) {
        this.userInfo = value;
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
    public String getSecurityCode() {
        return this.securityCode;
    }

    public void setSecurityCode(String value) {
        this.securityCode = value;
    }

    /**
     * Whether the user is managed by parent
     * @return
     */
    public Boolean getManaged() {
        return managed;
    }

    public void setManaged(Boolean managed) {
        this.managed = managed;
    }

    /**
     * Managers such as office assistants who can work with the doctor's account
     * @return
     */
    @OneToMany
    @Cascade(value = CascadeType.SAVE_UPDATE)
    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
