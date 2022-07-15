package com.hxcel.globalhealth.domain.patient;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.hxcel.globalhealth.domain.patient.dto.PatientDto;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.DomainException;

/**
 * User: bjorn
 * Date: Sep 6, 2008
 * Time: 12:38:59 PM
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface PatientManager {

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    PatientDto getPrimaryPatientDtoByUserId(String userId) throws DomainException;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    Patient getPrimaryPatientByUserId(String userId) throws DomainException;
}
