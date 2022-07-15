package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.ResourceBundleEntryOverride;
import com.hxcel.globalhealth.platform.dao.ResourceBundleEntryOverrideDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Disjunction;
import org.apache.commons.lang.StringUtils;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:08:38 PM
 */
public class ResourceBundleEntryOverrideDAOHibernate extends AbstractHibernateDAO<ResourceBundleEntryOverride, String> implements ResourceBundleEntryOverrideDAO {

    public List<ResourceBundleEntryOverride> searchForLicensedThirdPartyApplicationResourceBundleEntryOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(ResourceBundleEntryOverride.class);

        if (StringUtils.isNotBlank(organizationLicenseId)) {
            c = c.createAlias("organizationLicense", "org")
                    .add(Restrictions.eq("org.id", organizationLicenseId));
        }
        c = c.createAlias("original", "original");

        if (StringUtils.isNotBlank(name)) {
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("original.key", name + "%"));
            d.add(Restrictions.ilike("original.value", name + "%"));
            d.add(Restrictions.ilike("value", name + "%"));
            c.add(d);
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("original.key"));

        return c.list();
    }

    public Integer searchForLicensedThirdPartyApplicationResourceBundleEntryOverridesCount(String organizationLicenseId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(ResourceBundleEntryOverride.class);

        if (StringUtils.isNotBlank(organizationLicenseId)) {
            c = c.createAlias("organizationLicense", "org")
                    .add(Restrictions.eq("org.id", organizationLicenseId));
        }
        if (StringUtils.isNotBlank(name)) {
            c = c.createAlias("original", "original");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("original.key", name + "%"));
            d.add(Restrictions.ilike("original.value", name + "%"));
            d.add(Restrictions.ilike("value", name + "%"));
            c.add(d);
        }

        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    public List<ResourceBundleEntryOverride> getResourceBundleEntryOverridesByResourceBundleEntryId(String resourceBundleEntryId) throws PersistenceException {
        Criteria c = getSession().createCriteria(ResourceBundleEntryOverride.class);

        c = c.add(Restrictions.eq("original.id", resourceBundleEntryId));

        return c.list();
    }
}