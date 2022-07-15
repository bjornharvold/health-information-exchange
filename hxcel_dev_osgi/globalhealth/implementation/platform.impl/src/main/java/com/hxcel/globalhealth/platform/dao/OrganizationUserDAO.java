package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.OrganizationUser;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;

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