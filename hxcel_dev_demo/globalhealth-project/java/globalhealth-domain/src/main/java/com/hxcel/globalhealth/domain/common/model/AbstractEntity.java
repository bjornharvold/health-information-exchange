/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    private final static Logger log = LoggerFactory.getLogger(AbstractEntity.class);

    private String id = null;
    private Integer version = null;
    private Date createdDate = null;
    private Date lastUpdate = null;
    private boolean _saved = false;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Version
    @Column(nullable = false)
    @NotNull
    public Integer getVersion() {

        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean equals(Object value) {
        boolean result = false;

        /*
        if (log.isTraceEnabled()) {
            log.trace("Running equals on incoming entity: " + value);
        }
        */

        if (this == value) {
            return true;
        }

        if (getId() == null) {
            return false;
        }

        if (value instanceof AbstractEntity) {
            AbstractEntity abo = (AbstractEntity) value;

            /*
            if (log.isTraceEnabled()) {
                log.trace("\nobject: " + getClass().getSimpleName() + ", comparing ids : " + getId() + " and " + abo.getId() +
                          "\n and version: " + getVersion() + " and " + abo.getVersion() +
                          "\n and createddate: " + getCreatedDate() + " and " + abo.getCreatedDate() +
                          "\n and version: " + getLastUpdate() + " and " + abo.getLastUpdate());
            }
            */

            result = new EqualsBuilder()
                    .append(getId(), abo.getId())
                    .append(getVersion(), abo.getVersion())
                    .append(getCreatedDate(), abo.getCreatedDate())
                    .append(getLastUpdate(), abo.getLastUpdate())
                    .isEquals();
        } else {

            /*
            if (log.isTraceEnabled()) {
                log.trace("Objects of different type. Cannot compare: " + getClass().getSimpleName() + " with " + value.getClass().getSimpleName() + "("+value+")");
            }
            */
        }

        /*
        if (log.isTraceEnabled()) {
            log.trace("equals() result : " + result);
        }
        */

        return result;
    }

    public int hashCode() {
        int result = new HashCodeBuilder(17, 37)
                .append(id)
                .append(version)
                .append(createdDate)
                .append(lastUpdate).toHashCode();

        /*
        if (log.isTraceEnabled()) {
            log.trace("hashCode() for object: " + getClass().getSimpleName() + " - " + result);
        }
        */

        return result;
    }


    @Transient
    public void onSave() {
        _saved = true;

        lastUpdate = new Date(new Date().getTime());

        if (version == null) {
            version = 0;
        }

        if (createdDate == null) {
            createdDate = new Date(new Date().getTime());
        }
    }


    @Transient
    public void onLoad() {
        _saved = true;
    }


    @Transient
    public boolean isSaved() {
        return _saved;
    }
}