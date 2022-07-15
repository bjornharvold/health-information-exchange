/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.model.EmrDiagnosis;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:30:00 AM
 */
public interface EmrDiagnosisDAO extends GenericDAO<EmrDiagnosis, String> {
    void deleteDiagnosisFromCase(String diagnosisCaseId, Country country) throws PersistenceException;
    void insertProfessionalDiagnosisForCase(String journalId, String[] diagnosis, boolean isClassified, Country country);
}
