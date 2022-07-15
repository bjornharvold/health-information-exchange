package com.hxcel.globalhealth.domain.common;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.SystemAuditLoggerTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.AccessedEntryTypeCd;
import com.hxcel.globalhealth.domain.common.model.enums.ActivityLoggerTypeCd;
import com.hxcel.globalhealth.domain.common.model.News;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 10:13:44 PM
 * Responsible for logging user events such as updating a profile; events that can be displayed as part of the
 * user's event status. It also handles tracking access and changes to any part of the system for audit purposes. 
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface LoggingManager {
    // light-weight "user status" logger
    void logActivity(String entityId, ActivityLoggerTypeCd type, String logMessage) throws DomainException;

    // heavy-weight access logger
    void logAudit(String loggerId, String loggedId, String accessedEntryId,
                  ActivityLoggerTypeCd activityLoggerType, ActivityLoggerTypeCd activityLoggedType,
                  SystemAuditLoggerTypeCd loggerType, AccessedEntryTypeCd accessedEntryType, String logMessage) throws DomainException;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    List<News> getNews(String entityId, EntityTypeCd entityType, Integer index, Integer maxResults) throws DomainException;

    News createNewsEntry(News news) throws DomainException;

    void deleteUserNews(String userId) throws DomainException;
}
