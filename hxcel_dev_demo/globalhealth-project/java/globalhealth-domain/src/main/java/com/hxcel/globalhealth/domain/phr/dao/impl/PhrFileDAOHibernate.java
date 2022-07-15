/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.PhrFileDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.PhrFile;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: PhrFileDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR phrFile</p>
 *
 * @author Bjorn Harvold
 */
public class PhrFileDAOHibernate extends AbstractHibernateDAO<PhrFile, String> implements PhrFileDAO {

    private static final Logger log = LoggerFactory.getLogger(PhrFileDAOHibernate.class);

    /**
     * Get a list of phrFiles for the user
     *
     * @param phrId
     * @return List<PhrFiles>
     */
    public List<PhrFile> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(PhrFile.class), phrId).list();
    }

    public List<PhrFile> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(PhrFile.class), phrId, recordCreatorId, entityType).list();
    }

    public List<PhrFile> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(PhrFile.class), phrId, entityId, entityType).list();
    }

}
