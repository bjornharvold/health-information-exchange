package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.ApplicationConfiguration;
import com.hxcel.globalhealth.platform.dao.ApplicationConfigurationDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.apache.commons.lang.StringUtils;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:08:38 PM
 */
public class ApplicationConfigurationDAOHibernate extends AbstractHibernateDAO<ApplicationConfiguration, String> implements ApplicationConfigurationDAO {

    public List<ApplicationConfiguration> searchForApplicationConfigurations(String applicationId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationConfiguration.class);

        c.add(Restrictions.eq("application.id", applicationId));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(Restrictions.ilike("key", name + "%"), Restrictions.ilike("value", name + "%")));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index*maxResults);
            c.setMaxResults(maxResults);
        }

        return c.list();
    }

    public Integer searchForApplicationsConfigurationsCount(String applicationId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationConfiguration.class);

        c.add(Restrictions.eq("application.id", applicationId));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(Restrictions.ilike("key", name + "%"), Restrictions.ilike("value", name + "%")));
        }
        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public ApplicationConfiguration getApplicationConfiguration(String id) throws PersistenceException {
        return get(ApplicationConfiguration.class, id);
    }
}