/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "entitytype",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")}
        )
                }
)
public class News extends AbstractEntity implements Serializable {
    private String entityId;
    private EntityTypeCd entityType;
    private String title;
    private String content;
    private Date publishDate;
    private Boolean autoEntry;

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

    @NotNull
    @Column(nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    @Column(length = 10000)
    public String getContent() {
        return this.content;
    }

    public void setContent(String value) {
        this.content = value;
    }
    
    @Column(nullable = false)
    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(Date value) {
        this.publishDate = value;
    }

    public Boolean getAutoEntry() {
        return this.autoEntry;
    }

    public void setAutoEntry(Boolean value) {
        this.autoEntry = value;
    }
}
