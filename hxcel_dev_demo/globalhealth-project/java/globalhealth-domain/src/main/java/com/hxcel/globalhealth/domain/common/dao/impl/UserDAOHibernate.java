/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.UserDAO;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.enums.UserStatusCd;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.Regulation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Hibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.type.Type;

import java.util.List;

public class UserDAOHibernate extends AbstractHibernateDAO<User, String> implements UserDAO {
    private static final Logger log = LoggerFactory.getLogger(UserDAOHibernate.class);

    /**
     * Returns a user and all required objects based on a username and password
     *
     * @param username
     * @param password
     * @return
     * @throws com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException
     *
     */
    public User getUser(String username, String password) throws PersistenceException {
        if (StringUtils.isBlank(username)) {
            throw new PersistenceException("error.missing.argument.exception", "username cannot be null");
        }
        if (StringUtils.isBlank(password)) {
            throw new PersistenceException("error.missing.argument.exception", "password cannot be null");
        }

        String[] params = {"username", "password"};
        Object[] values = {username, password};
        Type[] types = {Hibernate.STRING, Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("user_authenticate_user", params, values, types);
    }


    /**
     * Changes the user's status to inactive.
     * Depending on whether the user was added by a professional or by user herself,
     * the status codes are different.
     *
     * @param userId String
     * @throws PersistenceException
     */
    public void deactivateUser(final String userId) throws PersistenceException {
        if (userId == null) {
            throw new PersistenceException("error.missing.argument.exception", "userId cannot be null");
        }

        User u = load(User.class, userId);
        if (u.getUserStatus() == UserStatusCd.ACTIVE) {
            u.setUserStatus(UserStatusCd.INACTIVE);
        }
        update(u);
    }

    /**
     * Changes the user's status to active.
     * Depending on whether the user was added by a professional or by user herself,
     * the status codes are different.
     *
     * @param userId String
     * @throws PersistenceException
     */
    public void activateUser(final String userId) throws PersistenceException {
        if (userId == null) {
            throw new PersistenceException("error.missing.argument.exception", "country cannot be null");
        }

        User u = load(User.class, userId);
        if (u.getUserStatus() == UserStatusCd.INACTIVE) {
            u.setUserStatus(UserStatusCd.ACTIVE);
        }

        update(u);
    }

    /**
     * checks the db and the user_tbl that the unique id and the username coming in are truly unique
     * If a user is returned it means this user is not unique
     *
     * @param userId
     * @param username String
     * @return boolean
     * @throws PersistenceException
     */
    public User isUserUnique(String userId, String username) throws PersistenceException {
        if (userId == null) {
            throw new PersistenceException("error.missing.argument.exception", "userId cannot be null");
        }
        if (StringUtils.isBlank(username)) {
            throw new PersistenceException("error.missing.argument.exception", "username cannot be null");
        }

        String[] params = {"userId", "username"};
        Object[] values = {userId, username};
        Type[] types = {Hibernate.STRING, Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("user_get_user_by_username_exclude_user_id", params, values, types);

    }

    /**
     * Quck check to see if user exists. That simple!
     *
     * @param username
     * @return boolean
     */
    public User getUserByUsername(String username) throws PersistenceException {
        if (StringUtils.isBlank(username)) {
            throw new PersistenceException("error.missing.argument.exception", "username cannot be null");
        }

        String[] params = {"username"};
        Object[] values = {username};
        Type[] types = {Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("user_get_user_by_username", params, values, types);

    }

    public User getUserBySecurityCode(String securityCode) throws PersistenceException {
        User result = null;

        if (StringUtils.isBlank(securityCode)) {
            throw new PersistenceException("error.missing.argument.exception", "security code cannot be null");
        }

        Criteria c = getSession().createCriteria(User.class);
        c.add(Restrictions.eq("securityCode", securityCode));

        result = (User) c.uniqueResult();

        return result;
    }

    /**
     * Returns a paged set of users. Name can be first name, last name
     *
     * @param name
     * @param index
     * @param maxResults
     * @return
     * @throws PersistenceException
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (index == null) {
            throw new PersistenceException("error.missing.argument.exception", "index");
        }
        if (maxResults == null) {
            throw new PersistenceException("error.missing.argument.exception", "maxResults");
        }

        if (StringUtils.isNotBlank(name)) {
            c.createAlias("userInfo", "ui");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("ui.firstName", name + "%"));
            d.add(Restrictions.ilike("ui.lastName", name + "%"));
            c.add(d);
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("username"));

        return c.list();
    }

    public List<User> getUsers(List<String> userIds, Integer index, Integer maxResults) throws PersistenceException {
        List<User> result = null;

        if (userIds != null && userIds.size() > 0) {
            Criteria c = getSession().createCriteria(User.class);

            c.add(Restrictions.in("id", userIds));

            if (index != null && maxResults != null) {
                c.setFirstResult(index * maxResults);
                c.setMaxResults(maxResults);
            }

            result = c.list();
        }

        return result;
    }

    public Integer getUserCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(name)) {
            c.createAlias("userInfo", "ui");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("ui.firstName", name + "%"));
            d.add(Restrictions.ilike("ui.lastName", name + "%"));
            c.add(d);
        }
        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }

    public List<User> getLastModifiedUsers(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }

    public List<User> searchForUsersWithExcludes(List<String> excludedUserIds, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(name)) {
            c.createAlias("userInfo", "ui");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("ui.firstName", name + "%"));
            d.add(Restrictions.ilike("ui.lastName", name + "%"));
            c.add(d);
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index);
            c.setMaxResults(maxResults);
        }

        // here we exclude the user ids we don't want to have returned
        if (excludedUserIds != null) {
            Conjunction conj = Restrictions.conjunction();

            for (String id : excludedUserIds) {
                conj.add(Restrictions.ne("id", id));
            }

            c.add(conj);
        }
        c.addOrder(Order.asc("username"));

        return c.list();
    }

    public Integer searchForUsersWithExcludesCount(List<String> excludedUserIds, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(User.class);

        if (StringUtils.isNotBlank(name)) {
            c.createAlias("userInfo", "ui");
            Disjunction d = Restrictions.disjunction();
            d.add(Restrictions.ilike("username", name + "%"));
            d.add(Restrictions.ilike("ui.firstName", name + "%"));
            d.add(Restrictions.ilike("ui.lastName", name + "%"));
            c.add(d);
        }

        // here we exclude the user ids we don't want to have returned
        if (excludedUserIds != null) {
            Conjunction conj = Restrictions.conjunction();

            for (String id : excludedUserIds) {
                conj.add(Restrictions.ne("id", id));
            }

            c.add(conj);
        }
        c.setProjection(Projections.rowCount());

        return (Integer) c.uniqueResult();
    }
}
