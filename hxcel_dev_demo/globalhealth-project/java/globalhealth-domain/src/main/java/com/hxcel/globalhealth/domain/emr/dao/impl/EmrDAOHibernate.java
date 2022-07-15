/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao.impl;

import com.hxcel.globalhealth.domain.emr.dao.EmrDAO;
import com.hxcel.globalhealth.domain.emr.model.Emr;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Feb 4, 2006
 * Time: 4:58:08 PM
 * <p/>
 * Description:
 */
public class EmrDAOHibernate extends AbstractHibernateDAO<Emr, String> implements EmrDAO {
    private static final Logger log = LoggerFactory.getLogger(EmrDAOHibernate.class);

}
