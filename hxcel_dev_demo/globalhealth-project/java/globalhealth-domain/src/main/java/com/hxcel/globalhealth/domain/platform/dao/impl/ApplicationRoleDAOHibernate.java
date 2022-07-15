/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.platform.dao.impl;

import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.ApplicationRole;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.enums.ApplicationStatusCd;
import com.hxcel.globalhealth.domain.platform.dao.ApplicationDAO;
import com.hxcel.globalhealth.domain.platform.dao.ApplicationRoleDAO;

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
public class ApplicationRoleDAOHibernate extends AbstractHibernateDAO<ApplicationRole, String> implements ApplicationRoleDAO {

    public List<ApplicationRole> searchForApplicationRoles(String applicationId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationRole.class);

        c.add(Restrictions.eq("application.id", applicationId));

        if (StringUtils.isNotBlank(name)) {
            c = c.createAlias("role", "r")
                    .add(Restrictions.ilike("r.name", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        return c.list();
    }

    public Integer searchForApplicationsRolesCount(String applicationId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationRole.class);

        c.add(Restrictions.eq("application.id", applicationId));

        if (StringUtils.isNotBlank(name)) {
            c = c.createAlias("role", "r")
                    .add(Restrictions.ilike("r.name", name + "%"));
        }
        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public ApplicationRole getApplicationRoleByApplicationIdAndRoleId(String applicationId, String roleId) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationRole.class);

        c.add(Restrictions.eq("application.id", applicationId));
        c.createAlias("role", "r")
                .add(Restrictions.eq("r.id", roleId));

        return (ApplicationRole) c.uniqueResult();
    }

    public List<ApplicationRole> getApplicationRolesByRoleId(String roleId) throws PersistenceException {
        Criteria c = getSession().createCriteria(ApplicationRole.class);

        c.createAlias("role", "r")
                .add(Restrictions.eq("r.id", roleId));

        return c.list();
    }
}