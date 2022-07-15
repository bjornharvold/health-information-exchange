package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Role;
import com.hxcel.globalhealth.platform.dao.RoleDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 24, 2008
 * Time: 6:28:14 PM
 */
public class RoleDAOHibernate extends AbstractHibernateDAO<Role, String> implements RoleDAO {
    public Role getRoleByStatusCode(String statusCode) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);
        c.add(Restrictions.eq("statusCode", statusCode));

        return (Role) c.uniqueResult();
    }

    public List<Role> searchForRoles(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);

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

    public Integer searchForRolesCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("name", name + "%"));
        }
        c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public List<Role> getLastModifiedRoles(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Role.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }
}
