/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.InsuranceDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Insurance;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: InsuranceDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR insurances</p>
 *
 * @author Bjorn Harvold
 */
public class InsuranceDAOHibernate extends AbstractHibernateDAO<Insurance, String> implements InsuranceDAO {
    private static final Logger log = LoggerFactory.getLogger(InsuranceDAOHibernate.class);

    /**
     * Get a list of insurances for the user
     * @param phrId

     * @return List<Insurance>
     */
    public List<Insurance> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Insurance.class), phrId).list();
    }

    public List<Insurance> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Insurance.class), phrId, recordCreatorId, entityType).list();
    }

    public List<Insurance> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Insurance.class), phrId, entityId, entityType).list();
    }

}
