/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.MedicalConditionDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.MedicalCondition;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: MedicalConditionDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR medicalConditions</p>
 *
 * @author Bjorn Harvold
 */
public class MedicalConditionDAOHibernate extends AbstractHibernateDAO<MedicalCondition, String> implements MedicalConditionDAO {

    private static final Logger log = LoggerFactory.getLogger(MedicalConditionDAOHibernate.class);

    /**
     * Get a list of medicalConditions for the user
     * @param phrId
     * @return List<MedicalCondition>
     */
    public List<MedicalCondition> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(MedicalCondition.class), phrId).list();
    }

    public List<MedicalCondition> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(MedicalCondition.class), phrId, recordCreatorId, entityType).list();
    }

    public List<MedicalCondition> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(MedicalCondition.class), phrId, entityId, entityType).list();
    }

}
