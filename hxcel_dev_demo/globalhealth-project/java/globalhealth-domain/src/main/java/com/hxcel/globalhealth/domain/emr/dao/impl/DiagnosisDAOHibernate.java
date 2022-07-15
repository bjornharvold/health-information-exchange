/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao.impl;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.dao.DiagnosisDAO;
import com.hxcel.globalhealth.domain.emr.model.Diagnosis;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Feb 4, 2006
 * Time: 4:58:08 PM
 * <p/>
 * Description: Diagnosis dao
 */
public class DiagnosisDAOHibernate extends AbstractHibernateDAO<Diagnosis, String> implements DiagnosisDAO {

    /**
     * Returns all diagnoses for a given country and diagnosis type (i.e. icpc or icd)
     * @param country country
     * @param type type
     * @return list of diagnoses
     * @throws com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException
     */
    public List<Diagnosis> getDiagnoses(Country country, DiagnosisTypeCd type) throws PersistenceException {
        Criteria c = getSession().createCriteria(Diagnosis.class);
        c.add(Restrictions.eq("countryCd", country));
        c.add(Restrictions.eq("diagnosisType", type));

        return c.list();
    }

    public Integer getDiagnosisCount(DiagnosisTypeCd type) throws PersistenceException {
        Criteria c = getSession().createCriteria(Diagnosis.class);
        c.add(Restrictions.eq("diagnosisType", type));
        c.setProjection(Projections.rowCount());
        return (Integer) c.list().get(0);
    }
}
