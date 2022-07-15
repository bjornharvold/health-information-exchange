/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.dao.impl;

import com.hxcel.globalhealth.common.hibernate.impl.AbstractHibernateDAO;
import com.hxcel.globalhealth.common.spec.PersistenceException;
import com.hxcel.globalhealth.platform.model.Country;
import com.hxcel.globalhealth.platform.dao.CountryDAO;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Order;
import org.hibernate.type.Type;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 16, 2006
 * Time: 2:52:24 PM
 * <p/>
 * <p/>
 * Description:
 */
public class CountryDAOHibernate extends AbstractHibernateDAO<Country, String> implements CountryDAO {

    public List<Country> getCountries() throws PersistenceException {
        return findByNamedQuery("countrycd_get_countries");
    }

    public List<Country> findByCurrencyCode(String curr) throws PersistenceException {
        return findByNamedQueryAndNamedParam("countrycd_get_countries_by_currency_code", "currencyCode", curr);
    }

    public List<Country> getCountriesWithoutDailyExchangeRateUpdates() throws PersistenceException {
        return findByNamedQuery("countrycd_get_countries_without_daily_exchange_rate_updates");
    }

    public Country findCountryByName(CountryCd countryName) throws PersistenceException {
        if (countryName == null) {
            throw new PersistenceException("error.missing.argument.exception", "countryName cannot be null");
        }
        String[] params = {"countryName"};
        Object[] values = {countryName.name()};
        Type[] types = {Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("countrycd_get_country_by_name", params, values, types);
    }

    public Country findCountryByLocale(String countryCode, String languageCode) throws PersistenceException {
        if (StringUtils.isBlank(countryCode)) {
            throw new PersistenceException("error.missing.argument.exception", "countryCode cannot be null");
        }
        if (StringUtils.isBlank(languageCode)) {
            throw new PersistenceException("error.missing.argument.exception", "languageCode cannot be null");
        }
        String[] params = {"countryCode", "languageCode"};
        Object[] values = {countryCode, languageCode};
        Type[] types = {Hibernate.STRING, Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("countrycd_get_country_by_locale", params, values, types);
    }

    public Country findCountryByCountryCode(String countryCode) throws PersistenceException {
        if (StringUtils.isBlank(countryCode)) {
            throw new PersistenceException("error.missing.argument.exception", "countryCode cannot be null");
        }

        String[] params = {"countryCode"};
        Object[] values = {countryCode};
        Type[] types = {Hibernate.STRING};

        return findObjectByNamedQueryAndNamedParam("countrycd_get_country_by_country_code", params, values, types);
    }

    public Country getCountry(CountryCd country) throws PersistenceException {
        Criteria criteria = getSession().createCriteria(Country.class);
        criteria.setCacheable(true);
        criteria.add(Restrictions.eq("statusCode", country.name()));

        return (Country) criteria.uniqueResult();
    }

    public Integer getCountryCount() throws PersistenceException {
        Criteria criteria = getSession().createCriteria(Country.class);
        criteria.setCacheable(true);
        criteria.setProjection(Projections.rowCount());

        return (Integer) criteria.list().get(0);
    }

    public List<Country> searchForCountries(String name, Integer index, Integer maxResult) throws PersistenceException {
        Criteria c = getSession().createCriteria(Country.class);

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(
                    Restrictions.ilike("countryCode", name + "%"),
                    Restrictions.ilike("statusCode", name + "%"))
            );
        }

        if (index != null && maxResult != null) {
            c.setFirstResult(index * maxResult);
            c.setMaxResults(maxResult);
        }

        c.addOrder(Order.asc("statusCode"));

        return c.list();
    }

    public Integer searchForCountriesCount(String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Country.class);
        c.setCacheable(true);
        c.setProjection(Projections.rowCount());

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.or(
                    Restrictions.ilike("countryCode", name + "%"),
                    Restrictions.ilike("statusCode", name + "%"))
            );
        }

        return (Integer) c.list().get(0);
    }

    public Country getCountry(String countryId) throws PersistenceException {
        return get(Country.class, countryId);
    }

    public List<Country> getLastModifiedCountries(Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Country.class);

        if (maxResults != null) {
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.desc("lastUpdate"));

        return c.list();
    }
}
