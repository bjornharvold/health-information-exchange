/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.common.dao;

import com.hxcel.globalhealth.common.model.Permission;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:45:10 AM
 */
@Repository
public interface PermissionDAO extends GenericDAO<Permission, String> {

}
