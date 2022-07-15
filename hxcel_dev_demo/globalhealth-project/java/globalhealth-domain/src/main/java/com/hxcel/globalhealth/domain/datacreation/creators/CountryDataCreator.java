/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.datacreation.creators;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.ReferenceManager;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreatorException;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * It expects a regular sql file to parse and create country objects for.
 * It expects a line like this:
 * VALUES ('85',  'HT', 'en', '2007-01-01 00:00:00', 'HAITI', 'USD', 840, 'US_DOLLAR', '2007-01-01 00:00:00', 1, 1, '2007-01-01 00:00:00', 'MM/dd/yyyy', 'h:mm a', 'MM/dd/yyyy hh:mm:ss');
 * <p/>
 * IMPORTANT: This wil only run on a clean database as it ony checks to see if there is something in the database from before
 * and wont proceed if there is.
 */
public class CountryDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(CountryDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;

    public void create() throws DataCreatorException {

        try {
            Integer count = referenceManager.getCountryCount();

            if (count == 0) {
                if (file.exists()) {
                    processCreation();
                } else {
                    throw new DataCreatorException("CSV file could not be found");
                }
            } else {
                log.info("Database already contains records. Will not continue");
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        log.info("Populated " + populated + " countries in db");
        log.info("Omitted " + omitted + " countries from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            persist(parseCSVFile());

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private List<Country> parseCSVFile() throws IOException {
        List<Country> result = new ArrayList<Country>();

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> countries = document.selectNodes("//Workbook/Worksheet/Table/Row");

            Country countryCd = null;

            for (Element e : countries) {
                List<Element> cells = e.elements();

                if (cells.size() == 11) {

                    String countryCode = cells.get(0).getTextTrim();
                    String language = cells.get(1).getTextTrim();
                    String statusCode = cells.get(2).getTextTrim();
                    String currencyCode = cells.get(3).getTextTrim();
                    String currencyCodeNumber = cells.get(4).getTextTrim();
                    String currencyName = cells.get(5).getTextTrim();
                    String exchangeDate = cells.get(6).getTextTrim();
                    String exchangeRate = cells.get(7).getTextTrim();
                    String dateFormatPattern = cells.get(8).getTextTrim();
                    String timeFormatPattern = cells.get(9).getTextTrim();
                    String timestampFormatPattern = cells.get(10).getTextTrim();

                    countryCd = new Country();
                    countryCd.setCountryCode(countryCode);
                    countryCd.setLanguage(language);
                    countryCd.setStatusCode(statusCode);
                    countryCd.setCurrencyCode(currencyCode);
                    countryCd.setCurrencyCodeNumber(currencyCodeNumber.equals("null") ? null : Integer.parseInt(currencyCodeNumber));
                    countryCd.setCurrencyName(currencyName);
                    countryCd.setExchangeRate(exchangeRate.equals("null") ? null : BigDecimal.valueOf(Double.parseDouble(exchangeRate)));
                    countryCd.setExchangeDate(new Date());
                    countryCd.setDateFormatPattern(dateFormatPattern);
                    countryCd.setTimeFormatPattern(timeFormatPattern);
                    countryCd.setTimestampFormatPattern(timestampFormatPattern);

                    result.add(countryCd);
                } else {
                    log.error("Missing token or too many tokens. Expecting 11, found " + cells.size());
                }
            }

        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            System.exit(1);
        }
        return result;
    }

    /**
     * Saves the countries if countries not already in db
     *
     * @param countries
     * @throws DataCreatorException
     */
    private void persist(List<Country> countries) throws DataCreatorException {
        List<Country> dbList = new ArrayList<Country>();
        try {

            for (Country country : countries) {
                CountryCd c = CountryCd.valueOf(country.getStatusCode());

                if (c != null) {
                    Country existingCountry = referenceManager.getCountry(c);

                    if (existingCountry != null) {
//                        if (log.isTraceEnabled()) {
//                            log.info("Country: " + country.getStatusCode() + " already exists");
//                        }
                        omitted++;
                    } else {
//                        if (log.isTraceEnabled()) {
//                            log.info("Persisting new country: " + country.getStatusCode());
//                        }
                        dbList.add(country);
                        populated++;
                    }
                } else {
                    log.error("Could not find enum: " + country.getStatusCode());
                    countries.remove(country);
                    omitted++;
                }
            }

            // ready fr save all
            if (dbList.size() > 0) {
                referenceManager.saveCountries(dbList);
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }


    // Spring IoC
    private Resource file;

    @Autowired
    private ReferenceManager referenceManager;

    @Required
    public void setFile(Resource sqlFile) {
        this.file = sqlFile;
    }
}
