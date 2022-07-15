/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.RecordStatusCd;
import com.hxcel.globalhealth.domain.common.model.enums.RecordTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.NotNull;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 18, 2007
 * Time: 8:28:22 PM
 * This class should be extended by any entity that is a secure shareable "record" such as all phr and emr records
 */
@MappedSuperclass
public abstract class AbstractRecordEntity extends AbstractEntity {
    private RecordTypeCd recordType;
    private RecordStatusCd recordStatus;
    private String recordCreatorId;
    private EntityTypeCd recordCreatorType;
    private Boolean emergency;
    private List<Permission> permissions;
    private String notes;

    @Type(type = "encryptedString")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Type(type = "rtype")
    @Column(length = 50, nullable = false)
    @NotNull
    public RecordTypeCd getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordTypeCd recordType) {
        this.recordType = recordType;
    }

    @Type(type = "rstatus")
    @Column(length = 50, nullable = false)
    @NotNull
    public RecordStatusCd getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatusCd recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRecordCreatorId() {
        return recordCreatorId;
    }

    public void setRecordCreatorId(String recordCreatorId) {
        this.recordCreatorId = recordCreatorId;
    }

    @Type(type = "encryptedBoolean")
    @Column(nullable = false)
    @NotNull
    public Boolean getEmergency() {
        return emergency;
    }

    public void setEmergency(Boolean emergency) {
        this.emergency = emergency;
    }

    @OneToMany
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Type(type = "entitytype")
    public EntityTypeCd getRecordCreatorType() {
        return recordCreatorType;
    }

    public void setRecordCreatorType(EntityTypeCd recordCreatorType) {
        this.recordCreatorType = recordCreatorType;
    }

    @Transient
    public void addPermission(Permission p) {
        if (permissions == null) {
            permissions = new ArrayList<Permission>();
        }

        // check for existing
        for (Permission existing : permissions) {
            if (StringUtils.equals(existing.getId(), p.getId())) {
                // remove it
                permissions.remove(existing);
            }
        }

        permissions.add(p);
    }

}
