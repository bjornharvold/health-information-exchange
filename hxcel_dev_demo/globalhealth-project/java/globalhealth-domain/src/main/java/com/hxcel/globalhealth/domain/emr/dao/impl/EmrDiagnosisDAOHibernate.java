/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao.impl;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.dao.EmrDiagnosisDAO;
import com.hxcel.globalhealth.domain.emr.model.EmrDiagnosis;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Feb 4, 2006
 * Time: 4:58:08 PM
 * <p/>
 * Description:
 */
public class EmrDiagnosisDAOHibernate extends AbstractHibernateDAO<EmrDiagnosis, String> implements EmrDiagnosisDAO {

    public void deleteDiagnosisFromCase(String diagnosisCaseId, Country country) throws PersistenceException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void insertProfessionalDiagnosisForCase(String journalId, String[] diagnosis, boolean isClassified, Country country) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
