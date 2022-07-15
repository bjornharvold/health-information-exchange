/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.LockedUserDAO;
import com.hxcel.globalhealth.domain.common.model.LockedUser;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockedUserDAOHibernate extends AbstractHibernateDAO<LockedUser, String> implements LockedUserDAO {
    private static final Logger log = LoggerFactory.getLogger(LockedUserDAOHibernate.class);


}
