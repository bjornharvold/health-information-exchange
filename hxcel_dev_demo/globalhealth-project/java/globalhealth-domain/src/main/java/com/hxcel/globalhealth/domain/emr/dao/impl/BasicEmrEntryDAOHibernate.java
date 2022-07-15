/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao.impl;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.emr.dao.BasicEmrEntryDAO;
import com.hxcel.globalhealth.domain.emr.model.BasicEmrEntry;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

/**
 * User: Bjorn Harvold
 * Date: Feb 4, 2006
 * Time: 4:58:08 PM
 * <p/>
 * Description:
 */
public class BasicEmrEntryDAOHibernate extends AbstractHibernateDAO<BasicEmrEntry, String> implements BasicEmrEntryDAO {

    public void deleteCaseInformation(String caseInformationId, Country country) throws PersistenceException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCaseInformationPrivileges(String caseInformationId, Country country) throws PersistenceException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
