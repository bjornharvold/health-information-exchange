/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.ImmunizationDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.Immunization;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Title: ImmunizationDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR advance directives</p>
 *
 * @author Bjorn Harvold
 */
public class ImmunizationDAOHibernate extends AbstractHibernateDAO<Immunization, String> implements ImmunizationDAO {

    private static final Logger log = LoggerFactory.getLogger(ImmunizationDAOHibernate.class);

    /**
     * Returns advance directive record based on a phr_id
     *
     * @param phrId String
     * @return Immunization
     */
    public List<Immunization> getRecordsByPhrId(String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecords(getSession().createCriteria(Immunization.class), phrId).list();
    }

    public List<Immunization> getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(Immunization.class), phrId, recordCreatorId, entityType).list();
    }

    public List<Immunization> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(Immunization.class), phrId, entityId, entityType).list();
    }

}
