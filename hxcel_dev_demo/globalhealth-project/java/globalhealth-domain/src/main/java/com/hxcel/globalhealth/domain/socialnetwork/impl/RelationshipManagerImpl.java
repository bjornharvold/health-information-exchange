package com.hxcel.globalhealth.domain.socialnetwork.impl;

import com.hxcel.globalhealth.domain.socialnetwork.RelationshipManager;
import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Recommendation;
import com.hxcel.globalhealth.domain.socialnetwork.dao.RelationshipDAO;
import com.hxcel.globalhealth.domain.socialnetwork.dao.RecommendationDAO;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.dto.StatusDto;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 9:42:14 PM
 */
public class RelationshipManagerImpl implements RelationshipManager {
    private final static Logger log = LoggerFactory.getLogger(RelationshipManagerImpl.class);

    public Relationship initiateRelationship(String requesterId, String requestedId, EntityTypeCd requesterType, EntityTypeCd requestedType, Integer role) throws DomainException {
        Relationship result = null;
        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (StringUtils.isBlank(requestedId)) {
            String error = "requestedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedId");
        }
        if (requesterType == null) {
            String error = "requesterType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterType");
        }
        if (requestedType == null) {
            String error = "requestedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedType");
        }

        try {
            String authToken = UUID.randomUUID().toString();

            result = relationshipDAO.initiateRelationship(requesterId, requestedId, requesterType, requestedType, role, authToken);

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public Relationship createRelationshipNow(String requesterId, String requestedId, EntityTypeCd requesterType, EntityTypeCd requestedType, Integer role) throws DomainException {
        Relationship result = null;
        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (StringUtils.isBlank(requestedId)) {
            String error = "requestedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedId");
        }
        if (requesterType == null) {
            String error = "requesterType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterType");
        }
        if (requestedType == null) {
            String error = "requestedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedType");
        }

        try {

            if (log.isTraceEnabled()) {
                log.trace("Creating a relationship between requester id: " + requesterId + "(of type " + requesterType.name() + ") and requested id: " + requestedId + "(of type " + requestedType.name() + ")");
            }
            result = relationshipDAO.createRelationshipNow(requesterId, requestedId, requesterType, requestedType, role);

        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public Relationship confirmRelationship(String authenticationToken) throws DomainException {
        Relationship result = null;
        if (StringUtils.isBlank(authenticationToken)) {
            String error = "authenticationToken cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "authenticationToken");
        }

        try {
            result = relationshipDAO.confirmRelationship(authenticationToken);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public Relationship rejectRelationship(String authenticationToken) throws DomainException {
        Relationship result = null;
        if (StringUtils.isBlank(authenticationToken)) {
            String error = "authenticationToken cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "authenticationToken");
        }

        try {
            result = relationshipDAO.rejectRelationship(authenticationToken);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public RelationshipStatusCd getRelationshipStatus(String requesterId, String requestedId) throws DomainException {
        RelationshipStatusCd result = null;

        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (StringUtils.isBlank(requestedId)) {
            String error = "requestedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedId");
        }

        try {
            result = relationshipDAO.getRelationshipStatus(requesterId, requestedId);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public StatusDto removeRelationship(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws DomainException {
        StatusDto result = null;

        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (StringUtils.isBlank(requestedId)) {
            String error = "requestedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedId");
        }
        if (requesterType == null) {
            String error = "requesterType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterType");
        }
        if (requestedType == null) {
            String error = "requestedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedType");
        }

        try {
            String id = relationshipDAO.removeRelationship(requesterId, requesterType, requestedId, requestedType);

            // and create the status dto
            StringBuilder sysMsg = new StringBuilder();
            sysMsg.append("Relationship removed with requester id: ");
            sysMsg.append(requesterId);
            sysMsg.append(" requester type: ");
            sysMsg.append(requesterType);
            sysMsg.append(" requested id: ");
            sysMsg.append(requestedId);
            sysMsg.append(" requested type: ");
            sysMsg.append(requestedType);

            String code = "socialnetwork.relationship.record.removed";
            String[] params = {id, requesterId, requesterType.name(), requestedId, requestedType.name()};

            result = new StatusDto(sysMsg.toString(), code, params, true, id, RelationshipStatusCd.REMOVED.name());
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public List<Relationship> getRelationshipsByRequesterIdAndRequestedType(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws DomainException {
        List<Relationship> result = null;

        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (requestedType == null) {
            String error = "requestedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedType");
        }
        if (status == null) {
            String error = "status cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = relationshipDAO.getRelationshipsByRequesterIdAndRequestedType(requesterId, requestedType, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public List<Relationship> getRelationshipsByRequestedIdAndRequesterType(String requestedId, EntityTypeCd requesterType, RelationshipStatusCd status) throws DomainException {
        List<Relationship> result = null;

        if (StringUtils.isBlank(requestedId)) {
            String error = "requestedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedId");
        }
        if (requesterType == null) {
            String error = "requesterType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterType");
        }
        if (status == null) {
            String error = "status cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = relationshipDAO.getRelationshipsByRequestedIdAndRequesterType(requestedId, requesterType, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public List<String> getRelationshipIds(String requesterId, EntityTypeCd requestedType, RelationshipStatusCd status) throws DomainException {
        List<String> result = null;

        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (requestedType == null) {
            String error = "requestedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedType");
        }
        if (status == null) {
            String error = "status cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "status");
        }

        try {
            result = relationshipDAO.getRelationshipIds(requesterId, requestedType, status);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    public List getRelationshipEntities(String requesterId, EntityTypeCd requestedType, Class entityClazz, RelationshipStatusCd status) throws DomainException {
        List result = null;
        // first we grab the ids using the above method
        List<String> ids = getRelationshipIds(requesterId, requestedType, status);

        if (ids != null) {
            result = relationshipDAO.getRelationshipEntities(ids, entityClazz);
        }

        return result;
    }

    public List<Recommendation> getRecommendations(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException {
        return getRecommendationInternal(recommendedId, recommendedType, recommenderType, RecommendationTypeCd.RECOMMENDATION);
    }

    public List<String> getRecommendationIds(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException {
        return getRecommendationIdsInternal(recommendedId, recommendedType, recommenderType, RecommendationTypeCd.RECOMMENDATION);
    }

    public List<Recommendation> getRatings(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException {
        return getRecommendationInternal(recommendedId, recommendedType, recommenderType, RecommendationTypeCd.RATING);
    }

    public List<String> getRatingIds(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType) throws DomainException {
        return getRecommendationIdsInternal(recommendedId, recommendedType, recommenderType, RecommendationTypeCd.RATING);
    }

    public Recommendation createRecommendation(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight) throws DomainException {
        return createRecommendationInternal(recommenderId, recommenderType, recommendedId, recommenderType, message, weight, RecommendationTypeCd.RECOMMENDATION);
    }

    public Recommendation createRating(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight) throws DomainException {
        return createRecommendationInternal(recommenderId, recommenderType, recommendedId, recommenderType, message, weight, RecommendationTypeCd.RATING);
    }

    public Recommendation getRecommendation(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType) throws DomainException {
        return getRecommendationInternal(recommendedId, recommendedType, recommenderId, recommenderType, RecommendationTypeCd.RECOMMENDATION);
    }

    public Recommendation getRating(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType) throws DomainException {
        return getRecommendationInternal(recommendedId, recommendedType, recommenderId, recommenderType, RecommendationTypeCd.RATING);
    }

    public StatusDto removeRecommendation(String requesterId, EntityTypeCd requesterType, String requestedId, EntityTypeCd requestedType) throws DomainException {
        StatusDto result = null;

        if (StringUtils.isBlank(requesterId)) {
            String error = "requesterId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterId");
        }
        if (StringUtils.isBlank(requestedId)) {
            String error = "requestedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedId");
        }
        if (requesterType == null) {
            String error = "requesterType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requesterType");
        }
        if (requestedType == null) {
            String error = "requestedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "requestedType");
        }

        try {
            recommendationDAO.removeRecommendation(requesterId, requesterType, requestedId, requestedType);

            // and create the status dto
            StringBuilder sysMsg = new StringBuilder();
            sysMsg.append("Recommendation removed with requester id: ");
            sysMsg.append(requesterId);
            sysMsg.append(" requester type: ");
            sysMsg.append(requesterType);
            sysMsg.append(" requested id: ");
            sysMsg.append(requestedId);
            sysMsg.append(" requested type: ");
            sysMsg.append(requestedType);

            String code = "socialnetwork.relationship.record.removed";
            String[] params = {requesterId, requesterType.name(), requestedId, requestedType.name()};

            result = new StatusDto(sysMsg.toString(), code, params, true, null, RelationshipStatusCd.REMOVED.name());
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;

    }

    private Recommendation getRecommendationInternal(String recommendedId, EntityTypeCd recommendedType, String recommenderId, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws DomainException {
        Recommendation result = null;

        if (StringUtils.isBlank(recommendedId)) {
            String error = "recommendedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedId");
        }
        if (StringUtils.isBlank(recommenderId)) {
            String error = "recommenderId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommenderId");
        }
        if (recommendedType == null) {
            String error = "recommendedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedType");
        }
        if (recommenderType == null) {
            String error = "recommenderType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommenderType");
        }

        try {
            result = recommendationDAO.getRecommendation(recommendedId, recommendedType, recommenderId, recommenderType, recommendationType);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    private List<Recommendation> getRecommendationInternal(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws DomainException {
        List<Recommendation> result = null;

        if (StringUtils.isBlank(recommendedId)) {
            String error = "recommendedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedId");
        }
        if (recommendedType == null) {
            String error = "recommendedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedType");
        }
        if (recommenderType == null) {
            String error = "recommenderType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommenderType");
        }

        try {
            result = recommendationDAO.getRecommendations(recommendedId, recommendedType, recommenderType, recommendationType);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    private Recommendation createRecommendationInternal(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight, RecommendationTypeCd recommendationType) throws DomainException {
        Recommendation result = null;

        if (StringUtils.isBlank(recommendedId)) {
            String error = "recommendedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedId");
        }
        if (StringUtils.isBlank(recommenderId)) {
            String error = "recommenderId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommenderId");
        }
        if (recommendedType == null) {
            String error = "recommendedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedType");
        }
        if (recommenderType == null) {
            String error = "recommenderType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommenderType");
        }

        try {

            result = getRecommendation(recommendedId, recommendedType, recommenderId, recommenderType);

            if (result == null) {
                result = recommendationDAO.createRecommendation(recommenderId, recommenderType, recommendedId, recommendedType, message, weight, recommendationType);
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Recommendation already exists between recommendedId: " + recommendedId + ", recommendedType: " + recommendedType + ", recommenderId: " + recommenderId + ", recomenderType: " + recommenderType);
                }
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    private List<String> getRecommendationIdsInternal(String recommendedId, EntityTypeCd recommendedType, EntityTypeCd recommenderType, RecommendationTypeCd recommendationType) throws DomainException {
            List<String> result = null;

        if (StringUtils.isBlank(recommendedId)) {
            String error = "recommendedId cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedId");
        }
        if (recommendedType == null) {
            String error = "recommendedType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommendedType");
        }
        if (recommenderType == null) {
            String error = "recommenderType cannot be null";
            log.error(error);
            throw new DomainException("error.missing.argument.exception", "recommenderType");
        }

        try {
            result = recommendationDAO.getRecommendationIds(recommendedId, recommendedType, recommenderType, recommendationType);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e, e.getParams());
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private RelationshipDAO relationshipDAO;

    @Autowired
    private RecommendationDAO recommendationDAO;
}
