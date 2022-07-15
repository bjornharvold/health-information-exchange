/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.News;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;

import java.util.List;

/**

 * User: Bjorn Harvold
 * Date: Oct 20, 2005
 * Time: 6:43:06 AM
 * Description:
 */
public interface NewsDAO extends GenericDAO<News, String> {
    List<News> getNews(String entityId, EntityTypeCd entityType, Integer index, Integer maxResults) throws PersistenceException;

    void deleteUserNews(String userId) throws PersistenceException;
}
