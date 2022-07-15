/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.platform.dao.CreditcardDAO;
import com.hxcel.globalhealth.platform.model.Creditcard;
import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 28, 2005
 * Time: 11:14:54 AM

 * <p/>
 * Description:
 */
public class CreditcardDAOHibernate extends AbstractHibernateDAO<Creditcard, String> implements CreditcardDAO {

    /**
     * Returns all payments for userId
     *
     * @param userId String
     * @return List<Payment>
     */
    public List<Creditcard> getPayments(String userId) throws PersistenceException {

        if (StringUtils.isBlank(userId)) {
            throw new PersistenceException("userId cannot be null");
        }
        String[] params = {"userId"};
        Object[] values = {userId};

        return findByNamedQueryAndNamedParam("creditcard_get_creditcards_by_user_id", params, values);

    }
}
