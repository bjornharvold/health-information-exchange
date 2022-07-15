package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

import javax.persistence.Entity;
import java.io.Serializable;

import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;

/**
 * User: bjorn
 * Date: Sep 6, 2008
 * Time: 2:48:45 PM
 * Tags can be used to annotate blogs, groups, professionals etc.
 * TODO this still need to be implemented
 */
@Entity
@TypeDefs(
        {
        @TypeDef(name = "country",
                typeClass = EnumUserType.class,
                parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.GroupStatusCd")}
        )
        }
)
public class Tag extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -1871333379796653319L;

    private String tag = null;
    // stemmed version of the tag
    private String stemmedTag = null;
    private CountryCd country = null;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStemmedTag() {
        return stemmedTag;
    }

    public void setStemmedTag(String stemmedTag) {
        this.stemmedTag = stemmedTag;
    }

    public CountryCd getCountry() {
        return country;
    }

    public void setCountry(CountryCd country) {
        this.country = country;
    }
}
