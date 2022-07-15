/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.CreditcardTypeCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate.type.EncryptedDateAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "cctype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.CreditcardTypeCd")}
        ),
        @TypeDef(
                name = "encryptedString",
                typeClass = EncryptedStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        ),
        @TypeDef(
                name = "encryptedBoolean",
                typeClass = EncryptedBooleanAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        ),
        @TypeDef(
                name = "encryptedDate",
                typeClass = EncryptedDateAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
                }
)
@NamedQueries(
        {
        @NamedQuery(name = "creditcard_get_creditcards_by_user_id",
                query = "SELECT cc FROM Creditcard cc WHERE cc.user.id = :userId")
                }
)
public class Creditcard extends AbstractEntity implements Serializable {

    private CreditcardTypeCd ccType;
    private String ccNumber;
    private String ccVerifyCode;
    private Date ccExpire;
    private Boolean isPreferred;
    private String ccIssuerPhone;
    private User user;

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Type(type = "cctype")
    public CreditcardTypeCd getCcType() {
        return this.ccType;
    }


    public void setCcType(CreditcardTypeCd value) {
        this.ccType = value;
    }

    @Type(type = "encryptedString")
    public String getCcNumber() {
        return this.ccNumber;
    }


    public void setCcNumber(String value) {
        this.ccNumber = value;
    }

    @Type(type = "encryptedString")
    public String getCcVerifyCode() {
        return this.ccVerifyCode;
    }


    public void setCcVerifyCode(String value) {
        this.ccVerifyCode = value;
    }

    @Type(type = "encryptedString")
    public Date getCcExpire() {
        return this.ccExpire;
    }


    public void setCcExpire(Date value) {
        this.ccExpire = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getIsPreferred() {
        return this.isPreferred;
    }


    public void setIsPreferred(Boolean value) {
        this.isPreferred = value;
    }

    @Type(type = "encryptedString")
    public String getCcIssuerPhone() {
        return this.ccIssuerPhone;
    }


    public void setCcIssuerPhone(String value) {
        this.ccIssuerPhone = value;
    }

    
}
