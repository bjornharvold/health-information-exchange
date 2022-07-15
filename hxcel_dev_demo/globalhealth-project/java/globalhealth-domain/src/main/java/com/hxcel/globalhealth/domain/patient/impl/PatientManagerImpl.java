package com.hxcel.globalhealth.domain.patient.impl;

import com.hxcel.globalhealth.domain.patient.PatientManager;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.patient.dao.PatientDAO;
import com.hxcel.globalhealth.domain.patient.dto.PatientDto;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.dao.RelationshipDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import net.sf.dozer.util.mapping.MapperIF;

/**
 * User: bjorn
 * Date: Sep 8, 2008
 * Time: 1:23:05 PM
 */
public class PatientManagerImpl implements PatientManager {
    private final static Logger log = LoggerFactory.getLogger(PatientManagerImpl.class);

    /**
     * Returns the primary patient record for the user id.
     *
     * @param userId
     * @return
     * @throws DomainException
     */
    public PatientDto getPrimaryPatientDtoByUserId(String userId) throws DomainException {
        if (StringUtils.isBlank(userId)) {
            log.error("userId cannot be null");
            throw new DomainException("error.missing.argument.exception", "userId");
        }

        PatientDto result = null;

        Patient p = getPrimaryPatientByUserId(userId);

        if (p != null) {
            result = (PatientDto) mapperIF.map(p, PatientDto.class);
        }

        return result;
    }

    /**
     * Returns the primary patient record for the user id.
     *
     * @param userId
     * @return
     * @throws DomainException
     */
    public Patient getPrimaryPatientByUserId(String userId) throws DomainException {
        if (StringUtils.isBlank(userId)) {
            log.error("userId cannot be null");
            throw new DomainException("error.missing.argument.exception", "userId");
        }

        Patient result = null;

        try {
            // grab all patient ids for user
            List<String> patientIds = relationshipDAO.getRelationshipIds(userId, EntityTypeCd.PATIENT, RelationshipStatusCd.ACTIVE);

            if (patientIds != null && patientIds.size() > 0) {
                // find primary patient
                Patient patient = patientDAO.getPrimaryPatient(patientIds);

                // map entity to dto
                if (patient != null) {
                    result = patient;
                } else {
                    log.warn("The user with id: " + userId + " does not have a primary patient record. That should never happen.");
                }
            } else {
                log.warn("The user with id: " + userId + " does not have any patient record relationships. That should never happen.");
            }
        } catch (PersistenceException e) {
            throw new DomainException(e.getMessage(), e.getParams());
        }
        return result;
    }

    // Spring IoC
    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Autowired
    private MapperIF mapperIF;
}
