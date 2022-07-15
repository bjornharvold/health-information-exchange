/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.MedicationDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Medication;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: MedicationDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR medicalConditions</p>
 *
 * @author Bjorn Harvold
 */
public class MedicationDAOHibernate extends AbstractHibernateDAO<Medication, String> implements MedicationDAO {

    private static final Logger log = LoggerFactory.getLogger(MedicationDAOHibernate.class);

    /**
     * Get a list of medicalConditions for the user
     * @param phrId
     * @return List<Medication>
     */
    public List<Medication> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Medication.class), phrId).list();
    }

    public List<Medication> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Medication.class), phrId, recordCreatorId, entityType).list();
    }

    public List<Medication> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Medication.class), phrId, entityId, entityType).list();
    }

}
