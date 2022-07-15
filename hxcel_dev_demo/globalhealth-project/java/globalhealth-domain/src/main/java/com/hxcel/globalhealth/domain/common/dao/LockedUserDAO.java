/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.LockedUser;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;


/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:50:40 AM

 */
@Repository
public interface LockedUserDAO extends GenericDAO<LockedUser, String> {
}
