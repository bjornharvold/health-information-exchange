package com.hxcel.globalhealth.domain.socialnetwork.model;

import com.hxcel.globalhealth.domain.common.model.UserInfo;
import com.hxcel.globalhealth.domain.common.model.ActivityLogger;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * User: bjorn
 * Date: Aug 14, 2008
 * Time: 1:01:26 PM
 */
@Entity
public class PublicProfile extends AbstractProfile implements Serializable {
    private static final long serialVersionUID = 3964686685924443610L;

}
