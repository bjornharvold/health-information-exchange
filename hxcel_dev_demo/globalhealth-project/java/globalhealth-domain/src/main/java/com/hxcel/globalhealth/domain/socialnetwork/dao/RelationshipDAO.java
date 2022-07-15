/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.dao;


import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Feb 13, 2007
 * Time: 10:53:50 PM
 * Dao manages all types of relationships in the system between
 * User, Organization, Professional, Application, Groups etc.
 */
public interface RelationshipDAO extends GenericDAO<Relationship, String> {
    /**
     * initiate a relationship.
     * @param requesterId
     * @param requestedId
     * @param requesterType
     * @param requestedType
     * @param role
     * @param authenticationToken
     * @throws PersistenceException
     */
    Relationship initiateRelationship(String requesterId, String requestedId, EntityTypeCd requesterType,
                              EntityTypeCd requestedType, Integer role, String authenticationToken) throws PersistenceException;

    /**
     * Differs from initiateRelationship in that the relationship does not require a confirmation
     * @param requesterId
     * @param requestedId
     * @param requesterType
     * @param requestedType
     * @param role
     * @throws PersistenceException
     */
    Relationship createRelationshipNow(String requesterId, String requestedId, EntityTypeCd requesterType,
                              EntityTypeCd requestedType, Integer role) throws PersistenceException;

    /**
     * Confirm a relationship
     * @param authenticationToken
     * @throws PersistenceException
     */
    Relationship confirmRelationship(String authenticationToken) throws PersistenceException;

    /**
     * Reject a relationship
     * @param authenticationToken
     * @throws PersistenceException
     */
    Relationship rejectRelationship(String authenticationToken) throws PersistenceException;

    /**
     * Retrieve status of a relationship
     * @param requesterId
     * @param requestedId
     * @return
     * @throws PersistenceException
     */
    RelationshipStatusCd getRelationshipStatus(String requesterId, String requestedId) throws PersistenceException;

    /**
     * Retrieve relationships based on requester id, requested type and status
     * @param requesterId
     * @param requestedType
     * @param status
     * @return
     * @throws PersistenceException
     */
    List<Relationship> getRelationshipsByRequesterIdAndRequestedType(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws PersistenceException;

    /**
     * Retrieves relationships based on requested id, requester type and status
     * @param requestedId
     * @param requesterType
     * @param status
     * @return
     * @throws PersistenceException
     */
    List<Relationship> getRelationshipsByRequestedIdAndRequesterType(String requestedId, EntityTypeCd requesterType, RelationshipStatusCd status) throws PersistenceException;

    /**
     * Same as above but returns only the ids of the requested type
     * @param requesterId
     * @param requestedType
     * @param status
     * @return
     */
    List<String> getRelationshipIds(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws PersistenceException;

    List getRelationshipEntities(List<String> ids, Class entityClazz);

    String removeRelationship(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws PersistenceException;
}
