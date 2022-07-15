/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.model;

import com.hxcel.globalhealth.common.model.AbstractReferenceDataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NamedQueries(
        {
        @NamedQuery(name = "countrycd_get_countries",
                query = "SELECT cc FROM Country cc WHERE cc.exchangeRate IS NOT NULL ORDER BY cc.statusCode"),
        @NamedQuery(name = "countrycd_get_countries_by_currency_code",
                query = "SELECT cc FROM Country cc WHERE cc.currencyCode = :currencyCode"),
        @NamedQuery(name = "countrycd_get_country_by_name",
                query = "SELECT cc FROM Country cc WHERE cc.statusCode = :countryName"),
        @NamedQuery(name = "countrycd_get_country_by_locale",
                query = "SELECT cc FROM Country cc WHERE cc.countryCode = :countryCode AND cc.language = :languageCode"),
        @NamedQuery(name = "countrycd_get_country_by_country_code",
                query = "SELECT cc FROM Country cc WHERE cc.countryCode = :countryCode")
                }
)
public class Country extends AbstractReferenceDataEntity implements Serializable {
    private String countryCode;
    private String language;
    private String currencyCode;
    private Integer currencyCodeNumber;
    private String currencyName;
    private Date exchangeDate;
    private BigDecimal exchangeRate;
    private String dateFormatPattern;
    private String timeFormatPattern;
    private String timestampFormatPattern;

    @Column(unique = true, nullable = false)
    public String getCountryCode() {
        return this.countryCode;
    }


    public void setCountryCode(String value) {
        this.countryCode = value;
    }


    public String getLanguage() {
        return this.language;
    }


    public void setLanguage(String value) {
        this.language = value;
    }


    public String getCurrencyCode() {
        return this.currencyCode;
    }


    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }


    public Integer getCurrencyCodeNumber() {
        return this.currencyCodeNumber;
    }


    public void setCurrencyCodeNumber(Integer value) {
        this.currencyCodeNumber = value;
    }


    public String getCurrencyName() {
        return this.currencyName;
    }


    public void setCurrencyName(String value) {
        this.currencyName = value;
    }


    public Date getExchangeDate() {
        return this.exchangeDate;
    }


    public void setExchangeDate(Date value) {
        this.exchangeDate = value;
    }


    public BigDecimal getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    public void setTimeFormatPattern(String timeFormatPattern) {
        this.timeFormatPattern = timeFormatPattern;
    }

    public void setTimestampFormatPattern(String timestampFormatPattern) {
        this.timestampFormatPattern = timestampFormatPattern;
    }

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public String getTimeFormatPattern() {
        return timeFormatPattern;
    }

    public String getTimestampFormatPattern() {
        return timestampFormatPattern;
    }

}
