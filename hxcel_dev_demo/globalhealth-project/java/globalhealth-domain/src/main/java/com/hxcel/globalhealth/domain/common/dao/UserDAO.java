/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:50:40 AM
 */
@Repository
public interface UserDAO extends GenericDAO<User, String> {

    User getUser(String username, String password) throws PersistenceException;

    void deactivateUser(String userId) throws PersistenceException;

    void activateUser(String userId) throws PersistenceException;

    User isUserUnique(String userId, String username) throws PersistenceException;

    User getUserByUsername(String username) throws PersistenceException;

    User getUserBySecurityCode(String securityCode) throws PersistenceException;

    List<User> getUsers(String name, Integer index, Integer maxResults) throws PersistenceException;

    List<User> getUsers(List<String> userIds, Integer index, Integer maxResults) throws PersistenceException;

    Integer getUserCount(String name) throws PersistenceException;

    List<User> getLastModifiedUsers(Integer maxResults) throws PersistenceException;

    List<User> searchForUsersWithExcludes(List<String> excludedUserIds, String name, Integer index, Integer maxResults) throws PersistenceException;

    Integer searchForUsersWithExcludesCount(List<String> excludedUserIds, String name) throws PersistenceException;
}
