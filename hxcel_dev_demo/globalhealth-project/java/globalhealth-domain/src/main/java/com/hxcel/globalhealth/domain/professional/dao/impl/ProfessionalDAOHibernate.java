/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.professional.dao.impl;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.professional.dao.ProfessionalDAO;
import com.hxcel.globalhealth.domain.professional.model.Professional;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import java.util.List;

/**
 * <p>Title: ProfessionalDAOHibernate</p>
 * <p>Description: Handles all operations related to handling professionals</p>


 *
 * @author Bjorn Harvold

 */

public class ProfessionalDAOHibernate extends AbstractHibernateDAO<Professional, String> implements ProfessionalDAO {
    private static final Logger log = LoggerFactory.getLogger(ProfessionalDAOHibernate.class);

    /**
     * Provide a user id from the user table and it will return a professional_tbl Value Object for you
     *
     * @param userId String
     * @return Professional
     */
    public Professional getProfessionalByUserId(String userId) throws PersistenceException {

        String[] params = {"userId"};
        Object[] values = {userId};
        Type[] types = {Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("professional_get_professional_by_user_id", params, values, types);

    }


    /**
     * This doesn't go here
     * @param journalId
     * @param country
     * @return list
     */
    public List<Professional> getProfessionalsWithNoPrivileges(String journalId, Country country) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Professional> getProfessionalsByLocale(Country country) throws PersistenceException {
            if (country == null) {
                log.error("country cannot be null");
                throw new PersistenceException("error.missing.argument.exception", "country is null");
            }
            
            return findByNamedQueryAndNamedParam("getProfessionalsByCountry", "countryCdId", country.getId());
        }


}
