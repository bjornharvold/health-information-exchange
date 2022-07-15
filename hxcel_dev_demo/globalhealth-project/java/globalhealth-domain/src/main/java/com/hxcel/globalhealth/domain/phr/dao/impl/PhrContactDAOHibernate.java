/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.PhrContactDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.PhrContact;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: PhrContactDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR contacts</p>
 *
 * @author Bjorn Harvold
 */
public class PhrContactDAOHibernate extends AbstractHibernateDAO<PhrContact, String> implements PhrContactDAO {
    private static final Logger log = LoggerFactory.getLogger(PhrContactDAOHibernate.class);

    /**
     * Get a list of contacts for the user
     * @param phrId

     * @return List<Contact>
     */
    public List<PhrContact> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(PhrContact.class), phrId).list();
    }

    public List<PhrContact> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(PhrContact.class), phrId, recordCreatorId, entityType).list();
    }

    public List<PhrContact> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(PhrContact.class), phrId, entityId, entityType).list();
    }

}
