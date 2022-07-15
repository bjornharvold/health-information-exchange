/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.VisionGlassesDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.VisionGlasses;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: VisionGlassesDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR eyeGlasses</p>
 *
 * @author Bjorn Harvold
 */
public class VisionGlassesDAOHibernate extends AbstractHibernateDAO<VisionGlasses, String> implements VisionGlassesDAO {

    private static final Logger log = LoggerFactory.getLogger(VisionGlassesDAOHibernate.class);

    /**
     * Get a list of eyeGlassess for the user
     *
     * @param phrId

     * @return List<VisionGlasses>
     */
    public List<VisionGlasses> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(VisionGlasses.class), phrId).list();
    }

    public List<VisionGlasses> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(VisionGlasses.class), phrId, recordCreatorId, entityType).list();
    }

    public List<VisionGlasses> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(VisionGlasses.class), phrId, entityId, entityType).list();
    }

}
