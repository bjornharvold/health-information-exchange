/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.Creditcard;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:30:00 AM
 * Description:
 */
@Repository
public interface CreditcardDAO extends GenericDAO<Creditcard, String> {

    List<Creditcard> getPayments(String userId) throws PersistenceException;

}
