/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.SystemAuditLoggerTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.AccessedEntryTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

import javax.persistence.Entity;
import java.io.Serializable;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * Every action needs to be logged for security purposes. Every time a record gets updated
 * we need to make sure we know who did it and on what entity that person did it on.
 */
@Entity
@TypeDefs(
        {
            @TypeDef(name = "logtype",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.SystemAuditLoggerTypeCd")}
            ),
            @TypeDef(name = "accessentrytype",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.SystemAuditLoggerTypeCd")}
            )
                }
)
public class SystemAuditLogger extends AbstractEntity implements Serializable {
    // this is the entity that performed an action
    private String loggerId;
    // this is the entity it was performed on
    private String loggedId;
    private String accessedEntryId;
    private String description;

    private SystemAuditLoggerTypeCd loggerType;
    private SystemAuditLoggerTypeCd loggedType;
    private AccessedEntryTypeCd accessEntryType;

    public String getLoggerId() {
        return loggerId;
    }

    public void setLoggerId(String loggerId) {
        this.loggerId = loggerId;
    }

    public String getLoggedId() {
        return loggedId;
    }

    public void setLoggedId(String loggedId) {
        this.loggedId = loggedId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Type(type = "logtype")
    public SystemAuditLoggerTypeCd getLoggerType() {
        return loggerType;
    }

    public void setLoggerType(SystemAuditLoggerTypeCd loggerType) {
        this.loggerType = loggerType;
    }

    @Type(type = "logtype")
    public SystemAuditLoggerTypeCd getLoggedType() {
        return loggedType;
    }

    public void setLoggedType(SystemAuditLoggerTypeCd loggedType) {
        this.loggedType = loggedType;
    }

    @Type(type = "accessentrytype")
    public AccessedEntryTypeCd getAccessEntryType() {
        return accessEntryType;
    }

    public void setAccessEntryType(AccessedEntryTypeCd accessEntryType) {
        this.accessEntryType = accessEntryType;
    }

    public String getAccessedEntryId() {
        return accessedEntryId;
    }

    public void setAccessedEntryId(String accessedEntryId) {
        this.accessedEntryId = accessedEntryId;
    }
}
