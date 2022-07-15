package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.OrganizationUserRole;
import com.hxcel.globalhealth.platform.dao.OrganizationUserRoleDAO;

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
public class OrganizationUserRoleDAOHibernate extends AbstractHibernateDAO<OrganizationUserRole, String> implements OrganizationUserRoleDAO {

    /**
     * Retrieves a list of roles for a member of an organization
     * @param organizationUserId
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws com.hxcel.globalhealth.common.spec.PersistenceException
     */
    public List<OrganizationUserRole> searchForOrganizationUserRoles(String organizationUserId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUserRole.class);

        c = c.createAlias("role", "r");

        if (StringUtils.isNotBlank(organizationUserId)) {
            c = c.createAlias("user", "ou")
                    .add(Restrictions.eq("ou.id", organizationUserId));
        }
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

    /**
     * Retrieves a member count for an organization
     * @param organizationUserId
     * @param name
     * @return
     * @throws com.hxcel.globalhealth.common.spec.PersistenceException
     */
    public Integer searchForOrganizationUserRolesCount(String organizationUserId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUserRole.class);

        if (StringUtils.isNotBlank(organizationUserId)) {
            c = c.createAlias("user", "ou")
                    .add(Restrictions.eq("ou.id", organizationUserId));
        }

        if (StringUtils.isNotBlank(name)) {
            c = c.createAlias("role", "r")
                    .add(Restrictions.ilike("r.name", name + "%"));
        }

        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    public OrganizationUserRole getOrganizationUserRole(String organizationUserId, String roleId) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUserRole.class);

        if (StringUtils.isNotBlank(organizationUserId)) {
            c = c.createAlias("user", "ou")
                    .add(Restrictions.eq("ou.id", organizationUserId));
        }
        if (StringUtils.isNotBlank(roleId)) {
            c = c.createAlias("role", "r")
                    .add(Restrictions.eq("r.id", roleId));
        }

        return (OrganizationUserRole) c.uniqueResult();
    }

    public List<OrganizationUserRole> getOrganizationUserRolesByRoleId(String roleId) throws PersistenceException {
        Criteria c = getSession().createCriteria(OrganizationUserRole.class);

        c = c.createAlias("role", "r");

        if (StringUtils.isNotBlank(roleId)) {
            c.add(Restrictions.eq("r.id", roleId));
        }

        return c.list();
    }
}