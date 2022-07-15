package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.domain.platform.model.OrganizationUser;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:20:42 PM
 */
public interface OrganizationUserDAO extends GenericDAO<OrganizationUser, String> {
    List<OrganizationUser> searchForOrganizationUsers(String organizationId, String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForOrganizationUsersCount(String organizationId, String name) throws PersistenceException;

    OrganizationUser getOrganizationUser(String organizationId, String userId) throws PersistenceException;

    List<String> getUserIdsForOrganizationMembers(String organizationId) throws PersistenceException;
}