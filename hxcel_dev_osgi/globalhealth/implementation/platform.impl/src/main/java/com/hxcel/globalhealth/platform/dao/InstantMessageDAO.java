/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.InstantMessage;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:43:06 AM
 * Description:
 */
@Repository
public interface InstantMessageDAO extends GenericDAO<InstantMessage, String> {

    InstantMessage newInstantMessage();
    
}
