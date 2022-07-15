/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.AllergyDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Allergy;
import com.hxcel.globalhealth.domain.phr.model.AdvanceDirective;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: AllergyDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR allergies</p>
 *
 * @author Bjorn Harvold
 */

public class AllergyDAOHibernate extends AbstractHibernateDAO<Allergy, String> implements AllergyDAO {

    private static final Logger log = LoggerFactory.getLogger(AllergyDAOHibernate.class);

    /**
     * Get a list of allergies for the user
     * @param phrId
     * @return List<Allergy>
     */
    public List<Allergy> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Allergy.class), phrId).list();
    }

    /**
     * Get those allergy records that were created by this user id
     * @param recordCreatorId
     * @param phrId
     * @return
     * @throws com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException
     */
    public List<Allergy> getRecordsbyRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Allergy.class), phrId, recordCreatorId, entityType).list();
    }

    /**
     * Returns a list of allergies for an entity that has permissions to do so
     * @param entityId
     * @param entityType
     * @param phrId
     * @return
     * @throws PersistenceException
     */
    public List<Allergy> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(AdvanceDirective.class), phrId, entityId, entityType).list();
    }
}
