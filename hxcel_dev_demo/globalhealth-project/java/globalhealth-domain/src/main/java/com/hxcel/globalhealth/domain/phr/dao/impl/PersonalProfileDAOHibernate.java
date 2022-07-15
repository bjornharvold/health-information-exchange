/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao.impl;

import com.hxcel.globalhealth.domain.phr.dao.PersonalProfileDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrCriteriaHelper;
import com.hxcel.globalhealth.domain.phr.model.PersonalProfile;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: PersonalProfileDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR advance directives</p>
 *
 * @author Bjorn Harvold
 */
public class PersonalProfileDAOHibernate extends AbstractHibernateDAO<PersonalProfile, String> implements PersonalProfileDAO {

    private static final Logger log = LoggerFactory.getLogger(PersonalProfileDAOHibernate.class);

    public PersonalProfile getRecordsByPhrId(String phrId) throws PersistenceException {
        return (PersonalProfile) PhrCriteriaHelper.getRecords(getSession().createCriteria(PersonalProfile.class), phrId).uniqueResult();
    }

    public PersonalProfile getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return (PersonalProfile) PhrCriteriaHelper.getRecordsByCreator(getSession().createCriteria(PersonalProfile.class), phrId, recordCreatorId, entityType).uniqueResult();
    }

    public PersonalProfile getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException {
        return (PersonalProfile) PhrCriteriaHelper.getRecordsWithPermissions(getSession().createCriteria(PersonalProfile.class), phrId, entityId, entityType).uniqueResult();
    }
}
