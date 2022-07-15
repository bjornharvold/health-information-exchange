package com.hxcel.globalhealth.domain.platform.dao.impl;

import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.domain.platform.model.OrganizationUser;
import com.hxcel.globalhealth.domain.platform.dao.ApplicationConfigurationOverrideDAO;

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
public class ApplicationConfigurationOverrideDAOHibernate extends AbstractHibernateDAO<ApplicationConfigurationOverride, String> implements ApplicationConfigurationOverrideDAO {

    public List<ApplicationConfigurationOverride> searchForLicensedThirdPartyApplicationConfigurationOverrides(String organizationLicenseId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationConfigurationOverride.class);

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

    public Integer searchForLicensedThirdPartyApplicationConfigurationOverridesCount(String organizationLicenseId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationConfigurationOverride.class);

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

    public List<ApplicationConfigurationOverride> getApplicationConfigurationOverrideByApplicationConfigurationId(String applicationConfigurationId) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationConfigurationOverride.class);

        c = c.add(Restrictions.eq("original.id", applicationConfigurationId));

        return c.list();
    }
}