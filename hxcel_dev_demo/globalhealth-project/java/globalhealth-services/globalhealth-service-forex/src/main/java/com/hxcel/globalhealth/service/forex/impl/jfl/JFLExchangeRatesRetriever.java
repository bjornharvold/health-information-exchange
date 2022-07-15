/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex.impl.jfl;

import com.hxcel.globalhealth.service.forex.ForexException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * <p>Title: JFLExchangeRatesRetriever</p>
 * <p/>
 * <p>Description: Updates all currency rates that have not been updated by the FedExchangeRatesRetriever.
 * We just check on the date to check if a currency has been updated</p>
 * <p/>
 *
 * @author Bjorn Harvold
 */
public class JFLExchangeRatesRetriever {
    private static final Logger log = LoggerFactory.getLogger(JFLExchangeRatesRetriever.class);

    /**
     * updates the rate from an external source
     *
     * @param currencyCode
     */
    public BigDecimal getRate(String currencyCode) {
        BigDecimal exchangeRate = new BigDecimal(-1);

        if (StringUtils.isNotBlank(currencyCode)) {
            JFLCurrencyConverter conv = JFLCurrencyConverter.getJFLCurrencyConverter();

            try {
                exchangeRate = conv.getExchangeRate(new BigDecimal(1), currencyCode, "USD");
            } catch (ForexException e) {
                log.error("Could not retrieve exchange rate for currencyCode: " + currencyCode, e);
            }
        }

        return exchangeRate;
    }
}
