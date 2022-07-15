/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Image;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd;
import com.hxcel.globalhealth.domain.professional.model.Professional;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "specialtytype",
                typeClass = com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd")}
        )
                }
)
@org.hibernate.annotations.NamedQueries(
        {
        @org.hibernate.annotations.NamedQuery(name = "specialty_get_specialties_by_professional_id",
                query = "FROM Specialty c WHERE c.professional.id = :professionalId")
                }
)
public class Specialty extends AbstractEntity implements Serializable {

    private SpecialtyTypeCd specialtyTypeCd;
    private Professional professional;
    private String name;
    private String description;
    private List<Image> images;


    @Type(type = "specialtytype")
    public SpecialtyTypeCd getSpecialtyTypeCd() {
        return this.specialtyTypeCd;
    }


    public void setSpecialtyTypeCd(SpecialtyTypeCd value) {
        this.specialtyTypeCd = value;
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

    @OneToMany
    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> value) {
        this.images = value;
    }
}
