/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.phr.model;

import com.hxcel.globalhealth.common.hibernate.type.EnumUserType;
import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.common.model.UserInfo;
import com.hxcel.globalhealth.common.model.Location;
import com.hxcel.globalhealth.phr.model.enums.ContactTypeCd;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "contactType", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.user.model.enums.ContactTypeCd")
                })
                }
)

public class Contact extends AbstractEntity implements Serializable {

    private UserInfo userInfo;
    private List<Location> locations;
    private ContactTypeCd contactType;
    private String contactTypeOther;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    public UserInfo getUserInfo() {
        return this.userInfo;
    }


    public void setUserInfo(UserInfo value) {
        this.userInfo = value;
    }

    @OneToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    public List<Location> getLocations() {
        return this.locations;
    }


    public void setLocations(List<Location> value) {
        this.locations = value;
    }

    @Type(type = "contactType")
    public ContactTypeCd getContactType() {
        return this.contactType;
    }


    public void setContactType(ContactTypeCd value) {
        this.contactType = value;
    }


    public String getContactTypeOther() {
        return this.contactTypeOther;
    }


    public void setContactTypeOther(String value) {
        this.contactTypeOther = value;
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
