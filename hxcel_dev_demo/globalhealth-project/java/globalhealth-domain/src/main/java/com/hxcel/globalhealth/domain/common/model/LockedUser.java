/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.jasypt.hibernate.type.EncryptedIntegerAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@TypeDefs(
        {
        @TypeDef(
                name = "encryptedInteger",
                typeClass = EncryptedIntegerAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
public class LockedUser extends AbstractEntity implements Serializable {

    private User user;
    private Integer attempts;

    @ManyToOne
    public User getUser() {
        return this.user;
    }

    public void setUser(User value) {
        this.user = value;
    }

    @Type(type = "encryptedInteger")
    public Integer getAttempts() {
        return this.attempts;
    }

    public void setAttempts(Integer value) {
        this.attempts = value;
    }

    
}
