/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.dao.impl;

import com.hxcel.globalhealth.domain.socialnetwork.dao.RelationshipDAO;
import com.hxcel.globalhealth.domain.socialnetwork.dao.RecommendationDAO;
import com.hxcel.globalhealth.domain.socialnetwork.dao.GroupDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Recommendation;
import com.hxcel.globalhealth.domain.socialnetwork.model.Group;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 15, 2006
 * Time: 2:19:22 PM
 * <p/>
 * <p/>
 * Description: see interface
 */
@SuppressWarnings("unchecked")
public class GroupDAOHibernate extends AbstractHibernateDAO<Group, String> implements GroupDAO {
    private static final Logger log = LoggerFactory.getLogger(GroupDAOHibernate.class);

    public Group getGroupByToken(String token) throws PersistenceException {
        Criteria c = getSession().createCriteria(Group.class);
        c.add(Restrictions.eq("token", token));

        return (Group) c.uniqueResult();
    }
}