/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Image;
import com.hxcel.globalhealth.domain.professional.model.Professional;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@org.hibernate.annotations.NamedQueries(
        {
        @org.hibernate.annotations.NamedQuery(name = "certification_get_certifications_by_professional_id",
                query = "FROM Certification c WHERE c.professional.id = :professionalId")
                }
)
public class Certification extends AbstractEntity implements Serializable {


    private Professional professional;

    private Image image;

    private String name;

    private String description;

    private String certNumber;


    public Certification() {
        super();
    }

    @ManyToOne
    public Professional getProfessional() {
        return this.professional;
    }


    public void setProfessional(Professional value) {
        this.professional = value;
    }


    public Image getImage() {
        return this.image;
    }


    public void setImage(Image value) {
        this.image = value;
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


    public String getCertNumber() {
        return this.certNumber;
    }


    public void setCertNumber(String value) {
        this.certNumber = value;
    }

}
