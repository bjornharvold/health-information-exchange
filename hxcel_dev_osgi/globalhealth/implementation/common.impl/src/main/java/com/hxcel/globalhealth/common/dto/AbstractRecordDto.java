/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.dto;

import com.hxcel.globalhealth.common.spec.model.enums.RecordStatusCd;
import com.hxcel.globalhealth.common.spec.model.enums.RecordTypeCd;
import com.hxcel.globalhealth.common.dto.AbstractDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 5:18:34 AM
 */
public class AbstractRecordDto extends AbstractDto {
    private RecordTypeCd recordType;
    private RecordStatusCd recordStatus;
    private String recordCreator;
    private Boolean emergency;
    private List<PermissionDto> permissions;
    private String notes;

    public RecordTypeCd getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordTypeCd recordType) {
        this.recordType = recordType;
    }

    public RecordStatusCd getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatusCd status) {
        this.recordStatus = status;
    }

    public String getRecordCreator() {
        return recordCreator;
    }

    public void setRecordCreator(String recordCreator) {
        this.recordCreator = recordCreator;
    }

    public Boolean getEmergency() {
        return emergency;
    }

    public void setEmergency(Boolean emergency) {
        this.emergency = emergency;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void addPermission(PermissionDto p) {
        if (permissions == null) {
            permissions = new ArrayList<PermissionDto>();
        }
        permissions.add(p);
    }
}
