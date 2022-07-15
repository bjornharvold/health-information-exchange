/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.common.dao;

import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.utils.hibernate.GenericDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
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

    /**
     * Returns a list of CountryCds that use the passed currency code
     *
     * @param curr
     * @return
     * @throws com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException
     */
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
