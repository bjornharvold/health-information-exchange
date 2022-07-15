/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.EmailDAO;
import com.hxcel.globalhealth.domain.common.model.Email;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: EmailDAOHibernate</p>
 * <p>Description: Handles emails for users.
 * </p>
 *
 * @author Bjorn Harvold
 */

public class EmailDAOHibernate extends AbstractHibernateDAO<Email, String> implements EmailDAO {
    private static final Logger log = LoggerFactory.getLogger(EmailDAOHibernate.class);

    public Email newEmail() {
        return new Email();
    }
}
