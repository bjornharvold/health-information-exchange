/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.socialnetwork.dao;


import com.hxcel.globalhealth.domain.socialnetwork.model.Relationship;
import com.hxcel.globalhealth.domain.socialnetwork.model.Group;
import com.hxcel.globalhealth.domain.socialnetwork.model.PublicProfile;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Feb 13, 2007
 * Time: 10:53:50 PM
 */
public interface PublicProfileDAO extends GenericDAO<PublicProfile, String> {
}