/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.impl;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.EMRException;
import com.hxcel.globalhealth.domain.emr.JournalManager;
import com.hxcel.globalhealth.domain.emr.dao.DiagnosisDAO;
import com.hxcel.globalhealth.domain.emr.model.Diagnosis;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 10, 2007
 * Time: 11:13:18 AM
 * This class is responsible for all interactions for emr functionality
 */
public class JournalManagerImpl implements JournalManager {

    /**
     * Get diagnosis by the given diagnosis id
     * @param diagnosisId id
     * @return diagnosis object
     * @throws EMRException
     */
    public Diagnosis getDiagnosis(String diagnosisId) throws EMRException {
        Diagnosis result = null;

        try {
            result = diagnosisDAO.findById(diagnosisId, false);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    public List<Diagnosis> getDiagnoses(Country country, DiagnosisTypeCd type) throws EMRException {
        List<Diagnosis> result = null;

        try {
            result = diagnosisDAO.getDiagnoses(country, type);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    public Integer getDiagnosisCount(DiagnosisTypeCd type) throws EMRException {
        Integer result = null;

        try {
            result = diagnosisDAO.getDiagnosisCount(type);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    public Diagnosis saveOrUpdateDiagnosis(Diagnosis diagnosis) throws EMRException {
        Diagnosis result = null;

        try {
            result = diagnosisDAO.saveOrUpdate(diagnosis);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    public void saveOrUpdateAllDiagnoses(List<Diagnosis> diagnoses) throws EMRException {

        try {
            diagnosisDAO.saveOrUpdateAll(diagnoses);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

    }

    public Diagnosis saveDiagnosis(Diagnosis diagnosis) throws EMRException {
        Diagnosis result = null;

        try {
            result = diagnosisDAO.save(diagnosis);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    public Diagnosis updateDiagnosis(Diagnosis diagnosis) throws EMRException {
        Diagnosis result = null;

        try {
            result = diagnosisDAO.update(diagnosis);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    public List<Diagnosis> findByExample(Diagnosis diagnosis) throws EMRException {
        List<Diagnosis> result = null;

        try {
            result = diagnosisDAO.findByExample(diagnosis);
        } catch (PersistenceException e) {
            throw new EMRException(e.getMessage(), e);
        }

        return result;
    }

    // Spring Dao
    private DiagnosisDAO diagnosisDAO;

    @Required
    public void setDiagnosisDAO(DiagnosisDAO diagnosisDAO) {
        this.diagnosisDAO = diagnosisDAO;
    }
}
