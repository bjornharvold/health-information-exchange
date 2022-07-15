package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.dao.UserRoleDAO;
import com.hxcel.globalhealth.platform.model.UserRole;
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
public class UserRoleDAOHibernate extends AbstractHibernateDAO<UserRole, String> implements UserRoleDAO {
    public List<UserRole> searchForUserRoles(String id, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("user", "u");
        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("u.id", id));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("r.name", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("r.name"));

        return c.list();
    }

    public Integer searchForUserRolesCount(String id, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("user", "u");
        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("u.id", id));

        if (StringUtils.isNotBlank(name)) {
            c = c.add(Restrictions.ilike("r.name", name + "%"));
        }

        c = c.setProjection(Projections.count("id"));

        return (Integer) c.uniqueResult();
    }

    public UserRole getUserRoleByUserIdAndRoleId(String userId, String roleId) {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("user", "u");
        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("u.id", userId));
        c = c.add(Restrictions.eq("r.id", roleId));

        return (UserRole) c.uniqueResult();
    }

    public List<UserRole> getUserRolesByRoleId(String roleId) throws PersistenceException {
        Criteria c = getSession().createCriteria(UserRole.class);

        c = c.createAlias("role", "r");
        c = c.add(Restrictions.eq("r.id", roleId));

        return c.list();
    }

}