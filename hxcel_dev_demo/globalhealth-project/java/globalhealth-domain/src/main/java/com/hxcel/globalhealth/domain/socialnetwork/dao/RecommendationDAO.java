/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.dao;


import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Recommendation;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * User: Bjorn Harvold
 * Date: Feb 13, 2007
 * Time: 10:53:50 PM
 * Responsible for storing ratings and recommendations to the recommendation table
 */
public interface RecommendationDAO extends GenericDAO<Recommendation, String> {

    List<String> getRecommendationIds(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws PersistenceException;

    /**
     * Retrieves a list of recommendations for an entity based on recommender entity type
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @return
     * @throws PersistenceException
     */
    List<Recommendation> getRecommendations(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws PersistenceException;

    /**
     * Creates a recommendation for entity
     * @param recommenderId entity who recommends
     * @param recommenderType recommender entity type
     * @param recommendedId entity being recommended
     * @param recommendedType recommended entity type
     * @param message message
     * @param weight a number that corresponds to the level of greatness
     * @return
     * @throws PersistenceException
     */
    Recommendation createRecommendation(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight, RecommendationTypeCd recommendationType) throws PersistenceException;

    /**
     * Returns a single recommendation
     * @param recommendedId
     * @param recommendedType
     * @param recommenderId
     * @param recommenderType
     * @return
     * @throws PersistenceException
     */
    Recommendation getRecommendation(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws PersistenceException;

    void removeRecommendation(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws PersistenceException;
}