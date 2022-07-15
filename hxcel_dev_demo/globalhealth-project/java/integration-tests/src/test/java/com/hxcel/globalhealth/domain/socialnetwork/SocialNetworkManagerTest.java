package com.hxcel.globalhealth.domain.socialnetwork;


import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.*;
import com.hxcel.globalhealth.domain.socialnetwork.model.Group;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.dto.GroupDto;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Sep 12, 2008
 * Time: 2:07:22 PM
 * Description:
 */
public class SocialNetworkManagerTest extends AbstractIntegrationBaseTest {
    private static final Logger log = LoggerFactory.getLogger(SocialNetworkManagerTest.class);

    @Test
    public void testAddUserToPublicGroupDto() {
        GroupDto dto = null;

        try {
            log.info("First we create a public group dto");
            dto = socialNetworkManager.saveOrUpdateGroupDto(makeGroupDto(GroupVisibilityCd.PUBLIC));
            assertNotNull("GroupDto couldn't be created", dto);
            assertNotNull("Group id doesn't exist", dto.getId());

            log.info("Then we add a user to that group");

            StatusDto status = socialNetworkManager.userRequestsGroupMembership(dto.getId(), getUser().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

            log.info("Then we remove a user from the group");
            status = socialNetworkManager.removeUserFromGroup(dto.getId(), getUser().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail();
        }
    }

    @Test
    public void testAddUserToPublicGroup() {
        Group group = null;

        try {
            log.info("First we create a public group");
            group = socialNetworkManager.saveOrUpdateGroup(makeGroup(GroupVisibilityCd.PUBLIC));
            assertNotNull("GroupDto couldn't be created", group);
            assertNotNull("Group id doesn't exist", group.getId());

            log.info("Then we add a user to that group");

            StatusDto status = socialNetworkManager.userRequestsGroupMembership(group.getId(), getUserDto().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

            log.info("Then we remove a user from the group");
            status = socialNetworkManager.removeUserFromGroup(group.getId(), getUserDto().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail();
        }
    }

    @Test
    public void testAddUserToPrivateGroup() {
        Group group = null;

        try {
            log.info("First we create a private group");
            group = socialNetworkManager.saveOrUpdateGroup(makeGroup(GroupVisibilityCd.PRIVATE));
            assertNotNull("GroupDto couldn't be created", group);
            assertNotNull("Group id doesn't exist", group.getId());

            log.info("Then we add a user request to join that group");

            StatusDto status = socialNetworkManager.userRequestsGroupMembership(group.getId(), getUserDto().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());
            assertNotNull("Authentication token is null", status.getToken());

            log.info("Then we approve the request");
            status = socialNetworkManager.acceptRequestToJoinGroup(status.getToken());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

            log.info("Request approved successfully. Check the group list to verify user has been added.");
            List<Relationship> relationships = relationshipManager.getRelationshipsByRequesterIdAndRequestedType(getUserDto().getId(), EntityTypeCd.GROUP, RelationshipStatusCd.ACTIVE);
            boolean found = false;

            for (Relationship relationship : relationships) {
                if (StringUtils.equals(relationship.getRequestedId(), group.getId()) && relationship.getStatus().equals(RelationshipStatusCd.ACTIVE)) {
                    found = true;
                }
            }

            assertTrue("User didn't get added to the group", found);

            log.info("Then we remove a user from the group");
            status = socialNetworkManager.removeUserFromGroup(group.getId(), getUserDto().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

            log.info("Then we add another user request to join that group");
            status = socialNetworkManager.userRequestsGroupMembership(group.getId(), getUserDto().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());
            assertNotNull("Authentication token is null", status.getToken());

            log.info("Then we reject the request");
            status = socialNetworkManager.ignoreRequestToJoinGroup(status.getToken());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

            log.info("Request rejected successfully. Check the group list to verify user has been added.");
            relationships = relationshipManager.getRelationshipsByRequesterIdAndRequestedType(getUserDto().getId(), EntityTypeCd.GROUP, RelationshipStatusCd.ACTIVE);
            found = false;

            for (Relationship relationship : relationships) {
                if (StringUtils.equals(relationship.getRequestedId(), group.getId()) && relationship.getStatus().equals(RelationshipStatusCd.ACTIVE)) {
                    found = true;
                }
            }

            assertFalse("User got added to the group anyway", found);

            log.info("Then we remove a user from the group");
            status = socialNetworkManager.removeUserFromGroup(group.getId(), getUserDto().getId());
            assertNotNull("Status is null", status);
            assertNotNull("Id is null", status.getEntityId());
            assertTrue("Status is false", status.getSuccess());

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail();
        }
    }

    private GroupDto makeGroupDto(GroupVisibilityCd visibility) {
        GroupDto g = new GroupDto();
        g.setName(RandomStringUtils.randomAlphabetic(5));
        g.setGroupSize(Integer.parseInt(RandomStringUtils.randomNumeric(2)));
        g.setGroupStatus(GroupStatusCd.ACTIVE);
        g.setGroupType(GroupTypeCd.CANCER);
        g.setModerator(getUserDto());
        g.setOwnerEntityId(getUser().getId());
        g.setOwnerEntityType(EntityTypeCd.USER);
        g.setVisibility(visibility);

        return g;
    }

    private Group makeGroup(GroupVisibilityCd visibility) {
        Group g = new Group();
        g.setName(RandomStringUtils.randomAlphabetic(5));
        g.setGroupSize(Integer.parseInt(RandomStringUtils.randomNumeric(2)));
        g.setGroupStatus(GroupStatusCd.ACTIVE);
        g.setGroupType(GroupTypeCd.CANCER);
        g.setModerator(getUser());
        g.setOwnerEntityId(getUser().getId());
        g.setOwnerEntityType(EntityTypeCd.USER);
        g.setVisibility(visibility);

        return g;
    }

    // Spring IoC
    @Autowired
    private SocialNetworkManager socialNetworkManager;

}
