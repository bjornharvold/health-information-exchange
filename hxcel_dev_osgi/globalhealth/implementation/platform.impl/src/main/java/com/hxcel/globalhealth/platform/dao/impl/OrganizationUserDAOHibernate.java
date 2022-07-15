package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.OrganizationUser;
import com.hxcel.globalhealth.platform.dao.OrganizationUserDAO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.Criteria;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:08:38 PM
 */
public class OrganizationUserDAOHibernate extends AbstractHibernateDAO<OrganizationUser, String> implements OrganizationUserDAO {

    /**
     * Retrieves a list of members of an organization
     * @param organizationId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws PersistenceException
     */
    public List<OrganizationUser> searchForOrganizationUsers(String organizationId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUser.class);

        c = c.createAlias("user", "u")
                .createAlias("u.userInfo", "ui");

        if (StringUtils.isNotBlank(organizationId)) {
            c = c.createAlias("organization", "org")
                    .add(Restrictions.eq("org.id", organizationId));
        }
        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(Restrictions.ilike("ui.firstName", name + "%"), Restrictions.ilike("ui.lastName", name + "%")));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("ui.lastName"));

        return c.list();
    }

    /**
     * Retrieves a member count for an organization
     * @param organizationId
     * @param name
     * @return
     * @throws PersistenceException
     */
    public Integer searchForOrganizationUsersCount(String organizationId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUser.class);

        if (StringUtils.isNotBlank(organizationId)) {
            c = c.createAlias("organization", "org")
                    .add(Restrictions.eq("org.id", organizationId));
        }

        if (StringUtils.isNotBlank(name)) {
            c = c.createAlias("user", "u")
                    .createAlias("u.userInfo", "ui")
                    .add(Restrictions.or(Restrictions.ilike("ui.firstName", name + "%"), Restrictions.ilike("ui.lastName", name + "%")));
        }
        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    public OrganizationUser getOrganizationUser(String organizationId, String userId) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUser.class);

        if (StringUtils.isNotBlank(organizationId)) {
            c = c.createAlias("organization", "org")
                    .add(Restrictions.eq("org.id", organizationId));
        }
        if (StringUtils.isNotBlank(userId)) {
            c = c.createAlias("user", "u")
                    .add(Restrictions.eq("u.id", userId));
        }

        return (OrganizationUser) c.uniqueResult();
    }

    public List<String> getUserIdsForOrganizationMembers(String organizationId) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUser.class);

        c = c.createAlias("organization", "org");
        c = c.createAlias("user", "u");

        if (StringUtils.isNotBlank(organizationId)) {
            c.add(Restrictions.eq("org.id", organizationId));
        }

        c.setProjection(Projections.property("u.id"));

        return c.list();
    }
}