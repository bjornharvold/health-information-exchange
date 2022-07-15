package com.hxcel.globalhealth.domain.platform.dao;

import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.platform.model.License;
import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.enums.LicenseStatusCd;

import java.util.List;

/**
 * User: bjorn
 * Date: Sep 21, 2008
 * Time: 2:07:45 PM
 */
public interface LicenseDAO extends GenericDAO<License, String> {
    List<License> searchForApplicationLicenses(String applicationId, String name, LicenseStatusCd status, Integer index, Integer maxResults) throws PersistenceException;
    Integer searchForApplicationsLicensesCount(String applicationId, String name, LicenseStatusCd status) throws PersistenceException;
    List<License> getLicensedApplicationsExcludeOwner(String organizationId, Boolean platform) throws PersistenceException;
}
