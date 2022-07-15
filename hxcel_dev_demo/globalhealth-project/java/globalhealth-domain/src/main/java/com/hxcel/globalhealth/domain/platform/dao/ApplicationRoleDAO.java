/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.ApplicationRole;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * User: bjorn
 * Date: Apr 29, 2008
 * Time: 4:07:03 PM
 */
public interface ApplicationRoleDAO extends GenericDAO<ApplicationRole, String> {

    List<ApplicationRole> searchForApplicationRoles(String applicationId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForApplicationsRolesCount(String applicationId, String name) throws PersistenceException;

    ApplicationRole getApplicationRoleByApplicationIdAndRoleId(String applicationId, String roleId) throws PersistenceException;

    List<ApplicationRole> getApplicationRolesByRoleId(String roleId) throws PersistenceException;
}