package com.hxcel.globalhealth.domain.socialnetwork.dto;

import com.hxcel.globalhealth.domain.common.dto.AbstractDto;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupVisibilityCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupTypeCd;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: crash
 * Date: Sep 11, 2008
 * Time: 10:29:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class GroupDto extends AbstractDto implements Serializable {
    private static final long serialVersionUID = -3146054818509212742L;
    private String name;
    private UserDto moderator;
    private String ownerEntityId;
    private EntityTypeCd ownerEntityType;
    private GroupStatusCd groupStatus;
    private GroupVisibilityCd visibility;
    private Integer groupSize;
    private GroupTypeCd groupType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDto getModerator() {
        return moderator;
    }

    public void setModerator(UserDto moderator) {
        this.moderator = moderator;
    }

    public String getOwnerEntityId() {
        return ownerEntityId;
    }

    public void setOwnerEntityId(String ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
    }

    public EntityTypeCd getOwnerEntityType() {
        return ownerEntityType;
    }

    public void setOwnerEntityType(EntityTypeCd ownerEntityType) {
        this.ownerEntityType = ownerEntityType;
    }

    public GroupStatusCd getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(GroupStatusCd groupStatus) {
        this.groupStatus = groupStatus;
    }

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

    public GroupTypeCd getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupTypeCd groupType) {
        this.groupType = groupType;
    }
}
