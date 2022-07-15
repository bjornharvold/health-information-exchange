/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.AuditTrailDAO;
import com.hxcel.globalhealth.domain.common.model.SystemAuditLogger;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Dec 4, 2005
 * Time: 10:20:24 PM

 * <p/>
 * Description: Handles persisting audit data to/from db
 */
public class AuditTrailDAOHibernate extends AbstractHibernateDAO<SystemAuditLogger, String> implements AuditTrailDAO {
    private static final Logger log = LoggerFactory.getLogger(AuditTrailDAOHibernate.class);


}
