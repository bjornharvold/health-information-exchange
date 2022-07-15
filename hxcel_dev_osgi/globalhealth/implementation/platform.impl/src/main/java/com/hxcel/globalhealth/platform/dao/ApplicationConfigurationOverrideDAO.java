package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;

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
