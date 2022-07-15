/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.AlternativeTreatmentDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.AlternativeTreatment;
import com.hxcel.globalhealth.domain.phr.model.AdvanceDirective;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: PHRAlternativeDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR alternative treatments</p>
 *
 * @author Bjorn Harvold
 */
public class AlternativeTreatmentDAOHibernate extends AbstractHibernateDAO<AlternativeTreatment, String> implements AlternativeTreatmentDAO {

    private static final Logger log = LoggerFactory.getLogger(AlternativeTreatmentDAOHibernate.class);

    /**
     * Get a list of alternative treatments for the user
     * @param phrId
     * @return List<AlternativeTreatment>
     */
    public List<AlternativeTreatment> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(AlternativeTreatment.class), phrId).list();
    }


    public List<AlternativeTreatment> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(AlternativeTreatment.class), phrId, recordCreatorId, entityType).list();
    }

    public List<AlternativeTreatment> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(AdvanceDirective.class), phrId, entityId, entityType).list();
    }
}
