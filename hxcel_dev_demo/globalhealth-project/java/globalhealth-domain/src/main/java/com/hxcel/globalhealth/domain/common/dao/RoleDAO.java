package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 24, 2008
 * Time: 6:27:29 PM
 */
public interface RoleDAO extends GenericDAO<Role, String> {
    Role getRoleByStatusCode(String statusCode) throws PersistenceException;

    List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForRolesCount(String name) throws PersistenceException;

    List<Role> getLastModifiedRoles(Integer maxResults) throws PersistenceException;
}
