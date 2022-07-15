/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.ContactDAO;
import com.hxcel.globalhealth.domain.common.model.Contact;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: ContactDAOHibernate</p>
 * <p>Description: Handles all interaction with PHR contacts</p>
 * @author Bjorn Harvold
 */

public class ContactDAOHibernate extends AbstractHibernateDAO<Contact, String> implements ContactDAO {

    private static final Logger log = LoggerFactory.getLogger(ContactDAOHibernate.class);


}
