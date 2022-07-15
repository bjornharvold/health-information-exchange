package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 24, 2008
 * Time: 6:27:29 PM
 */
public interface UserRoleDAO extends GenericDAO<UserRole, String> {
    List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForUserRolesCount(String id, String name) throws PersistenceException;

    UserRole getUserRoleByUserIdAndRoleId(String userId, String roleId) throws PersistenceException;

    List<UserRole> getUserRolesByRoleId(String roleId) throws PersistenceException;
}