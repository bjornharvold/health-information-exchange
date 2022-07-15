package com.hxcel.globalhealth.domain.socialnetwork.impl;

import com.hxcel.globalhealth.domain.socialnetwork.SocialNetworkManager;
import com.hxcel.globalhealth.domain.socialnetwork.RelationshipManager;
import com.hxcel.globalhealth.domain.socialnetwork.dao.GroupDAO;
import com.hxcel.globalhealth.domain.socialnetwork.dto.GroupDto;
import com.hxcel.globalhealth.domain.socialnetwork.model.Group;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.GroupVisibilityCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.common.AbstractManager;
import com.hxcel.globalhealth.domain.common.model.News;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: bjorn
 * Date: Aug 16, 2008
 * Time: 8:59:02 AM
 * Responsible for all aspects of functionality that pertains to socil networking
 */
public class SocialNetworkManagerImpl extends AbstractManager implements SocialNetworkManager {
    private static final Logger log = LoggerFactory.getLogger(SocialNetworkManagerImpl.class);

    /**
     * Saves or updates a group dto
     *
     * @param dto
     * @return
     * @throws DomainException
     */
    public GroupDto saveOrUpdateGroupDto(GroupDto dto) throws DomainException {
        Group g = (Group) mapperIF.map(dto, Group.class);
        g = saveOrUpdateGroup(g);
        dto = (GroupDto) mapperIF.map(g, GroupDto.class);

        return dto;
    }

