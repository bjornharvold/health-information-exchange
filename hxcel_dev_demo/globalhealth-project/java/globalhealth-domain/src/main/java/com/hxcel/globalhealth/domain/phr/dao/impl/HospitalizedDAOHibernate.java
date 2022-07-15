/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.HospitalizedDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Hospitalized;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: HospitalizedDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR hospitalizeds</p>
 *
 * @author Bjorn Harvold
 */
public class HospitalizedDAOHibernate extends AbstractHibernateDAO<Hospitalized, String> implements HospitalizedDAO {

    private static final Logger log = LoggerFactory.getLogger(HospitalizedDAOHibernate.class);

    /**
     * Get a list of hospitalizeds for the user
     * @param phrId

     * @return List<Hospitalized>
     */
    public List<Hospitalized> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Hospitalized.class), phrId).list();
    }

    public List<Hospitalized> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Hospitalized.class), phrId, recordCreatorId, entityType).list();
    }

    public List<Hospitalized> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Hospitalized.class), phrId, entityId, entityType).list();
    }

}
