package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.utils.EnumUserType;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Column;

import org.hibernate.annotations.*;
import org.hibernate.validator.NotNull;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 9, 2008
 * Time: 10:19:19 PM
 * Description:
 */
@Entity
@TypeDefs(
        {
            @TypeDef(name = "status",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.OrganizationLicenseStatusCd")}
            )
                }
)
public class OrganizationLicense extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -7965479129099578005L;
    private Organization organization;
    private License license;
    private OrganizationLicenseStatusCd status;
    
    public OrganizationLicense(){}

    public OrganizationLicense(Organization org, License lic){
        organization = org;
        license = lic;
    }

    public OrganizationLicense(Organization org, License lic, OrganizationLicenseStatusCd status){
        organization = org;
        license = lic;
        this.status = status;
    }

    @ManyToOne(optional = false)
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(optional = false)
    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    @Type(type = "status")
    @NotNull
    @Column(nullable = false)
    public OrganizationLicenseStatusCd getStatus() {
        return status;
    }

    public void setStatus(OrganizationLicenseStatusCd status) {
        this.status = status;
    }
}
