/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.jasypt.hibernate.type.EncryptedStringType;

import javax.persistence.Entity;
import java.io.Serializable;

import com.hxcel.globalhealth.common.model.AbstractEntity;


@TypeDefs(
        {
        @TypeDef(
                name = "encryptedString",
                typeClass = EncryptedStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
@Entity
public class Image extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 422058186814610399L;
    private String mediumIconUrl;
    private String smallIconUrl;
    private String largeIconUrl;

//    @Type(type = "encryptedString")
    public String getMediumIconUrl() {
        return mediumIconUrl;
    }

    public void setMediumIconUrl(String mediumIconUrl) {
        this.mediumIconUrl = mediumIconUrl;
    }

//    @Type(type = "encryptedString")
    public String getSmallIconUrl() {
        return smallIconUrl;
    }

    public void setSmallIconUrl(String smallIconUrl) {
        this.smallIconUrl = smallIconUrl;
    }

//    @Type(type = "encryptedString")
    public String getLargeIconUrl() {
        return largeIconUrl;
    }

    public void setLargeIconUrl(String largeIconUrl) {
        this.largeIconUrl = largeIconUrl;
    }
}
