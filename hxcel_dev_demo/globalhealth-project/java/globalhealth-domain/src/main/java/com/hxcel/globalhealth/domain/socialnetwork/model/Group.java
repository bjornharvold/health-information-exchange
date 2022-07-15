package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupVisibilityCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 1:00:31 PM
 *
 * Group for every type of entity to join and own. The moderator has to be a user
 * but the owner/creator can be a user or organization
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "gstatus",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupStatusCd")}
        ),
        @TypeDef(name = "visibility",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupVisibilityCd")}
        ),
        @TypeDef(name = "grouptype",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupTypeCd")}
        ),
        @TypeDef(name = "entitytype",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")}
        )
        }
)
public class Group extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -8190597981287277292L;
    private String name;
    private User moderator;
    private String ownerEntityId;
    private EntityTypeCd ownerEntityType;
    private GroupStatusCd groupStatus;
    private GroupVisibilityCd visibility;
    private Integer groupSize;
    private GroupTypeCd groupType;

    @NotNull
    @Column(nullable = false, length = 75, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @ManyToOne
    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    @NotNull
    @Type(type = "gstatus")
    @Column(nullable = false)
    public GroupStatusCd getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(GroupStatusCd groupStatus) {
        this.groupStatus = groupStatus;
    }

    @NotNull
    @Type(type = "visibility")
    @Column(nullable = false)
    public GroupVisibilityCd getVisibility() {
        return visibility;
    }

    public void setVisibility(GroupVisibilityCd visibility) {
        this.visibility = visibility;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    @NotNull
    @Type(type = "grouptype")
    @Column(nullable = false)
    public GroupTypeCd getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupTypeCd groupType) {
        this.groupType = groupType;
    }

    @NotNull
    @Column(nullable = false)
    public String getOwnerEntityId() {
        return ownerEntityId;
    }

    public void setOwnerEntityId(String ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
    }

    @NotNull
    @Type(type = "entitytype")
    @Column(nullable = false)
    public EntityTypeCd getOwnerEntityType() {
        return ownerEntityType;
    }

    public void setOwnerEntityType(EntityTypeCd ownerEntityType) {
        this.ownerEntityType = ownerEntityType;
    }
}
