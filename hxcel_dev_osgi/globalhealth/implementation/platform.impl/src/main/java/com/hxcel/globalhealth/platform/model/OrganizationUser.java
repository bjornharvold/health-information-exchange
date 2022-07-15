package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractEntity;
import com.hxcel.globalhealth.platform.utils.EnumUserType;
import com.hxcel.globalhealth.platform.spec.model.enums.OrganizationUserStatusCd;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import org.hibernate.annotations.*;

/**
 * User: Bjorn Harvold
 * Date: Oct 10, 2008
 * Time: 8:57:36 PM
 * Description:
 */
@Entity
@TypeDefs(
        {
            @TypeDef(name = "status",
                    typeClass = EnumUserType.class,
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.platform.spec.model.enums.OrganizationUserStatusCd")}
            )
                }
)
public class OrganizationUser extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -1007136748003835218L;
    private Organization organization;
    private User user;
    private OrganizationUserStatusCd status;

    public OrganizationUser() {}

    public OrganizationUser(Organization organization, User user) {
        this.organization = organization;
        this.user = user;
    }

    @ManyToOne(optional = false)
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Type(type = "status")
    public OrganizationUserStatusCd getStatus() {
        return status;
    }

    public void setStatus(OrganizationUserStatusCd status) {
        this.status = status;
    }
}
