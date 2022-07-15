/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.model;

import com.hxcel.globalhealth.common.spec.model.enums.EntityTypeCd;
import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@TypeDefs(
        {
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
public class Permission extends AbstractEntity implements Serializable {
    private String entityId;
    private EntityTypeCd entityType;
    private Boolean viewable;
    private Boolean editable;
    private Boolean removeable;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Type(type = "entitytype")
    public EntityTypeCd getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityTypeCd entityType) {
        this.entityType = entityType;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getViewable() {
        return this.viewable;
    }


    public void setViewable(Boolean value) {
        this.viewable = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getEditable() {
        return this.editable;
    }


    public void setEditable(Boolean value) {
        this.editable = value;
    }

    @Type(type = "encryptedBoolean")
    public Boolean getRemoveable() {
        return this.removeable;
    }


    public void setRemoveable(Boolean value) {
        this.removeable = value;
    }

    
}
