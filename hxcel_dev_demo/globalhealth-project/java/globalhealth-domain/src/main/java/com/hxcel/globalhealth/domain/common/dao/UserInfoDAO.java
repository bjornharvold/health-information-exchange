/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.UserInfo;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Jul 12, 2006
 * Time: 7:41:37 PM
 */
@Repository
public interface UserInfoDAO extends GenericDAO<UserInfo, String> {
}
