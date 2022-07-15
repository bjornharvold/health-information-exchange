/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.utils.EnumUserType;
import com.hxcel.globalhealth.common.spec.model.enums.LocationTypeCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "locationType",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.common.spec.model.enums.LocationTypeCd")}
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
        )
                }
)
public class Location extends AbstractEntity implements Serializable {

    private Country country;
    private LocationTypeCd locationType;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String zip;
    private Boolean primary;

    @ManyToOne
    public Country getCountry() {
        return this.country;
    }


    public void setCountry(Country value) {
        this.country = value;
    }

    @Type(type = "locationType")
    public LocationTypeCd getLocationType() {
        return this.locationType;
    }


    public void setLocationType(LocationTypeCd value) {
        this.locationType = value;
    }

    @Type(type = "encryptedString")
    public String getAddress1() {
        return this.address1;
    }


    public void setAddress1(String value) {
        this.address1 = value;
    }

    @Type(type = "encryptedString")
    public String getAddress2() {
        return this.address2;
    }


    public void setAddress2(String value) {
        this.address2 = value;
    }

    @Type(type = "encryptedString")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    @Type(type = "encryptedString")
    public String getCity() {
        return this.city;
    }


    public void setCity(String value) {
        this.city = value;
    }

    @Type(type = "encryptedString")
    public String getState() {
        return this.state;
    }


    public void setState(String value) {
        this.state = value;
    }

    @Type(type = "encryptedString")
    public String getZip() {
        return this.zip;
    }


    public void setZip(String value) {
        this.zip = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getPrimary() {
        return this.primary;
    }

    public void setPrimary(Boolean value) {
        this.primary = value;
    }


}
