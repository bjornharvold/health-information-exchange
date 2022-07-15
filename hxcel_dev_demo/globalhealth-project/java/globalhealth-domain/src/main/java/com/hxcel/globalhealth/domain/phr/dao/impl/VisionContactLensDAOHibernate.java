/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.VisionContactLensDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.VisionContactLenses;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: VisionContactLensDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR eyeContactLens</p>
 *
 * @author Bjorn Harvold
 */
public class VisionContactLensDAOHibernate extends AbstractHibernateDAO<VisionContactLenses, String> implements VisionContactLensDAO {

    private static final Logger log = LoggerFactory.getLogger(VisionContactLensDAOHibernate.class);

    /**
     * Get a list of eyeContactLenss for the user
     * @param phrId
     
     * @return List<VisionContactLenses>
     */
    public List<VisionContactLenses> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(VisionContactLenses.class), phrId).list();
    }

    public List<VisionContactLenses> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(VisionContactLenses.class), phrId, recordCreatorId, entityType).list();
    }

    public List<VisionContactLenses> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(VisionContactLenses.class), phrId, entityId, entityType).list();
    }

}
