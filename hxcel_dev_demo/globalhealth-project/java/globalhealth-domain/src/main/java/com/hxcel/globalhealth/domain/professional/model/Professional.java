/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.professional.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.professional.model.enums.ProfessionalStatusCd;
import com.hxcel.globalhealth.domain.professional.model.enums.ProfessionalTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@TypeDefs(
        {
        @TypeDef(name = "protype", typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.professional.model.enums.ProfessionalTypeCd")}
        ),
        @TypeDef(name = "prostatus", typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.professional.model.enums.ProfessionalStatusCd")}
        )
                }
)
public class Professional extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -5380354272397167659L;
    private ProfessionalTypeCd professionalTypeCd;
    private ProfessionalStatusCd professionalStatusCd;
    private ProfessionalSettings professionalSettings;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Type(type = "protype")
    public ProfessionalTypeCd getProfessionalTypeCd() {
        return this.professionalTypeCd;
    }


    public void setProfessionalTypeCd(ProfessionalTypeCd value) {
        this.professionalTypeCd = value;
    }

    @Type(type = "prostatus")
    public ProfessionalStatusCd getProfessionalStatusCd() {
        return this.professionalStatusCd;
    }


    public void setProfessionalStatusCd(ProfessionalStatusCd value) {
        this.professionalStatusCd = value;
    }

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public ProfessionalSettings getProfessionalSettings() {
        return this.professionalSettings;
    }


    public void setProfessionalSettings(ProfessionalSettings value) {
        this.professionalSettings = value;
    }

}
