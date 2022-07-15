package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfiguration;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:07:45 PM
 */
public interface ApplicationConfigurationDAO extends GenericDAO<ApplicationConfiguration, String> {
    List<ApplicationConfiguration> searchForApplicationConfigurations(String applicationId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForApplicationsConfigurationsCount(String applicationId, String name) throws PersistenceException;
    ApplicationConfiguration getApplicationConfiguration(String id) throws PersistenceException;
}