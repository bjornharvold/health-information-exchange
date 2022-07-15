/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.model;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
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
        @TypeDef(name = "diagnosistype", typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd")}
        )
        }
)
public class Diagnosis extends AbstractEntity implements Serializable {

    private Country countryCd;
    private String code;
    private String name;
    private String description;
    private List<Diagnosis> children;
    private DiagnosisTypeCd diagnosisType;

    // this is so we can later update with a newer one
    private String diagnosisVersion;

    @ManyToOne
    public Country getCountryCd() {
        return this.countryCd;
    }

    public void setCountryCd(Country value) {
        this.countryCd = value;
    }


    public String getCode() {
        return this.code;
    }


    public void setCode(String value) {
        this.code = value;
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

    @Type(type = "diagnosistype")
    public DiagnosisTypeCd getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(DiagnosisTypeCd diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public String getDiagnosisVersion() {
        return diagnosisVersion;
    }

    public void setDiagnosisVersion(String diagnosisVersion) {
        this.diagnosisVersion = diagnosisVersion;
    }

    @OneToMany
    public List<Diagnosis> getChildren() {
        return children;
    }

    public void setChildren(List<Diagnosis> children) {
        this.children = children;
    }

    
}
