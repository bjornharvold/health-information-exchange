/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Application;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.platform.spec.model.enums.ApplicationTypeCd;
import com.hxcel.globalhealth.platform.dao.ApplicationDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.apache.commons.lang.StringUtils;

/**
 * User: bjorn
 * Date: Apr 29, 2008
 * Time: 4:06:08 PM
 */
@SuppressWarnings("unchecked")
public class ApplicationDAOHibernate extends AbstractHibernateDAO<Application, String> implements ApplicationDAO {

    /**
     * @param name
     * @param isPlatform defines whether this application is a platform application
     * @param index
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
    public List<Application> searchForApplications(String name, Boolean isPlatform, Integer index, Integer maxResult) throws PersistenceException {
        Criteria c = getSession().createCriteria(Application.class);

        if (isPlatform != null) {
            c.add(Restrictions.eq("platform", isPlatform));
        }

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }

        if (index != null && maxResult != null) {
            c.setFirstResult(index * maxResult);
            c.setMaxResults(maxResult);
        }

        c.addOrder(Order.asc("name"));

        return c.list();
    }

    /**
     * @param name
     * @param isPlatform defines whether this application is a platform application
     * @return
     * @throws PersistenceException
     */
    public Integer searchForApplicationsCount(String name, Boolean isPlatform) throws PersistenceException {
        Criteria c = getSession().createCriteria(Application.class);

        if (isPlatform != null) {
            c.add(Restrictions.eq("platform", isPlatform));
        }

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }

        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    /**
     * Returns a list of apps based on their ids
     *
     * @param appIds
     * @param index
     * @param maxResult
     * @return
     * @throws PersistenceException
     */
    public List<Application> getApplications(List<String> appIds, Integer index, Integer maxResult) throws PersistenceException {
        List<Application> result = null;

        if (appIds != null && appIds.size() > 0) {
            Criteria c = getSession().createCriteria(Application.class);

            c.add(Restrictions.in("id", appIds));

            if (index != null && maxResult != null) {
                c.setFirstResult(index * maxResult);
                c.setMaxResults(maxResult);
            }

            result = c.list();
        }

        return result;
    }

    public List<Application> getApplicationsByLicenseIds(List<String> licenseIds, Integer index, Integer maxResult) throws PersistenceException {
        List<Application> result = null;

        if (licenseIds != null && licenseIds.size() > 0) {
            Criteria c = getSession().createCriteria(Application.class);
            c.createAlias("licenses", "l");
            c.add(Restrictions.in("l.id", licenseIds));

            if (index != null && maxResult != null) {
                c.setFirstResult(index * maxResult);
                c.setMaxResults(maxResult);
            }

            result = c.list();
        }

        return result;
    }

    /**
     * Grab all applications by owner.
     *
     * @param organizationId
     * @param name
     * @param platform
     * @param index
     * @param maxResults     @return
     * @throws PersistenceException
     */
    public List<Application> searchForApplicationsByOwner(String organizationId, String name, Boolean platform, Integer index, Integer maxResults) throws PersistenceException {
        List<Application> apps = null;

        // grab all applications by owner id
        Criteria c = getSession().createCriteria(Application.class);
        c.add(Restrictions.eq("owner.id", organizationId));

        if (platform != null) {
            c.add(Restrictions.eq("platform", platform));
        }

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("name"));
        c.addOrder(Order.asc("applicationStatus"));
        c.addOrder(Order.asc("applicationType"));
        return c.list();
    }

    public Integer searchForApplicationsByOwnerCount(String organizationId, String name, Boolean platform) throws PersistenceException {
        Criteria c = getSession().createCriteria(Application.class);

        c.add(Restrictions.eq("owner.id", organizationId));
        if (platform != null) {
            c.add(Restrictions.eq("platform", platform));
        }

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }

        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public Application getLaunchableApplication(String organizationId, String applicationId, Boolean platform) throws PersistenceException {
        Criteria c = getSession().createCriteria(Application.class);

        c.add(Restrictions.eq("owner.id", organizationId));
        c.add(Restrictions.eq("id", applicationId));
        c.add(Restrictions.eq("applicationStatus", ApplicationStatusCd.PUBLISHED));

        if (platform != null) {
            c.add(Restrictions.eq("platform", platform));
            c.add(Restrictions.eq("applicationType", ApplicationTypeCd.PLATFORM));
        }

        return (Application) c.uniqueResult();
    }

    public List<Application> getLastModifiedApplications(Boolean platform, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Application.class);

        if (platform != null) {
            c.add(Restrictions.eq("platform", platform));
        }

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));
        
        return c.list();
    }

    public Application getApplication(String id) throws PersistenceException {
        return get(Application.class, id);
    }
}
