/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.platform.dao.InstantMessageDAO;
import com.hxcel.globalhealth.platform.model.InstantMessage;
import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: EmailDAOHibernate</p>
 * <p>Description: Handles emails for users.
 * </p>
 *
 * @author Bjorn Harvold
 */

public class InstantMessageDAOHibernate extends AbstractHibernateDAO<InstantMessage, String> implements InstantMessageDAO {
    private static final Logger log = LoggerFactory.getLogger(InstantMessageDAOHibernate.class);

    public InstantMessage newInstantMessage() {
        return new InstantMessage();
    }
}
