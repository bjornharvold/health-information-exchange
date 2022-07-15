package com.hxcel.globalhealth.domain.socialnetwork;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Recommendation;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;

import java.util.List;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 9:36:05 PM
 * Every part of the system that wants to manage some sort of relationship with something else
 * should go through this class. It will manage the relationship and post an activity log as
 * to what is happening with the relationship
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface RelationshipManager {
    /**
     * initiate a relationship.
     * @param requesterId
     * @param requestedId
     * @param requesterType
     * @param requestedType
     * @param role
     * @throws com.hxcel.globalhealth.domain.DomainException
     */
    Relationship initiateRelationship(String requesterId, String requestedId, EntityTypeCd requesterType,
                              EntityTypeCd requestedType, Integer role) throws DomainException;

    /**
     * Differs from initiateRelationship in that the relationship does not require a confirmation
     * @param requesterId
     * @param requestedId
     * @param requesterType
     * @param requestedType
     * @param role
     * @throws DomainException
     */
    Relationship createRelationshipNow(String requesterId, String requestedId, EntityTypeCd requesterType,
                              EntityTypeCd requestedType, Integer role) throws DomainException;

    /**
     * Confirm a relationship
     * @param authenticationToken
     * @throws DomainException
     */
    Relationship confirmRelationship(String authenticationToken) throws DomainException;

    /**
     * Reject a relationship
     * @param authenticationToken
     * @throws DomainException
     */
    Relationship rejectRelationship(String authenticationToken) throws DomainException;

    /**
     * Retrieve status of a relationship
     * @param requesterId
     * @param requestedId
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    RelationshipStatusCd getRelationshipStatus(String requesterId, String requestedId) throws DomainException;

    /**
     * Remove relationship
     * @param requesterId
     * @param requestedId
     * @return
     * @throws DomainException
     */
    StatusDto removeRelationship(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws DomainException;

    /**
     * Retrieve relationships based on requester id, type and status
     * @param requesterId
     * @param requestedType
     * @param status
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<Relationship> getRelationshipsByRequesterIdAndRequestedType(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws DomainException;

    /**
     * Retrieve a list of ids that belong to the requested type
     * @param requesterId
     * @param requestedType
     * @param status
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<String> getRelationshipIds(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws DomainException;

    /**
     * Will return a list of the entity objects the requester has a relationship with
     * @param requesterId
     * @param requestedType
     * @param entityClazz
     * @param status
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List getRelationshipEntities(String requesterId, EntityTypeCd requestedType, Class entityClazz, RelationshipStatusCd status) throws DomainException;

    /**
     * Retrieves a list of recommendations for an entity based on recommender entity type
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<Recommendation> getRecommendations(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException;

    /**
     * Retrieves a list of entity ids for an entity based on recommender entity type
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<String> getRecommendationIds(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException;

    /**
     * Retrieves a list of ratings for an entity based on recommender entity type
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<Recommendation> getRatings(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException;

    /**
     * Retrieves a list of entity ids for an entity based on recommender entity type
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<String> getRatingIds(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException;

    /**
     * Creates a recommendation for entity
     * @param recommenderId entity who recommends
     * @param recommenderType recommender entity type
     * @param recommendedId entity being recommended
     * @param recommendedType recommended entity type
     * @param message message
     * @param weight a number that corresponds to the level of greatness
     * @return
     * @throws DomainException
     */
    Recommendation createRecommendation(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight) throws DomainException;

    /**
     * Creates a rating for entity
     * @param recommenderId entity who recommends
     * @param recommenderType recommender entity type
     * @param recommendedId entity being recommended
     * @param recommendedType recommended entity type
     * @param message message
     * @param weight a number that corresponds to the level of greatness
     * @return
     * @throws DomainException
     */
    Recommendation createRating(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight) throws DomainException;

    /**
     * Returns a single recommendation
     * @param recommendedId
     * @param recommendedType
     * @param recommenderId
     * @param recommenderType
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    Recommendation getRecommendation(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType) throws DomainException;

    /**
     * Returns a single rating
     * @param recommendedId
     * @param recommendedType
     * @param recommenderId
     * @param recommenderType
     * @return
     * @throws DomainException
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    Recommendation getRating(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType) throws DomainException;

    StatusDto removeRecommendation(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws DomainException;
}
