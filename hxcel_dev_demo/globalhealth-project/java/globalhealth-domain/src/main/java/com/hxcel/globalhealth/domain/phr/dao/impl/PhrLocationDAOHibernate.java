/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.PhrLocationDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.PhrLocation;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: PhrLocationDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR locations</p>
 *
 * @author Bjorn Harvold
 */
public class PhrLocationDAOHibernate extends AbstractHibernateDAO<PhrLocation, String> implements PhrLocationDAO {

    private static final Logger log = LoggerFactory.getLogger(PhrLocationDAOHibernate.class);

    /**
     * Get a list of locations for the user
     * @param phrId
     * @return List<Location>
     */
    public List<PhrLocation> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(PhrLocation.class), phrId).list();
    }

    public List<PhrLocation> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(PhrLocation.class), phrId, recordCreatorId, entityType).list();
    }

    public List<PhrLocation> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(PhrLocation.class), phrId, entityId, entityType).list();
    }

}
