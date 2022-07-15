/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.patient.dao.impl;

import com.hxcel.globalhealth.domain.patient.dao.PatientDAO;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 4, 2005
 * Time: 10:20:24 PM

 * <p/>
 * Description: Handles persisting audit data to/from db
 */
public class PatientDAOHibernate extends AbstractHibernateDAO<Patient, String> implements PatientDAO {
    private static final Logger log = LoggerFactory.getLogger(PatientDAOHibernate.class);

    /**
     * Returns the patient object for a user that is set to primary. This is the entity that gets
     * created during user registration.
     * @param patientIds
     * @return
     * @throws PersistenceException
     */
    public Patient getPrimaryPatient(List<String> patientIds) throws PersistenceException {
        Criteria c = getSession().createCriteria(Patient.class);
        c.add(Restrictions.in("id", patientIds));
        c.add(Restrictions.eq("patientType", PatientTypeCd.PRIMARY));

        return (Patient) c.uniqueResult();
    }
}