/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.SurgeryDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Surgery;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: SurgeryDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR surgerys</p>
 *
 * @author Bjorn Harvold
 */
public class SurgeryDAOHibernate extends AbstractHibernateDAO<Surgery, String> implements SurgeryDAO {

    private static final Logger log = LoggerFactory.getLogger(SurgeryDAOHibernate.class);

    /**
     * Get a list of surgerys for the user
     * @param phrId
     * @return List<Surgery>
     */
    public List<Surgery> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Surgery.class), phrId).list();
    }

    public List<Surgery> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Surgery.class), phrId, recordCreatorId, entityType).list();
    }

    public List<Surgery> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Surgery.class), phrId, entityId, entityType).list();
    }

}
