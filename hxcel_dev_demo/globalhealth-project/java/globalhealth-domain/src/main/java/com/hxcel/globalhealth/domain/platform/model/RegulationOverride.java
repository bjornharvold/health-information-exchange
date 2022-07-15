package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Country;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

import org.hibernate.validator.NotNull;

/**
 * User: bjorn
 * Date: Nov 8, 2008
 * Time: 4:44:10 PM
 * Overrides an existing default regulation with one on a country level
 */
@Entity
public class RegulationOverride extends AbstractEntity {
    private Regulation original;
    private Country country;
    private String value;
    private String description;

    @ManyToOne(optional = false)
    public Regulation getOriginal() {
        return original;
    }

    public void setOriginal(Regulation original) {
        this.original = original;
    }

    @ManyToOne(optional = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Column(nullable = false)
    @NotNull
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}