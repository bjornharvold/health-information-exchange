/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao;

import com.hxcel.globalhealth.domain.phr.model.FamilyHistory;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:45:10 AM
 * Description:
 */
@Repository
public interface FamilyHistoryDAO extends GenericDAO<FamilyHistory, String> {

    List<FamilyHistory> getRecordsByPhrId(String phrId) throws PersistenceException;
    List<FamilyHistory> getRecordsByRecordCreatorId(String recordOwnerId, EntityTypeCd entityType, String phrId) throws PersistenceException;
    List<FamilyHistory> getRecordsWithPermissions(String entityId, EntityTypeCd entityType, String phrId) throws PersistenceException;

}
