/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.EmployerDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Employer;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: EmployerDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR employers</p>
 *
 * @author Bjorn Harvold
 */
public class EmployerDAOHibernate extends AbstractHibernateDAO<Employer, String> implements EmployerDAO {

    private static final Logger log = LoggerFactory.getLogger(EmployerDAOHibernate.class);

    /**
     * Get a list of employers for the user
     * @param phrId
     * @return List<Employer>
     */
    public List<Employer> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Employer.class), phrId).list();
    }

     /**
     * Get a list of employers for the user that professional has privs for
     * @param recordCreatorId
     * @param entityType
     * @param phrId
     * @return
     * @throws PersistenceException
     */
    public List<Employer> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Employer.class), phrId, recordCreatorId, entityType).list();
    }

    /**
     * Returns a list of employers to entity with access permissions
     * @param entityId
     * @param entityType
     * @param phrId
     * @return
     * @throws PersistenceException
     */
    public List<Employer> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Employer.class), phrId, entityId, entityType).list();
    }
}
