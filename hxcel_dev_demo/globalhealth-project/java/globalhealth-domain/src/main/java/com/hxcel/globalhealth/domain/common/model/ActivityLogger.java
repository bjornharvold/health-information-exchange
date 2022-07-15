package com.hxcel.globalhealth.domain.common.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.enums.ActivityLoggerTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 2:19:36 PM
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "activitystatus",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.ActivityLoggerTypeCd")}
        )
        }
)
public class ActivityLogger extends AbstractEntity {
    private String description;
    private ActivityLoggerTypeCd type;

    // the id for which the activity occurred
    private String entityId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Type(type = "activitystatus")
    public ActivityLoggerTypeCd getActivityLoggerType() {
        return type;
    }

    public void setActivityLoggerType(ActivityLoggerTypeCd activityLoggerType) {
        this.type = activityLoggerType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
