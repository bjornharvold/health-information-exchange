/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.common.spec.model.enums.EntityTypeCd;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 5:20:48 AM
 */
public class PermissionDto extends AbstractDto {
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

    public EntityTypeCd getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityTypeCd entityType) {
        this.entityType = entityType;
    }

    public Boolean getViewable() {
        return viewable;
    }

    public void setViewable(Boolean viewable) {
        this.viewable = viewable;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getRemoveable() {
        return removeable;
    }

    public void setRemoveable(Boolean removeable) {
        this.removeable = removeable;
    }
}
