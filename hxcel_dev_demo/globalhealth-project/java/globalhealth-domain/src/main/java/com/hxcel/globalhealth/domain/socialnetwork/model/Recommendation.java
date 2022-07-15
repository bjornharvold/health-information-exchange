package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.io.Serializable;

/**
 * User: bjorn
 * Date: Sep 5, 2008
 * Time: 10:12:27 PM
 * This entity stores recommendations and ratings.
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "entitytype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")}
        ),
        @TypeDef(name = "recommendationtype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.RecommendationTypeCd")}
        )
                }
)
public class Recommendation extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 8649785061895078370L;

    public Recommendation(){}

    public Recommendation(String recommenderId, EntityTypeCd recommenderType, String recommendedId, EntityTypeCd recommendedType, String message, Integer weight, RecommendationTypeCd recommendationType) {
        this.recommenderId = recommenderId;
        this.recommendedId = recommendedId;
        this.recommenderType = recommenderType;
        this.recommendedType = recommendedType;
        this.recommendationType = recommendationType;
        this.message = message;
        this.weight = weight;
    }

    // entity recommending
    private String recommenderId = null;
    // entity recommended
    private String recommendedId = null;
    // entity type e.g. patient
    private EntityTypeCd recommenderType = null;
    // entity type e.g. doctor
    private EntityTypeCd recommendedType = null;
    // rating or recommendation
    private RecommendationTypeCd recommendationType = null;
    // user message
    private String message = null;
    // 5 stars or something
    private Integer weight = null;

    public String getRecommenderId() {
        return recommenderId;
    }

    public void setRecommenderId(String recommenderId) {
        this.recommenderId = recommenderId;
    }

    public String getRecommendedId() {
        return recommendedId;
    }

    public void setRecommendedId(String recommendedId) {
        this.recommendedId = recommendedId;
    }

    @Type(type = "entitytype")
    public EntityTypeCd getRecommenderType() {
        return recommenderType;
    }

    public void setRecommenderType(EntityTypeCd recommenderType) {
        this.recommenderType = recommenderType;
    }

    @Type(type = "entitytype")
    public EntityTypeCd getRecommendedType() {
        return recommendedType;
    }

    public void setRecommendedType(EntityTypeCd recommendedType) {
        this.recommendedType = recommendedType;
    }

    @Type(type = "recommendationtype")
    public RecommendationTypeCd getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(RecommendationTypeCd recommendationType) {
        this.recommendationType = recommendationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
