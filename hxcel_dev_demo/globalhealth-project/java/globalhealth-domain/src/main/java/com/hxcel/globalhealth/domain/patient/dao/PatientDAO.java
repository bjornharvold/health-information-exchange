/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.patient.dao;

import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 4, 2005
 * Time: 10:36:42 PM

 * <p/>
 * Description:
 */
@Repository
public interface PatientDAO extends GenericDAO<Patient, String> {

    Patient getPrimaryPatient(List<String> patientIds) throws PersistenceException;
}