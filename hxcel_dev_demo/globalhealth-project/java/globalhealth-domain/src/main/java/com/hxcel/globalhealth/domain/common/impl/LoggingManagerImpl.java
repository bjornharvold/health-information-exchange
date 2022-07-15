package com.hxcel.globalhealth.domain.common.impl;

import com.hxcel.globalhealth.domain.common.LoggingManager;
import com.hxcel.globalhealth.domain.common.dao.NewsDAO;
import com.hxcel.globalhealth.domain.common.model.enums.ActivityLoggerTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.SystemAuditLoggerTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.AccessedEntryTypeCd;
import com.hxcel.globalhealth.domain.common.model.News;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Aug 17, 2008
 * Time: 5:10:28 PM
 */
public class LoggingManagerImpl implements LoggingManager {
    private final static Logger log = LoggerFactory.getLogger(LoggingManagerImpl.class);

    // light-weight "user status" logger
    public void logActivity(String entityId, ActivityLoggerTypeCd type, String logMessage) throws DomainException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    // heavy-weight access logger
    public void logAudit(String loggerId, String loggedId, String accessedEntryId,
                         ActivityLoggerTypeCd activityLoggerType, ActivityLoggerTypeCd activityLoggedType,
                         SystemAuditLoggerTypeCd loggerType, AccessedEntryTypeCd accessedEntryType,
                         String logMessage) throws DomainException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<News> getNews(String entityId, EntityTypeCd entityType, Integer index, Integer maxResults) throws DomainException {
        List<News> result = null;

        if (StringUtils.isBlank(entityId)) {
            log.error("entityId is null");
            throw new DomainException("error.missing.argument.exception", "entityId");
        }
        if (entityType == null) {
            log.error("entityType is null");
            throw new DomainException("error.missing.argument.exception", "entityType");
        }

        try {
            result = newsDAO.getNews(entityId, entityType, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    /**
     * Saves a news entry
     * @param news
     * @return
     * @throws DomainException
     */
    public News createNewsEntry(News news) throws DomainException {
        if (news == null) {
            log.error("news is null");
            throw new DomainException("error.missing.argument.exception", "news");
        }
        if (news.getEntityType() == null) {
            log.error("entityType is null");
            throw new DomainException("error.missing.argument.exception", "entityType");
        }
        if (StringUtils.isBlank(news.getContent())) {
            log.error("content is null");
            throw new DomainException("error.missing.argument.exception", "content");
        }
        if (StringUtils.isBlank(news.getTitle())) {
            log.error("title is null");
            throw new DomainException("error.missing.argument.exception", "title");
        }
        if (StringUtils.isBlank(news.getEntityId())) {
            log.error("entityId is null");
            throw new DomainException("error.missing.argument.exception", "entityId");
        }

        try {
            news = newsDAO.save(news);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return news;
    }

    public void deleteUserNews(String userId) throws DomainException {
        if (StringUtils.isBlank(userId)) {
            log.error("userId is null");
            throw new DomainException("error.missing.argument.exception", "userId");
        }

        try {
            newsDAO.deleteUserNews(userId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }
    }

    // Spring IoC
    @Autowired
    private NewsDAO newsDAO;
}
