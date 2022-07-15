package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.ResourceBundleEntryOverride;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;

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