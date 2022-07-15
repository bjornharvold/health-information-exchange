/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.model.BasicEmrEntry;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:30:00 AM
 */
public interface BasicEmrEntryDAO extends GenericDAO<BasicEmrEntry, String> {
    /**
     * Deletes only cases younger than 24 hours
     * @param caseInformationId String
     * @param country
     */
    void deleteCaseInformation(String caseInformationId, Country country) throws PersistenceException;
    void setCaseInformationPrivileges(String caseInformationId, Country country) throws PersistenceException;
}
