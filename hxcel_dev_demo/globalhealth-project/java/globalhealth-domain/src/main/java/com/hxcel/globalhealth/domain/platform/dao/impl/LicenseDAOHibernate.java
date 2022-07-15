package com.hxcel.globalhealth.domain.platform.dao.impl;

import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.dao.LicenseDAO;

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
public class LicenseDAOHibernate extends AbstractHibernateDAO<License, String> implements LicenseDAO {

    public List<License> searchForApplicationLicenses(String applicationId, String name, LicenseStatusCd status, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(License.class);

        c.add(Restrictions.eq("application.id", applicationId));
        c.add(Restrictions.eq("status", status));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("name"));

        return c.list();
    }

    public Integer searchForApplicationsLicensesCount(String applicationId, String name, LicenseStatusCd status) throws PersistenceException {
        Criteria c = getSession().createCriteria(License.class);

        c.add(Restrictions.eq("application.id", applicationId));
        c.add(Restrictions.eq("status", status));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }
        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    /**
     * Returns a list of applications that has licenses and that haven't been created by the
     * organization specified by id
     *
     * @param organizationId
     * @param platform
     * @return
     */
    public List<License> getLicensedApplicationsExcludeOwner(String organizationId, Boolean platform) throws PersistenceException {
        // grab all applications by owner id
        Criteria c = getSession().createCriteria(License.class);
        c.createAlias("application", "app")
                .add(Restrictions.ne("app.owner.id", organizationId))
                .add(Restrictions.eq("app.applicationStatus", ApplicationStatusCd.PUBLISHED))
                .add(Restrictions.eq("app.platform", platform));

        c.addOrder(Order.asc("app.name"));
        return c.list();
    }
}
