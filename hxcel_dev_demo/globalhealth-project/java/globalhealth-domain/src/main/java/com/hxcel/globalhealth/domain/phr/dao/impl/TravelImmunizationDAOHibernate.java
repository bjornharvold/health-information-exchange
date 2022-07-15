/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.TravelImmunizationDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.TravelImmunization;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: TravelImmunizationDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR travelImmunizations</p>
 *
 * @author Bjorn Harvold
 */
public class TravelImmunizationDAOHibernate extends AbstractHibernateDAO<TravelImmunization, String> implements TravelImmunizationDAO {

    private static final Logger log = LoggerFactory.getLogger(TravelImmunizationDAOHibernate.class);

    /**
     * Get a list of travelImmunizations for the user
     * @param phrId

     * @return List<TravelImmunization>
     */
    public List<TravelImmunization> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(TravelImmunization.class), phrId).list();
    }

    public List<TravelImmunization> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(TravelImmunization.class), phrId, recordCreatorId, entityType).list();
    }

    public List<TravelImmunization> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(TravelImmunization.class), phrId, entityId, entityType).list();
    }

}
