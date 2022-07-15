package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.RegulationOverride;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 8, 2008
 * Time: 6:38:06 PM
 */
public interface RegulationOverrideDAO extends GenericDAO<RegulationOverride, String> {
    List<RegulationOverride> searchForRegulationOverridesByCountry(String countryId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForRegulationOverridesByCountryCount(String countryId, String name) throws PersistenceException;
    List<RegulationOverride> getOverridesByRegulationId(String regulationId);
}