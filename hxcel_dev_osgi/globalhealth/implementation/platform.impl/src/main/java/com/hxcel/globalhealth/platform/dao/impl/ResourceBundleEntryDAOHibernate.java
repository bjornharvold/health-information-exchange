package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.ResourceBundleEntry;
import com.hxcel.globalhealth.platform.dao.ResourceBundleEntryDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.apache.commons.lang.StringUtils;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:08:38 PM
 */
public class ResourceBundleEntryDAOHibernate extends AbstractHibernateDAO<ResourceBundleEntry, String> implements ResourceBundleEntryDAO {

    public List<ResourceBundleEntry> searchForResourceBundleEntries(String applicationId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(ResourceBundleEntry.class);

        c.add(Restrictions.eq("application.id", applicationId));
        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(
                    Restrictions.ilike("key", name + "%"),
                    Restrictions.ilike("value", name + "%")
                    ));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("key"));

        return c.list();
    }

    public Integer searchForResourceBundleEntriesCount(String applicationId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(ResourceBundleEntry.class);

        c.add(Restrictions.eq("application.id", applicationId));
        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(
                    Restrictions.ilike("key", name + "%"),
                    Restrictions.ilike("value", name + "%")
                    ));
        }

        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public ResourceBundleEntry getResourceBundleEntryByKey(String applicationId, String countryId, String key) throws PersistenceException {
        Criteria c = getSession().createCriteria(ResourceBundleEntry.class);

        c.add(Restrictions.eq("application.id", applicationId));
        c.add(Restrictions.eq("country.id", countryId));
        c.add(Restrictions.eq("key", key));


        return (ResourceBundleEntry) c.uniqueResult();
    }

    public ResourceBundleEntry getResourceBundleEntry(String resourceBundleEntryId) throws PersistenceException {
        return get(ResourceBundleEntry.class, resourceBundleEntryId);
    }
}