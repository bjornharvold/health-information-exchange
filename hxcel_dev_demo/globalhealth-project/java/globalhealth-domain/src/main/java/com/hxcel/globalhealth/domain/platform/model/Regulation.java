package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.platform.model.enums.RegulationTypeCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.jasypt.hibernate.type.EncryptedStringType;

/**
 * User: bjorn
 * Date: Nov 8, 2008
 * Time: 4:44:10 PM
 */
@TypeDefs(
        {
            @TypeDef(name = "regulationtype",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.platform.model.enums.RegulationTypeCd")}
            )
         }
)
@Entity
public class Regulation extends AbstractEntity {
    private String name;
    private String key;
    private String value;
    private String description;
    private RegulationTypeCd type;

    @Column(nullable = false)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @NotNull
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @Type(type = "regulationtype")
    @Column(nullable = false)
    @NotNull
    public RegulationTypeCd getType() {
        return type;
    }

    public void setType(RegulationTypeCd type) {
        this.type = type;
    }

    @Transient
    public String getLabel() {
        return key + " : " + value + " : " + type;
    }
}
