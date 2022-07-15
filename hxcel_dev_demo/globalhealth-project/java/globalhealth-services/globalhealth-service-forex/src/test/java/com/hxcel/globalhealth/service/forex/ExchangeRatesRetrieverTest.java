/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex;

import com.hxcel.globalhealth.service.forex.impl.fed.FedExchangeRatesRetriever;
import com.hxcel.globalhealth.service.forex.impl.jfl.JFLExchangeRatesRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Jul 2, 2006
 * Time: 9:39:15 PM
 */
public class ExchangeRatesRetrieverTest extends ForexBaseTest {
    private static final Logger log = LoggerFactory.getLogger(ExchangeRatesRetrieverTest.class);

    public void testFedExchangeRatesRetriever() {
        try {
            log.info("Retrieving exchange rates using FedExchangeRatesRetriever.");

            Map<String, Rate> rates = fedExchangeRatesRetriever.getRates();

            if (rates != null && !rates.isEmpty()) {
                log.info("Rates retrieved successfully");
                assertTrue(true);
            }

        } catch (ForexException e) {
            log.error("Error retrieving rates from the fed: \n", e);
            assertTrue(false);
        }
    }

    public void testJFLExchangeRatesRetriever() {
        log.info("Retrieving exchange rates using JFLExchangeRatesRetriever.");

        BigDecimal result = jflExchangeRatesRetriever.getRate("CAD");

        if (result != null && result.intValue() > 0) {
            log.info("Rate retrieved successfully");
            assertTrue(true);
        }

    }

    // Spring IoC
    private JFLExchangeRatesRetriever jflExchangeRatesRetriever;
    private FedExchangeRatesRetriever fedExchangeRatesRetriever;

    public void setJflExchangeRatesRetriever(JFLExchangeRatesRetriever jflExchangeRatesRetriever) {
        this.jflExchangeRatesRetriever = jflExchangeRatesRetriever;
    }

    public void setFedExchangeRatesRetriever(FedExchangeRatesRetriever fedExchangeRatesRetriever) {
        this.fedExchangeRatesRetriever = fedExchangeRatesRetriever;
    }
}
