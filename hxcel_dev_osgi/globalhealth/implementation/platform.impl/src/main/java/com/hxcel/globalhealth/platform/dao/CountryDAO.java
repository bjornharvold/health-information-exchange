/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao;

import com.hxcel.globalhealth.common.spec.hibernate.GenericDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 16, 2006
 * Time: 2:51:27 PM
 * <p/>
 * <p/>
 * Description:
 */
@Repository
public interface CountryDAO extends GenericDAO<Country, String> {


    public List<Country> getCountries() throws PersistenceException;

    public List<Country> findByCurrencyCode(String curr) throws PersistenceException;

    public List<Country> getCountriesWithoutDailyExchangeRateUpdates() throws PersistenceException;

    public Country findCountryByName(CountryCd countryName) throws PersistenceException;

    public Country findCountryByLocale(String countryCode, String languageCode) throws PersistenceException;

    public Country findCountryByCountryCode(String countryCode) throws PersistenceException;

    public Country getCountry(CountryCd country) throws PersistenceException;

    Integer getCountryCount() throws PersistenceException;

    List<Country> searchForCountries(String name, Integer index, Integer maxResult) throws PersistenceException;

    Integer searchForCountriesCount(String name) throws PersistenceException;

    Country getCountry(String countryId) throws PersistenceException;

    List<Country> getLastModifiedCountries(Integer maxResults) throws PersistenceException;
}
