/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Application;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * User: bjorn
 * Date: Apr 29, 2008
 * Time: 4:07:03 PM
 */
public interface ApplicationDAO extends GenericDAO<Application, String> {
    List<Application> searchForApplications(String name, Boolean isPlatform, Integer index, Integer maxResult) throws PersistenceException;
    Integer searchForApplicationsCount(String name, Boolean isPlatform) throws PersistenceException;
    List<Application> getApplications(List<String> appIds, Integer index, Integer maxResult) throws PersistenceException;
    List<Application> getApplicationsByLicenseIds(List<String> licenseIds, Integer index, Integer maxResult) throws PersistenceException;
    List<Application> searchForApplicationsByOwner(String organizationId, String name, Boolean platform, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForApplicationsByOwnerCount(String organizationId, String name, Boolean platform) throws PersistenceException;
    Application getLaunchableApplication(String organizationId, String applicationId, Boolean platform) throws PersistenceException;
    List<Application> getLastModifiedApplications(Boolean platform, Integer maxResults) throws PersistenceException;
    Application getApplication(String id) throws PersistenceException;
}
