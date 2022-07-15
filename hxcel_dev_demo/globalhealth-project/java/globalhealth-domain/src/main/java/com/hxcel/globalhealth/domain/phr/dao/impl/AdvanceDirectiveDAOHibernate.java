/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.AdvanceDirectiveDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.AdvanceDirective;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: AdvanceDirectiveDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR advance directives</p>
 *
 * @author Bjorn Harvold
 */
public class AdvanceDirectiveDAOHibernate extends AbstractHibernateDAO<AdvanceDirective, String> implements AdvanceDirectiveDAO {

    private static final Logger log = LoggerFactory.getLogger(AdvanceDirectiveDAOHibernate.class);

    /**
     * Returns advance directive record based on a phr_id
     *
     * @param phrId String
     * @return AdvanceDirective
     */
    public List<AdvanceDirective> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(AdvanceDirective.class), phrId).list();
    }

    /**
     * return an advance directive if professional has privs to view it
     *
     * @param recordCreatorId
     * @param phrId
     * @return
     * @throws PersistenceException
     */
    public List<AdvanceDirective> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(AdvanceDirective.class), phrId, recordCreatorId, entityType).list();
    }

    public List<AdvanceDirective> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(AdvanceDirective.class), phrId, entityId, entityType).list();
    }

}
