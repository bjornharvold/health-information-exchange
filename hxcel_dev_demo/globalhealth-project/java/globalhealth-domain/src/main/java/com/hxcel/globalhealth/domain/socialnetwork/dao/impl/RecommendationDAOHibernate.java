/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.dao.impl;

import com.hxcel.globalhealth.domain.socialnetwork.dao.RelationshipDAO;
import com.hxcel.globalhealth.domain.socialnetwork.dao.RecommendationDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Recommendation;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 15, 2006
 * Time: 2:19:22 PM
 * <p/>
 * <p/>
 * Description: see interface
 */
@SuppressWarnings("unchecked")
public class RecommendationDAOHibernate extends AbstractHibernateDAO<Recommendation, String> implements RecommendationDAO {
    private static final Logger log = LoggerFactory.getLogger(RecommendationDAOHibernate.class);

    /**
     * Returns a single recommendation entity
     * @param recommendedId
     * @param recommendedType
     * @param recommenderId
     * @param recommenderType
     * @param recommendationType
     * @return
     * @throws PersistenceException
     */
    public Recommendation getRecommendation(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws PersistenceException {
        Criteria c = getSession().createCriteria(Recommendation.class);
        c.add(Restrictions.eq("recommendedId", recommendedId));
        c.add(Restrictions.eq("recommendedType", recommendedType));
        c.add(Restrictions.eq("recommenderId", recommenderId));
        c.add(Restrictions.eq("recommenderType", recommenderType));
        c.add(Restrictions.eq("recommendationType", recommendationType));

        return (Recommendation) c.uniqueResult();
    }

    public void removeRecommendation(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType) throws PersistenceException {
        Criteria c = getSession().createCriteria(Recommendation.class);
        c.add(Restrictions.eq("recommendedId", recommendedId));
        c.add(Restrictions.eq("recommendedType", recommendedType));
        c.add(Restrictions.eq("recommenderId", recommenderId));
        c.add(Restrictions.eq("recommenderType", recommenderType));

        deleteAll(c.list());
    }

    /**
     * Convenience method for the public methods above. This is where it all happens
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @param recommendationType
     * @return
     * @throws PersistenceException
     */
    public List<Recommendation> getRecommendations(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws PersistenceException {
        Criteria c = getSession().createCriteria(Recommendation.class);
        c.add(Restrictions.eq("recommendedId", recommendedId));
        c.add(Restrictions.eq("recommendedType", recommendedType));
        c.add(Restrictions.eq("recommenderType", recommenderType));
        c.add(Restrictions.eq("recommendationType", recommendationType));

        return c.list();
    }

    /**
     * Returns a list of ids of entities that recommended the specified entity (recommendedId)
     * @param recommendedId
     * @param recommendedType
     * @param recommenderType
     * @param recommendationType
     * @return
     * @throws PersistenceException
     */
    public List<String> getRecommendationIds(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws PersistenceException {
        Criteria c = getSession().createCriteria(Recommendation.class);
        c.add(Restrictions.eq("recommendedId", recommendedId));
        c.add(Restrictions.eq("recommendedType", recommendedType));
        c.add(Restrictions.eq("recommenderType", recommenderType));
        c.add(Restrictions.eq("recommendationType", recommendationType));
        c.setProjection(Projections.distinct(Projections.property("recommenderId")));

        return c.list();
    }

    /**
     * Convenience method for the public methods above. This is where it all happens
     * @param recommenderId
     * @param recommenderType
     * @param recommendedId
     * @param recommendedType
     * @param message
     * @param weight
     * @param recommendationType
     * @return
     * @throws PersistenceException
     */
    public Recommendation createRecommendation(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight, RecommendationTypeCd recommendationType) throws PersistenceException {
        Recommendation rec = new Recommendation(recommenderId, recommenderType, recommendedId, recommendedType, message, weight, recommendationType);

        return save(rec);
    }
}