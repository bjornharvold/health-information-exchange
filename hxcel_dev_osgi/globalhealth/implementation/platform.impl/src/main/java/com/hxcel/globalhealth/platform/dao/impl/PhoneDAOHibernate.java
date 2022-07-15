/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.platform.dao.PhoneDAO;
import com.hxcel.globalhealth.platform.model.Phone;
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

public class PhoneDAOHibernate extends AbstractHibernateDAO<Phone, String> implements PhoneDAO {
    private static final Logger log = LoggerFactory.getLogger(PhoneDAOHibernate.class);

    public Phone newPhone() {
        return new Phone();
    }
}
