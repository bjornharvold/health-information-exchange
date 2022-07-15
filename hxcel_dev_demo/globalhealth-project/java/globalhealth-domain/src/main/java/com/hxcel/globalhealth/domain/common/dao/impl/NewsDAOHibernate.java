/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.NewsDAO;
import com.hxcel.globalhealth.domain.common.model.News;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: NewsDAOHibernate</p>
 * <p>Description: Handles all operations related to handling news and news channels
 * All news items have a 2 week expiration date; meaning they wont show up after 2 weeks.
 * </p>
 *
 * @author Bjorn Harvold
 */
public class NewsDAOHibernate extends AbstractHibernateDAO<News, String> implements NewsDAO {
    private static final Logger log = LoggerFactory.getLogger(NewsDAOHibernate.class);

    public List<News> getNews(String entityId, EntityTypeCd entityType, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(News.class);
        c.add(Restrictions.eq("entityId", entityId));
        c.add(Restrictions.eq("entityType", entityType));

        if (index != null && index > -1 && maxResults != null && maxResults > 0) {
            c.setFirstResult(index);
            c.setMaxResults(maxResults);
        }

        return c.list();
    }

    public void deleteUserNews(String userId) throws PersistenceException {
        Criteria c = getSession().createCriteria(News.class);

        c.add(Restrictions.eq("entityId", userId));
        c.add(Restrictions.eq("entityType", EntityTypeCd.USER));

        deleteAll(c.list());
    }
}
