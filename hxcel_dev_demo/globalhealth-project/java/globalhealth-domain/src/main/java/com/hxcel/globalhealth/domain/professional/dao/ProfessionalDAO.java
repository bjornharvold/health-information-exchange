/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.professional.dao;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.professional.model.Professional;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:48:48 AM
 * Description:
 */
public interface ProfessionalDAO extends GenericDAO<Professional, String> {

    /**
     * This method returns a Professional with user, personal profile and user info initialized
     * @param userId
     * @return
     * @throws PersistenceException
     */
    Professional getProfessionalByUserId(String userId) throws PersistenceException;

    List<Professional> getProfessionalsWithNoPrivileges(String journalId, Country country) throws PersistenceException;

    public List<Professional> getProfessionalsByLocale(Country country) throws PersistenceException;
}
