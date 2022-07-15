/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.professional.model.Professional;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.NamedQueries(
        {
        @org.hibernate.annotations.NamedQuery(name = "association_get_associations_by_professional_id",
                query = "FROM Association a WHERE a.professional.id = :professionalId")
                }
)
public class Association extends AbstractEntity implements Serializable {


    private Professional professional;
    private String name;
    private String description;

    public Association() {
        super();
    }

    @ManyToOne
    public Professional getProfessional() {
        return this.professional;
    }


    public void setProfessional(Professional value) {
        this.professional = value;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String value) {
        this.name = value;
    }


    public String getDescription() {
        return this.description;
    }


    public void setDescription(String value) {
        this.description = value;
    }

}
