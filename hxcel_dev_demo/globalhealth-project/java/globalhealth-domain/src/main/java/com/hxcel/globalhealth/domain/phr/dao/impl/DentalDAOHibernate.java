/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.DentalDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Dental;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: DentalDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR dentals</p>
 *
 * @author Bjorn Harvold
 */

public class DentalDAOHibernate extends AbstractHibernateDAO<Dental, String> implements DentalDAO {

    private static final Logger log = LoggerFactory.getLogger(DentalDAOHibernate.class);

    /**
     * Get a list of dentals for the user
     * @param phrId

     * @return List<Dental>
     */
    public List<Dental> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Dental.class), phrId).list();
    }

    public List<Dental> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Dental.class), phrId, recordCreatorId, entityType).list();
    }

    /**
     * Returns a list of dental records to entity with permissions to access them
     * @param entityId
     * @param entityType
     * @param phrId
     * @return
     * @throws PersistenceException
     */
    public List<Dental> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Dental.class), phrId, entityId, entityType).list();
    }

}
