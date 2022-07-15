package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:20:42 PM
 */
public interface ApplicationConfigurationOverrideDAO extends GenericDAO<ApplicationConfigurationOverride, String> {
    List<ApplicationConfigurationOverride> searchForLicensedThirdPartyApplicationConfigurationOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForLicensedThirdPartyApplicationConfigurationOverridesCount(String organizationApplicationId, String name) throws PersistenceException;
    List<ApplicationConfigurationOverride> getApplicationConfigurationOverrideByApplicationConfigurationId(String applicationConfigurationId) throws PersistenceException;
}
