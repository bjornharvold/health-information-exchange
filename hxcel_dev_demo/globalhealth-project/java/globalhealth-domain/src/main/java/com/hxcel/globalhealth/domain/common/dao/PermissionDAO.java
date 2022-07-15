/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.Permission;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:45:10 AM
 */
@Repository
public interface PermissionDAO extends GenericDAO<Permission, String> {

}
