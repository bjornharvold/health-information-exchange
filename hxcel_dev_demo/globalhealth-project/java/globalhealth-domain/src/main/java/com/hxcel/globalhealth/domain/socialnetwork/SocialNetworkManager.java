package com.hxcel.globalhealth.domain.socialnetwork;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.News;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.socialnetwork.model.Group;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupVisibilityCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.dto.GroupDto;

import java.util.List;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 12:56:14 PM
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface SocialNetworkManager {
    // group functionality
    GroupDto saveOrUpdateGroupDto(GroupDto group) throws DomainException;
    Group saveOrUpdateGroup(Group group) throws DomainException;
    StatusDto organizationRequestsGroupMembership(String groupId, String organizationId) throws DomainException;
    StatusDto userRequestsGroupMembership(String groupId, String userId) throws DomainException;
    StatusDto removeUserFromGroup(String groupId, String userId) throws DomainException;
    StatusDto acceptRequestToJoinGroup(String token) throws DomainException;
    StatusDto ignoreRequestToJoinGroup(String token) throws DomainException;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    GroupDto getGroup(String groupId) throws DomainException;
    List<Group> getUserGroups(String userId) throws DomainException;
    List<Group> getModeratedUserGroups(String userId) throws DomainException;
    List<News> getGroupNews(String userId) throws DomainException;
}
