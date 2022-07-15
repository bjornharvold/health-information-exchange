/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * This entity is responsible for managing the relationship between a patient and a doctor
 */
@Entity
@TypeDefs(
        {
            @TypeDef(name = "relstatus",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd")}
            ),
            @TypeDef(name = "requestType",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")}
            )

                }
)
@NamedQueries(
        {
            @NamedQuery(name = "relationship_get_requested_ids_only",
                    query = "SELECT r.requestedId FROM Relationship r WHERE " +
                            "r.requesterId = :requesterId AND r.requestedType = :requestedType " +
                            "AND r.status = :status")
                }
)
public class Relationship extends AbstractEntity implements Serializable {
    private String requesterId;
    private String requestedId;
    private EntityTypeCd requesterType;
    private EntityTypeCd requestedType;
    private Integer role;
    private RelationshipStatusCd status;
    private String authenticationToken;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Type(type = "relstatus")
    public RelationshipStatusCd getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatusCd status) {
        this.status = status;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequestedId() {
        return requestedId;
    }

    public void setRequestedId(String requestedId) {
        this.requestedId = requestedId;
    }

    @Type(type = "requestType")
    public EntityTypeCd getRequesterType() {
        return requesterType;
    }

    public void setRequesterType(EntityTypeCd entityType) {
        this.requesterType = entityType;
    }

    @Type(type = "requestType")
    public EntityTypeCd getRequestedType() {
        return requestedType;
    }

    public void setRequestedType(EntityTypeCd requestedType) {
        this.requestedType = requestedType;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}
