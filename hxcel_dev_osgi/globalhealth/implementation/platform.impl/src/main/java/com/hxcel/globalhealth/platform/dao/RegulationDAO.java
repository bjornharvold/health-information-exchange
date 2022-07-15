package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.Regulation;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 8, 2008
 * Time: 6:38:06 PM
 */
public interface RegulationDAO extends GenericDAO<Regulation, String> {
    List<Regulation> searchForRegulations(String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForRegulationsCount(String name) throws PersistenceException;

    Regulation getRegulation(String id) throws PersistenceException;

    List<Regulation> getLastModifiedRegulations(Integer maxResults) throws PersistenceException;
}
