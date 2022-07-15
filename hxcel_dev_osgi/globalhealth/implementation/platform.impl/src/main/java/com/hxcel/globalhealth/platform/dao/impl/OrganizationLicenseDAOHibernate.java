package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.platform.spec.model.enums.LicenseStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;
import com.hxcel.globalhealth.platform.dao.OrganizationLicenseDAO;

import java.util.List;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.apache.commons.lang.StringUtils;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:08:38 PM
 */
public class OrganizationLicenseDAOHibernate extends AbstractHibernateDAO<OrganizationLicense, String> implements OrganizationLicenseDAO {

    public List<OrganizationLicense> searchForOrganizationLicenses(String organizationId, String name, Boolean platform, OrganizationLicenseStatusCd status, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationLicense.class);

        if (StringUtils.isNotBlank(organizationId)) {
            c = c.createAlias("organization", "org")
                    .add(Restrictions.eq("org.id", organizationId));
        }

        if (status != null) {
            c.add(Restrictions.eq("status", status));
        }

        if (StringUtils.isNotBlank(name) || platform != null) {
            c = c.createAlias("license", "lic")
                    .createAlias("lic.application", "app");

            if (StringUtils.isNotBlank(name)) {
                c.add(Restrictions.ilike("app.name", name + "%"));
            }

            if (platform != null) {
                c.add(Restrictions.eq("app.platform", platform));
            }
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("app.name"));
        c.addOrder(Order.asc("app.applicationStatus"));
        c.addOrder(Order.asc("app.applicationType"));

        return c.list();
    }

    public Integer searchForOrganizationLicensesCount(String organizationId, String name, Boolean platform, OrganizationLicenseStatusCd status) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationLicense.class);

        if (StringUtils.isNotBlank(organizationId)) {
            c = c.createAlias("organization", "org")
                    .add(Restrictions.eq("org.id", organizationId));
        }

        if (status != null) {
            c.add(Restrictions.eq("status", status));
        }

        if (StringUtils.isNotBlank(name) || platform != null) {
            c = c.createAlias("license", "lic")
                    .createAlias("lic.application", "app");

            if (StringUtils.isNotBlank(name)) {
                c.add(Restrictions.ilike("app.name", name + "%"));
            }

            if (platform != null) {
                c.add(Restrictions.eq("app.platform", platform));
            }
        }

        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    /**
     * Retrieves a OrganizationLicense entity based on an organizationId and a licenseId
     *
     * @param organizationId
     * @param licenseId
     * @return
     * @throws PersistenceException
     */
    public OrganizationLicense getOrganizationLicense(String organizationId, String licenseId) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationLicense.class);

        if (StringUtils.isNotBlank(organizationId)) {
            c = c.createAlias("organization", "org")
                    .add(Restrictions.eq("org.id", organizationId));
        }
        if (StringUtils.isNotBlank(licenseId)) {
            c = c.createAlias("license", "lic")
                    .add(Restrictions.eq("lic.id", licenseId));
        }

        return (OrganizationLicense) c.uniqueResult();
    }

    public OrganizationLicense getOrganizationLicense(String id) throws PersistenceException {
        return get(OrganizationLicense.class, id);
    }

    public Integer getApplicationUsageCount(String applicationId) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationLicense.class);

        c.setProjection(Projections.count("id"))
                .createAlias("license", "lic")
                .createAlias("lic.application", "app")
                .add(Restrictions.eq("app.id", applicationId));

        return (Integer) c.uniqueResult();
    }

    public OrganizationLicense getLaunchableApplication(String organizationId, String applicationId, Boolean platform) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationLicense.class);

        // needs to match the licensee
        c = c.createAlias("organization", "org")
                .add(Restrictions.eq("org.id", organizationId));

        // the license needs to be active and not have expired
        c = c.createAlias("license", "lic")
                .add(Restrictions.eq("lic.status", LicenseStatusCd.ACTIVE))
                .add(Restrictions.gt("lic.expirationDate", new Date()));

        // needs to match application and app needs to be active
        c = c.createAlias("lic.application", "app")
                .add(Restrictions.eq("app.id", applicationId))
                .add(Restrictions.eq("app.applicationStatus", ApplicationStatusCd.PUBLISHED));

        // and if the type is a platform it will add the restrictions below as well
        if (platform != null) {
            c.add(Restrictions.eq("app.platform", platform));
            c.add(Restrictions.eq("app.applicationType", ApplicationTypeCd.PLATFORM));
        }

        return (OrganizationLicense) c.uniqueResult();
    }
    
}