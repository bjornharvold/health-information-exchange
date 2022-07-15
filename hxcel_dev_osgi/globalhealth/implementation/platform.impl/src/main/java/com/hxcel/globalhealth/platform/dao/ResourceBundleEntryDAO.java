package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.platform.model.ResourceBundleEntry;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 8, 2008
 * Time: 6:38:06 PM
 */
public interface ResourceBundleEntryDAO extends GenericDAO<ResourceBundleEntry, String> {
    List<ResourceBundleEntry> searchForResourceBundleEntries(String applicationId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForResourceBundleEntriesCount(String applicationId, String name) throws PersistenceException;
    ResourceBundleEntry getResourceBundleEntryByKey(String applicationId, String countryId, String key) throws PersistenceException;
    ResourceBundleEntry getResourceBundleEntry(String resourceBundleEntryId) throws PersistenceException;
}