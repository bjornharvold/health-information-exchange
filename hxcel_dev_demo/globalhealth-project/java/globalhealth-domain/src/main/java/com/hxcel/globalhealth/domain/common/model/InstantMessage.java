/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.InstantMessageTypeCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate.type.EncryptedStringType;

import javax.persistence.Entity;
import java.io.Serializable;


/**
 * User: Bjorn Harvold
 * Date: Jun 12, 2006
 * Time: 7:18:21 PM
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "imtype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.InstantMessageTypeCd")}
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
public class InstantMessage extends AbstractEntity implements Serializable {

    private String nickname = null;
    private InstantMessageTypeCd instantMessageTypeCd = null;

    @Type(type = "encryptedString")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Type(type = "imtype")
    public InstantMessageTypeCd getInstantMessageTypeCd() {
        return instantMessageTypeCd;
    }

    public void setInstantMessageTypeCd(InstantMessageTypeCd instantMessageTypeCd) {
        this.instantMessageTypeCd = instantMessageTypeCd;
    }
    
}
