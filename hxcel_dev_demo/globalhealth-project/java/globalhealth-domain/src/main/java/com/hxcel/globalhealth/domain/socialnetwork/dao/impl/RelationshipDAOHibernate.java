/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.dao.impl;

import com.hxcel.globalhealth.domain.socialnetwork.dao.RelationshipDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 15, 2006
 * Time: 2:19:22 PM
 * <p/>
 * <p/>
 * Description:
 */
public class RelationshipDAOHibernate extends AbstractHibernateDAO<Relationship, String> implements RelationshipDAO {
    private static final Logger log = LoggerFactory.getLogger(RelationshipDAOHibernate.class);

    public Relationship initiateRelationship(String requesterId, String requestedId, EntityTypeCd requesterType,
                                             EntityTypeCd requestedType, Integer role, String authenticationToken) throws PersistenceException {
        Relationship relationship = getRelationshipFromIds(requesterId, requestedId);

        if (relationship == null) {
            relationship = new Relationship();
            relationship.setRequesterId(requesterId);
            relationship.setRequestedId(requestedId);
            relationship.setRequesterType(requesterType);
            relationship.setRequestedType(requestedType);
            relationship.setRole(role);
            relationship.setAuthenticationToken(authenticationToken);
            relationship.setStatus(RelationshipStatusCd.PENDING);
            relationship = save(relationship);
        } else {
            relationship.setRole(role);
            relationship.setAuthenticationToken(authenticationToken);
            relationship.setStatus(RelationshipStatusCd.PENDING);
            relationship = update(relationship);
        }

        return relationship;
    }

    public Relationship createRelationshipNow(String requesterId, String requestedId, EntityTypeCd requesterType, EntityTypeCd requestedType, Integer role) throws PersistenceException {
        Relationship relationship = getRelationshipFromIds(requesterId, requestedId);

        if (relationship == null) {
            relationship = new Relationship();
            relationship.setRequesterId(requesterId);
            relationship.setRequestedId(requestedId);
            relationship.setRequesterType(requesterType);
            relationship.setRequestedType(requestedType);
            relationship.setRole(role);
            relationship.setStatus(RelationshipStatusCd.ACTIVE);
            relationship = save(relationship);
        } else {
            relationship.setRole(role);
            relationship = update(relationship);
        }

        return relationship;
    }

    public Relationship confirmRelationship(String authenticationToken) throws PersistenceException {
        Relationship relationship = getRelationshipFromToken(authenticationToken);

        if (relationship != null) {
            relationship.setStatus(RelationshipStatusCd.ACTIVE);
            relationship.setAuthenticationToken(null);
            relationship = update(relationship);
        } else {
            String error = "Could not find relationship based on token: " + authenticationToken;
            log.error(error);
            throw new PersistenceException(error);
        }

        return relationship;
    }

    public Relationship rejectRelationship(String authenticationToken) throws PersistenceException {
        Relationship relationship = getRelationshipFromToken(authenticationToken);

        if (relationship != null) {
            relationship.setStatus(RelationshipStatusCd.DENIED);
            relationship.setAuthenticationToken(null);
            relationship = update(relationship);
        } else {
            String error = "Could not find relationship based on token: " + authenticationToken;
            log.error(error);
            throw new PersistenceException(error);
        }

        return relationship;
    }

    public RelationshipStatusCd getRelationshipStatus(String requesterId, String requestedId) throws PersistenceException {
        RelationshipStatusCd result = null;

        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("requesterId", requesterId));
        c.add(Restrictions.eq("requestedId", requestedId));

        Relationship relationship = (Relationship) c.uniqueResult();

        if (relationship != null) {
            result = relationship.getStatus();
        }

        return result;
    }

    public List<Relationship> getRelationshipsByRequesterIdAndRequestedType(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws PersistenceException {
        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("requesterId", requesterId));
        c.add(Restrictions.eq("requestedType", requestedType));
        c.add(Restrictions.eq("status", status));

        return c.list();
    }

    public List<Relationship> getRelationshipsByRequestedIdAndRequesterType(String requestedId, EntityTypeCd requesterType, RelationshipStatusCd status) throws PersistenceException {
        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("requestedId", requestedId));
        c.add(Restrictions.eq("requesterType", requesterType));
        c.add(Restrictions.eq("status", status));

        c.addOrder(Order.desc("createdDate"));
        return c.list();
    }

    /**
     * Returns a list of requestedIds
     *
     * @param requesterId
     * @param requestedType
     * @param status
     * @return
     * @throws PersistenceException
     */
    public List<String> getRelationshipIds(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws PersistenceException {
        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("requesterId", requesterId));
        c.add(Restrictions.eq("requestedType", requestedType));
        c.add(Restrictions.eq("status", status));
        c.setProjection(Projections.distinct(Projections.property("requestedId")));


        return c.list();
    }

    public List getRelationshipEntities(List<String> ids, Class entityClazz) {
        List result = null;

        if (ids != null && ids.size() > 0) {
            Criteria c = getSession().createCriteria(entityClazz);
            c.add(Restrictions.in("id", ids));
            c.addOrder(Order.desc("createdDate"));
            result = c.list();
        }

        return result;
    }

    public String removeRelationship(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws PersistenceException {
        String id = getRelationshipId(requesterId, requesterType, requestedId, requestedType);

        if (StringUtils.isBlank(id)) {
            throw new PersistenceException("error.database.id.na", new String[]{"Relationship"});
        }

        delete(id);

        return id;
    }

    private Relationship getRelationshipFromToken(String authenticationToken) throws PersistenceException {
        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("authenticationToken", authenticationToken));

        return (Relationship) c.uniqueResult();
    }

    private Relationship getRelationshipFromIds(String requesterId, String requestedId) throws PersistenceException {
        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("requesterId", requesterId));
        c.add(Restrictions.eq("requestedId", requestedId));

        return (Relationship) c.uniqueResult();
    }

    private String getRelationshipId(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws PersistenceException {
        Criteria c = getSession().createCriteria(Relationship.class);
        c.add(Restrictions.eq("requesterId", requesterId));
        c.add(Restrictions.eq("requestedId", requestedId));
        c.add(Restrictions.eq("requesterType", requesterType));
        c.add(Restrictions.eq("requestedType", requestedType));
        c.setProjection(Projections.distinct(Projections.property("id")));

        return (String) c.uniqueResult();
    }
}
