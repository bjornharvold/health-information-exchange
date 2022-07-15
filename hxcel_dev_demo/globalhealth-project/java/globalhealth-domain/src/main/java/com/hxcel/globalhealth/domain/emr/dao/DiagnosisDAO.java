/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.model.Diagnosis;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:30:00 AM
 */
public interface DiagnosisDAO extends GenericDAO<Diagnosis, String> {
    List<Diagnosis> getDiagnoses(Country country, DiagnosisTypeCd type) throws PersistenceException;
    Integer getDiagnosisCount(DiagnosisTypeCd type) throws PersistenceException;
}
