package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Country;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

/**
 * User: bjorn
 * Date: Nov 8, 2008
 * Time: 4:54:38 PM
 */
@Entity
public class ResourceBundleEntry extends AbstractEntity {
    private Application application;
    private Country country;
    private String key;
    private String value;
    private String description;

    public ResourceBundleEntry(){}
    public ResourceBundleEntry(Application application, Country country){
        this.application = application;
        this.country = country;
    }

    @ManyToOne(optional = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @ManyToOne(optional = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    @Column(nullable = false)
    @NotNull
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(nullable = false)
    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append(" : ");
        sb.append(value);
        sb.append(" : ");

        if (country != null) {
            sb.append(country.getStatusCode());
        }

        return sb.toString();
    }
}
