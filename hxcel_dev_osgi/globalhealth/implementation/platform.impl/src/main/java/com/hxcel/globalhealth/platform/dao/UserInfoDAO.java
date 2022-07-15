/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.UserInfo;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Jul 12, 2006
 * Time: 7:41:37 PM
 */
@Repository
public interface UserInfoDAO extends GenericDAO<UserInfo, String> {
}
