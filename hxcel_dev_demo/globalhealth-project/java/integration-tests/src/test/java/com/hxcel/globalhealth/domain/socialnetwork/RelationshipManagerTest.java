/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork;


import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import com.hxcel.globalhealth.domain.socialnetwork.RelationshipManager;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Recommendation;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jun 24, 2006
 * Time: 7:28:50 PM
 * Handles relationship tests
 */
public class RelationshipManagerTest extends AbstractIntegrationBaseTest {
    private static final Logger log = LoggerFactory.getLogger(RelationshipManagerTest.class);
    private final static String ENTITY_ONE = "1";
    private final static String ENTITY_TWO = "2";

    @After
    public void onTearDown() {
        try {
            relationshipManager.removeRecommendation(ENTITY_ONE, EntityTypeCd.PROFESSIONAL, ENTITY_TWO, EntityTypeCd.PROFESSIONAL);
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void testRelationship() {
        Relationship relationship = null;

        try {
            log.info("Creating relationship request between id 1 and 2");
            relationship = relationshipManager.initiateRelationship(ENTITY_ONE, ENTITY_TWO, EntityTypeCd.USER, EntityTypeCd.PROFESSIONAL, null);

            assertEquals("Expecting PENDING relationship", relationship.getStatus(), RelationshipStatusCd.PENDING);

            log.info("Retrieving status from db for relationship 1 and 2");
            RelationshipStatusCd status = relationshipManager.getRelationshipStatus(ENTITY_ONE, ENTITY_TWO);

            assertEquals("Status should be PENDING", RelationshipStatusCd.PENDING, status);

            log.info("Retreving relationships between 1 and 2");
            List<Relationship> relationships = relationshipManager.getRelationshipsByRequesterIdAndRequestedType(ENTITY_ONE, EntityTypeCd.PROFESSIONAL, RelationshipStatusCd.PENDING);

            assertEquals("There should only be one pending relationship", relationships.size(), 1);

            log.info("Rejecting relationship betwen 1 and 2");
            relationship = relationshipManager.rejectRelationship(relationship.getAuthenticationToken());
            assertEquals("Status should be DENIED", RelationshipStatusCd.DENIED, relationship.getStatus());

            status = relationshipManager.getRelationshipStatus(ENTITY_ONE, ENTITY_TWO);

            assertEquals("Status should be DENIED", RelationshipStatusCd.DENIED, status);

            log.info("Recreating relationship request between id 1 and 2");
            relationship = relationshipManager.initiateRelationship(ENTITY_ONE, ENTITY_TWO, EntityTypeCd.USER, EntityTypeCd.PROFESSIONAL, null);

            assertEquals("Expecting PENDING relationship", relationship.getStatus(), RelationshipStatusCd.PENDING);

            log.info("Retrieving status from db for relationship 1 and 2");
            status = relationshipManager.getRelationshipStatus(ENTITY_ONE, ENTITY_TWO);
            assertEquals("Expecting PENDING relationship", status, RelationshipStatusCd.PENDING);

            log.info("Confirm relationship between 1 and 2");
            relationshipManager.confirmRelationship(relationship.getAuthenticationToken());

            status = relationshipManager.getRelationshipStatus(ENTITY_ONE, ENTITY_TWO);

            assertEquals("Status should be ACTIVE", RelationshipStatusCd.ACTIVE, status);

            List<String> ids = relationshipManager.getRelationshipIds(ENTITY_ONE, EntityTypeCd.PROFESSIONAL, RelationshipStatusCd.ACTIVE);

            assertEquals("Expecting 1 relationship with professional", 1, ids.size());
            assertEquals("Expecting 1 relationship with professional id to be 2", "2", ids.get(0));

            log.info("Now we remove the relationship");
            StatusDto statusDto = relationshipManager.removeRelationship(ENTITY_ONE, EntityTypeCd.USER, ENTITY_TWO, EntityTypeCd.PROFESSIONAL);
            assertNotNull("Status is empty", statusDto);
            assertTrue("Removal did not succeed", statusDto.getSuccess());

            log.info("All RelationshipManager tests passed SUCCESSFULLY!");
        } catch (DomainException ex) {
            log.error("Error: ", ex.getMessage());
            fail();
        }
    }

    @Test
    public void testRecommendation() {
        String message = "Wow, amazing doctor!! High five.";
        Integer weight = 1;

        try {
            log.info("Creating recommendation/rating between id 1 and 2");
            Recommendation rec = relationshipManager.createRecommendation(ENTITY_ONE, EntityTypeCd.PROFESSIONAL, ENTITY_TWO, EntityTypeCd.PROFESSIONAL, message, weight);

            log.info("Getting recommendation in return");
            assertNotNull("Recommendation is null", rec);

            log.info("Matching all values");
            assertNotNull("Id is null", rec.getId());
            assertEquals("Message doesn't match", rec.getMessage(), message);
            assertEquals("Weight doesn't match", rec.getWeight(), weight);
            assertEquals("Recommendation type doesn't match", rec.getRecommendationType(), RecommendationTypeCd.RECOMMENDATION);
            assertEquals("Recommended doesn't match", rec.getRecommendedId(), ENTITY_TWO);
            assertEquals("Recommender doesn't match", rec.getRecommenderId(), ENTITY_ONE);
            assertEquals("Recommended type doesn't match", rec.getRecommendedType(), EntityTypeCd.PROFESSIONAL);
            assertEquals("Recommender type doesn't match", rec.getRecommenderType(), EntityTypeCd.PROFESSIONAL);
            log.info("All values match");

            log.info("Retrieving recommendation");
            rec = relationshipManager.getRecommendation(ENTITY_TWO, EntityTypeCd.PROFESSIONAL, ENTITY_ONE, EntityTypeCd.PROFESSIONAL);

            assertNotNull("Recommendation is null", rec);

            log.info("Matching all values again");
            assertNotNull("Id is null", rec.getId());
            assertEquals("Message doesn't match", rec.getMessage(), message);
            assertEquals("Weight doesn't match", rec.getWeight(), weight);
            assertEquals("Recommendation type doesn't match", rec.getRecommendationType(), RecommendationTypeCd.RECOMMENDATION);
            assertEquals("Recommended doesn't match", rec.getRecommendedId(), ENTITY_TWO);
            assertEquals("Recommender doesn't match", rec.getRecommenderId(), ENTITY_ONE);
            assertEquals("Recommended type doesn't match", rec.getRecommendedType(), EntityTypeCd.PROFESSIONAL);
            assertEquals("Recommender type doesn't match", rec.getRecommenderType(), EntityTypeCd.PROFESSIONAL);
            log.info("All values match again");

            log.info("Retrieving all recommendations for recommended. Should just be 1 in there");
            List<Recommendation> recs = relationshipManager.getRecommendations(ENTITY_TWO, EntityTypeCd.PROFESSIONAL, EntityTypeCd.PROFESSIONAL);

            assertNotNull("List is null", recs);
            assertEquals("List is not the right size", 1, recs.size());

            log.info("Retrieving all recommendation ids for recommended. Should just be 1 in there");
            List<String> recIds = relationshipManager.getRecommendationIds(ENTITY_TWO, EntityTypeCd.PROFESSIONAL, EntityTypeCd.PROFESSIONAL);

            assertNotNull("List is null", recIds);
            assertEquals("List is not the right size", 1, recIds.size());
            assertEquals("List value doesn't have right value", ENTITY_ONE, recIds.get(0));

            log.info("Retrieving all recommendations for recommended successful");

            log.info("RECOMMENDATION TESTS PASSED");

        } catch (DomainException ex) {
            log.error("Error: ", ex.getMessage());
            fail();
        }
    }

    // Spring IoC
    @Autowired
    private RelationshipManager relationshipManager;

}
