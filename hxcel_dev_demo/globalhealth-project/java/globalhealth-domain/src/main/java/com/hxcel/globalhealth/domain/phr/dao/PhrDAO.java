/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dao;

import com.hxcel.globalhealth.domain.phr.model.Phr;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import org.springframework.stereotype.Repository;

/**
 * User: bjorn
 * Date: Nov 11, 2007
 * Time: 7:31:36 PM
 */
@Repository
public interface PhrDAO extends GenericDAO<Phr, String> {
}
