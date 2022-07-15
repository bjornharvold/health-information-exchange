/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao;

import com.hxcel.globalhealth.domain.phr.model.PersonalProfile;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.springframework.stereotype.Repository;

/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:45:10 AM
 * Description:
 */
@Repository
public interface PersonalProfileDAO extends GenericDAO<PersonalProfile, String> {
    PersonalProfile getRecordsByPhrId(String phrId) throws PersistenceException ;
    PersonalProfile getRecordsByRecordCreatorId(String recordCreatorId, EntityTypeCd entityType, String phrId) throws PersistenceException ;
    PersonalProfile getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException ;
}
