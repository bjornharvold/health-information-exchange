/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.Email;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;


/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:43:06 AM
 * Description:
 */
@Repository
public interface EmailDAO extends GenericDAO<Email, String> {

    Email newEmail();
    
}
