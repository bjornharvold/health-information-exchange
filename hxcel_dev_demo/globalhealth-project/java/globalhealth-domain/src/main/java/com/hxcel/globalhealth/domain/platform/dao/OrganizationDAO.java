/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.utils.string.KeyValuePair;

import java.util.List;

/**
 * User: bjorn
 * Date: Apr 29, 2008
 * Time: 4:07:03 PM
 */
public interface OrganizationDAO extends GenericDAO<Organization, String> {
    Organization getHXCELOrganization() throws PersistenceException;
    List<Organization> getOrganizationByType(OrganizationTypeCd type) throws PersistenceException;
    Organization getOrganizationByName(String name) throws PersistenceException;
    List<Organization> searchForOrganizations(String name, Integer index, Integer maxResult) throws PersistenceException;
    List<KeyValuePair> getOrganizationThinList() throws PersistenceException;
    Organization getOrganization(String id) throws PersistenceException;
    Integer searchForOrganizationsCount(String name) throws PersistenceException;
    List<Organization> getLastModifiedOrganizations(Integer maxResults) throws PersistenceException;
}