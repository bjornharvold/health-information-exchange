package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

/**
 * User: bjorn
 * Date: Sep 23, 2008
 * Time: 4:32:36 PM
 */
@Entity
public class ApplicationConfiguration extends AbstractEntity {
    
    private String key;
    private String value;
    private String description;
    private Boolean exportable;
    private Application application;

    @ManyToOne(optional = false)
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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

    @Column(nullable = false)
    @NotNull
    public Boolean getExportable() {
        return exportable;
    }

    public void setExportable(Boolean exportable) {
        this.exportable = exportable;
    }

    @Transient
    public String getLabel() {
        return key + " : " + value;
    }
}
