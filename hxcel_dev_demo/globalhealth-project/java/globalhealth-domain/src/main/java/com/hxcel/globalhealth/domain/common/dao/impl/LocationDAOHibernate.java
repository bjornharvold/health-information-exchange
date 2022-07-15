/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao.impl;

import com.hxcel.globalhealth.domain.common.dao.LocationDAO;
import com.hxcel.globalhealth.domain.common.model.Location;
import com.hxcel.globalhealth.domain.utils.hibernate.impl.AbstractHibernateDAO;

/**
 * <p>Title: LocationDAOHibernate</p>
 * <p>Description: Handles all interaction locations</p>
 *
 * @author Bjorn Harvold
 */
public class LocationDAOHibernate extends AbstractHibernateDAO<Location, String> implements LocationDAO {

    public Location newLocation() {
        return new Location();
    }
}
