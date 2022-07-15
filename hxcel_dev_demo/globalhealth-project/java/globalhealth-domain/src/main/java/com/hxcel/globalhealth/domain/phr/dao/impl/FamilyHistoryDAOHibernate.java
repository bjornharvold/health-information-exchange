/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.FamilyHistoryDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.FamilyHistory;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: FamilyHistoryDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR familyHistory</p>
 *
 * @author Bjorn Harvold
 */
public class FamilyHistoryDAOHibernate extends AbstractHibernateDAO<FamilyHistory, String> implements FamilyHistoryDAO {

    private static final Logger log = LoggerFactory.getLogger(FamilyHistoryDAOHibernate.class);

    /**
     * Get a list of familyHistorys for the user
     * @param phrId

     * @return List<FamilyHistory>
     */
    public List<FamilyHistory> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(FamilyHistory.class), phrId).list();
    }

    public List<FamilyHistory> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(FamilyHistory.class), phrId, recordCreatorId, entityType).list();
    }

    public List<FamilyHistory> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(FamilyHistory.class), phrId, entityId, entityType).list();
    }

}
