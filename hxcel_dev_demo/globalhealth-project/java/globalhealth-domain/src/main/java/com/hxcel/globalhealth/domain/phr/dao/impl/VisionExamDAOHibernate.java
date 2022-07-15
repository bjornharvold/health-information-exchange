/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.VisionExamDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.VisionExam;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: VisionExamDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR eyeExams</p>
 *
 * @author Bjorn Harvold
 */
public class VisionExamDAOHibernate extends AbstractHibernateDAO<VisionExam, String> implements VisionExamDAO {

    private static final Logger log = LoggerFactory.getLogger(VisionExamDAOHibernate.class);

    /**
     * Get a list of eyeExams for the user
     * @param phrId

     * @return List<VisionExam>
     */
    public List<VisionExam> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(VisionExam.class), phrId).list();
    }

    public List<VisionExam> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(VisionExam.class), phrId, recordCreatorId, entityType).list();
    }

    public List<VisionExam> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(VisionExam.class), phrId, entityId, entityType).list();
    }
}
