/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.model.Diagnosis;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 8, 2007
 * Time: 6:17:32 PM
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface JournalManager {
    Diagnosis getDiagnosis(String diagnosisId) throws EMRException;

    List<Diagnosis> getDiagnoses(Country country, DiagnosisTypeCd type) throws EMRException;

    Integer getDiagnosisCount(DiagnosisTypeCd type) throws EMRException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Diagnosis saveOrUpdateDiagnosis(Diagnosis diagnosis) throws EMRException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOrUpdateAllDiagnoses(List<Diagnosis> diagnoses) throws EMRException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Diagnosis saveDiagnosis(Diagnosis diagnosis) throws EMRException;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    Diagnosis updateDiagnosis(Diagnosis diagnosis) throws EMRException;

    List<Diagnosis> findByExample(Diagnosis diagnosis) throws EMRException;
}
