/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Organization;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationTypeCd;
import com.hxcel.globalhealth.platform.dao.OrganizationDAO;
import com.hxcel.globalhealth.platform.utils.KeyValuePair;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: bjorn
 * Date: Apr 29, 2008
 * Time: 4:06:08 PM
 */
@SuppressWarnings("unchecked")
public class OrganizationDAOHibernate extends AbstractHibernateDAO<Organization, String> implements OrganizationDAO {
    public Organization getOrganizationByName(String name) throws PersistenceException {
        return (Organization) getSession().createCriteria(Organization.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    public List<Organization> searchForOrganizations(String name, Integer index, Integer maxResult) throws PersistenceException {
        Criteria c = getSession().createCriteria(Organization.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name+"%"));
        }

        if (index != null && maxResult != null) {
            c.setFirstResult(index*maxResult);
            c.setMaxResults(maxResult);
        }

        c.addOrder(Order.asc("name"));
        return c.list();
    }

    public List<Organization> getOrganizationByType(OrganizationTypeCd type) throws PersistenceException {
        return getSession().createCriteria(Organization.class)
                .add(Restrictions.eq("organizationType", type))
                .list();
    }

    public Organization getHXCELOrganization() throws PersistenceException {
        return (Organization) getSession().createCriteria(Organization.class)
                .add(Restrictions.eq("organizationType", OrganizationTypeCd.HXCEL))
                .uniqueResult();
    }

    public List<KeyValuePair> getOrganizationThinList() throws PersistenceException {
        Query q = getSession().createQuery("select new com.hxcel.globalhealth.platform.utils.KeyValuePair(o.id, o.name) from Organization as o");

        return q.list();
    }

    public Organization getOrganization(String id) throws PersistenceException {
        Criteria c = getSession().createCriteria(Organization.class);
        c.add(Restrictions.eq("id", id));

        return (Organization) c.uniqueResult();
    }

    public Integer searchForOrganizationsCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Organization.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }
        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public List<Organization> getLastModifiedOrganizations(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Organization.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }
}