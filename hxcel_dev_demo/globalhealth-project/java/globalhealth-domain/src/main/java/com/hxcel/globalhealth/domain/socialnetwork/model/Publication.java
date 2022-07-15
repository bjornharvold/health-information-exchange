package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 1:45:00 PM
 */
@Entity
public class Publication extends AbstractEntity {
    private String name;
    private Date publicationDate;
    private String locationUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }
}