    /**
     * Saves or updates a group
     *
     * @param group
     * @return
     * @throws DomainException
     */
    public Group saveOrUpdateGroup(Group group) throws DomainException {
        // validate group
        if (group == null) {
            log.error("group is null");
            throw new DomainException("error.missing.argument.exception", "group");
        }
        if (StringUtils.isBlank(group.getName())) {
            log.error("groupName is null");
            throw new DomainException("error.missing.argument.exception", "groupName");
        }
        if (StringUtils.isBlank(group.getOwnerEntityId())) {
            log.error("ownerEntityId is null");
            throw new DomainException("error.missing.argument.exception", "ownerEntityId");
        }
        if (group.getOwnerEntityType() == null) {
            log.error("ownerEntityType is null");
            throw new DomainException("error.missing.argument.exception", "ownerEntityType");
        }
        if (group.getGroupType() == null) {
            log.error("groupType is null");
            throw new DomainException("error.missing.argument.exception", "groupType");
        }
        if (group.getModerator() == null) {
            log.error("moderator is null");
            throw new DomainException("error.missing.argument.exception", "moderator");
        }
        if (group.getVisibility() == null) {
            log.error("visibility is null");
            throw new DomainException("error.missing.argument.exception", "visibility");
        }

        try {
            group = groupDAO.saveOrUpdate(group);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return group;
    }

    /**
     * Creates group org relationship or request depending on group visibility
     *
     * @param groupId
     * @param organizationId
     * @return
     * @throws DomainException
     */
    public StatusDto organizationRequestsGroupMembership(String groupId, String organizationId) throws DomainException {
        return entityRequestsGroupMembership(groupId, organizationId, EntityTypeCd.ORGANIZATION);
    }

    /**
     * Creates group user relationship or request dependning on group visibility
     *
     * @param groupId
     * @param userId
     * @return
     * @throws DomainException
     */
    public StatusDto userRequestsGroupMembership(String groupId, String userId) throws DomainException {
        return entityRequestsGroupMembership(groupId, userId, EntityTypeCd.USER);
    }

    public StatusDto removeUserFromGroup(String groupId, String userId) throws DomainException {
        if (StringUtils.isBlank(groupId)) {
            log.error("groupId is null");
            throw new DomainException("error.missing.argument.exception", "groupId");
        }
        if (StringUtils.isBlank(userId)) {
            log.error("userId is null");
            throw new DomainException("error.missing.argument.exception", "userId");
        }

        return relationshipManager.removeRelationship(userId, EntityTypeCd.USER, groupId, EntityTypeCd.GROUP);
    }

    /**
     * Accepts relationship request based on the token
     *
     * @param token
     * @return
     * @throws DomainException
     */
    public StatusDto acceptRequestToJoinGroup(String token) throws DomainException {
        if (StringUtils.isBlank(token)) {
            log.error("token is null");
            throw new DomainException("error.missing.argument.exception", "token");
        }

        Relationship r = relationshipManager.confirmRelationship(token);
        StringBuilder sysMsg = new StringBuilder();
        sysMsg.append("Relationship confirmed for token: ");
        sysMsg.append(token);
        sysMsg.append(" Relationship ID: ");
        sysMsg.append(r.getId());
        sysMsg.append(" Relationship status: ");
        sysMsg.append(r.getStatus());

        String code = "socialnetwork.group.confirm";
        StatusDto result = new StatusDto(sysMsg.toString(), code, null, true, r.getId(), r.getStatus().name());

        return result;
    }

    /**
     * Rejects relationship request based on token
     *
     * @param token
     * @return
     * @throws DomainException
     */
    public StatusDto ignoreRequestToJoinGroup(String token) throws DomainException {
        if (StringUtils.isBlank(token)) {
            log.error("token is null");
            throw new DomainException("error.missing.argument.exception", "token");
        }

        Relationship r = relationshipManager.rejectRelationship(token);
        StringBuilder sysMsg = new StringBuilder();
        sysMsg.append("Relationship rejected for token: ");
        sysMsg.append(token);
        sysMsg.append(" Relationship ID: ");
        sysMsg.append(r.getId());
        sysMsg.append(" Relationship status: ");
        sysMsg.append(r.getStatus());

        String code = "socialnetwork.group.reject";
        StatusDto result = new StatusDto(sysMsg.toString(), code, null, true, r.getId(), r.getStatus().name());

        return result;
    }

    /**
     * Retrieves a group based on group id
     *
     * @param groupId
     * @return
     * @throws DomainException
     */
    public GroupDto getGroup(String groupId) throws DomainException {
        GroupDto result = null;

        if (StringUtils.isBlank(groupId)) {
            log.error("groupId is null");
            throw new DomainException("error.missing.argument.exception", "groupId");
        }

        try {
            // retrieve from db
            Group group = groupDAO.get(Group.class, groupId);
            // map it to groupdto
            result = (GroupDto) mapperIF.map(group, GroupDto.class);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    /**
     * TODO implement
     * @param userId
     * @return
     * @throws DomainException
     */
    public List<Group> getUserGroups(String userId) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * TODO implement
     * @param userId
     * @return
     * @throws DomainException
     */
    public List<Group> getModeratedUserGroups(String userId) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * TODO implement
     * @param userId
     * @return
     * @throws DomainException
     */
    public List<News> getGroupNews(String userId) throws DomainException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private StatusDto entityRequestsGroupMembership(String groupId, String entityId, EntityTypeCd entityType) throws DomainException {
        StatusDto result = null;
        StringBuilder systemMessage = null;
        String[] params;
        Relationship r = null;
        String code = null;

        if (StringUtils.isBlank(groupId)) {
            log.error("groupId is null");
            throw new DomainException("error.missing.argument.exception", "groupId");
        }
        if (StringUtils.isBlank(entityId)) {
            log.error("entityId is null");
            throw new DomainException("error.missing.argument.exception", "entityId");
        }

        try {
            // retrieve from db
            Group group = groupDAO.get(Group.class, groupId);

            if (group.getVisibility().equals(GroupVisibilityCd.PUBLIC)) {
                // go ahead and approve the request immediately
                r = relationshipManager.createRelationshipNow(entityId, groupId, entityType, EntityTypeCd.GROUP, null);
                code = "socialnetwork.group.added";
            } else if (group.getVisibility().equals(GroupVisibilityCd.PRIVATE)) {
                // create a relationship request for the moderator to approve
                r = relationshipManager.initiateRelationship(entityId, groupId, entityType, EntityTypeCd.GROUP, null);
                code = "socialnetwork.group.request";
            }

            // create the status message
            systemMessage = new StringBuilder("Entity with ID: ");
            systemMessage.append(entityId);
            systemMessage.append(" with entityType ");
            systemMessage.append(entityType);
            systemMessage.append(" was added to a ");
            systemMessage.append(group.getVisibility());
            systemMessage.append(" group with ID: ");
            systemMessage.append(groupId);
            systemMessage.append(" and name: ");
            systemMessage.append(group.getName());
            systemMessage.append(". Status: ");
            systemMessage.append(r.getStatus());
            systemMessage.append(". Relationship ID: ");
            systemMessage.append(r.getId());
            params = new String[]{group.getName()};

            result = new StatusDto(systemMessage.toString(), code, params, true, r.getId(), r.getAuthenticationToken(), r.getStatus().name());
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private RelationshipManager relationshipManager;
}
