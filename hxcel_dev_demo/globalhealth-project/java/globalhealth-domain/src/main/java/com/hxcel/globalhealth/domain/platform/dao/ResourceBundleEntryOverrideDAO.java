package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.ResourceBundleEntryOverride;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:20:42 PM
 */
public interface ResourceBundleEntryOverrideDAO extends GenericDAO<ResourceBundleEntryOverride, String> {
    List<ResourceBundleEntryOverride> searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(String organizationLicenseId, String name) throws PersistenceException;
    List<ResourceBundleEntryOverride> getResourceBundleEntryOverridesByResourceBundleEntryId(String resourceBundleEntryId) throws PersistenceException;
}