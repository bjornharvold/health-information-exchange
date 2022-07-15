/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import com.hxcel.globalhealth.domain.common.model.Location;
import com.hxcel.globalhealth.domain.common.model.UserInfo;
import com.hxcel.globalhealth.domain.phr.model.enums.ProfessionCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import org.apache.commons.lang.StringUtils;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@TypeDefs(
        {
        @TypeDef(name = "profession", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.ProfessionCd")
                }),
        @TypeDef(name = "rstatus", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordStatusCd")
                }),
        @TypeDef(name = "rtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordTypeCd")
                }),
        @TypeDef(name = "entitytype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")
                }),
        @TypeDef(name = "phrtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd")
                }),
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
public class Employer extends AbstractPhrEntity implements Serializable {

    private ProfessionCd professionCd;
    private List<Location> locations;
    private UserInfo userInfo;
    private String companyName;
    private Date startDate;
    private Date endDate;
    private String professionOther;

    @Type(type = "profession")
    public ProfessionCd getProfessionCd() {
        return this.professionCd;
    }

    public void setProfessionCd(ProfessionCd value) {
        this.professionCd = value;
    }

    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo value) {
        this.userInfo = value;
    }

    @Type(type = "encryptedString")
    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String value) {
        this.companyName = value;
    }

    @Type(type = "encryptedDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Type(type = "encryptedDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Type(type = "encryptedString")
    public String getProfessionOther() {
        return this.professionOther;
    }

    public void setProfessionOther(String value) {
        this.professionOther = value;
    }

    @Transient
    public void addLocation(Location location) {
        Location dupe = null;

        if (locations == null) {
            locations = new ArrayList<Location>();
        }

        // check for duplicates
        for (Location l : locations) {
            if (StringUtils.equals(l.getId(), location.getId())) {
                dupe = l;
            }
        }

        // remove it if dupe (older version) was found
        if (dupe != null) {
            locations.remove(dupe);
        }

        locations.add(location);
    }
}
