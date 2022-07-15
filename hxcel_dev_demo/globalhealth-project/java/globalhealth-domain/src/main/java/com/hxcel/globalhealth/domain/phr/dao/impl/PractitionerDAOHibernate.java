/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: PractitionerDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR practitioners</p>
 *
 * @author Bjorn Harvold
 */
public class PractitionerDAOHibernate extends AbstractHibernateDAO<Practitioner, String> implements PractitionerDAO {
    private static final Logger log = LoggerFactory.getLogger(PractitionerDAOHibernate.class);

    /**
     * Get a list of practitioners for the user
     * @param phrId

     * @return List<Practitioner>
     */
    public List<Practitioner> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Practitioner.class), phrId).list();
    }

    public List<Practitioner> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Practitioner.class), phrId, recordCreatorId, entityType).list();
    }

    public List<Practitioner> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Practitioner.class), phrId, entityId, entityType).list();
    }

}
