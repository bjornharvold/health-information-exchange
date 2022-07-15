package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.platform.model.OrganizationUserRole;
import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 25, 2008
 * Time: 1:20:42 PM
 */
public interface OrganizationUserRoleDAO extends GenericDAO<OrganizationUserRole, String> {
    List<OrganizationUserRole> searchForOrganizationUserRoles(String organizationUserId, String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForOrganizationUserRolesCount(String organizationUserId, String name) throws PersistenceException;

    OrganizationUserRole getOrganizationUserRole(String organizationUserId, String roleId) throws PersistenceException;

    List<OrganizationUserRole> getOrganizationUserRolesByRoleId(String roleId) throws PersistenceException;
}