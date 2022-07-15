package com.hxcel.globalhealth.domain.platform.model;

import com.hxcel.globalhealth.domain.common.model.AbstractEntity;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;
import com.hxcel.globalhealth.domain.platform.model.enums.OrganizationUserStatusCd;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import java.util.List;
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
                    parameters = {@Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.platform.model.enums.OrganizationUserStatusCd")}
            )
                }
)
public class OrganizationUser extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = -1007136748003835218L;
    private Organization organization;
    private User user;
    private OrganizationUserStatusCd status;

    public OrganizationUser() {
    }

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
