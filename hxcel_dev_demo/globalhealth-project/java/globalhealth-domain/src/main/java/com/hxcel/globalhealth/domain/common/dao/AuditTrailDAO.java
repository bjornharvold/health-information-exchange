/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.SystemAuditLogger;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Dec 4, 2005
 * Time: 10:36:42 PM

 * <p/>
 * Description:
 */
@Repository
public interface AuditTrailDAO extends GenericDAO<SystemAuditLogger, String> {

}
