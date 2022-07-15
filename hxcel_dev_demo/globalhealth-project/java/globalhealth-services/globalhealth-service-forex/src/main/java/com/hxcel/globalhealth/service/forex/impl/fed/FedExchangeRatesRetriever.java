/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex.impl.fed;

import com.hxcel.globalhealth.service.forex.ExchangeRatesParser;
import com.hxcel.globalhealth.service.forex.ForexException;
import com.hxcel.globalhealth.service.forex.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * <p>Title: FedExchangeRatesRetriever</p>
 * <p/>
 * <p>Description: Knows how to get the exchange rates from Feds Web Service:
 * </p>
 *
 * @author Bjorn Harvold
 */
public class FedExchangeRatesRetriever {
    private static final Logger log = LoggerFactory.getLogger(FedExchangeRatesRetriever.class);

    /**
     * retreives exchange rates data and updates the db
     *
     */
    public Map<String, Rate> getRates() throws ForexException {
        Map<String, Rate> result = null;
/*
        try {
            log.info("Contacting FBNY using their Web Service");
            // grab onto axis's service


            FXWSService service = new FXWSServiceLocator();
            FXWS fxws = service.getFXWSCfc();

            // get the latest exchange rates
            String xml = fxws.getAllLatestNoonRates();

            if (log.isDebugEnabled()) {
                System.out.println("Result from Web Service call to Fed:\n\n" + xml);
            }

            // init xml
            log.info("Updating exchange rates from the Fed. Populating object model with Fed XML.");
            result = exchangeRatesParser.parse(xml);

        } catch (ForexException ex) {
            log.error("ServiceException while talking to the Fed: " + ex.getMessage());
            throw new ForexException("exception.service");
        } catch (RemoteException ex) {
            log.error("RemoteException while talking to the Fed: " + ex.getMessage());
            throw new ForexException("exception.remote");
        }
*/
        return result;
    }

    // Spring IoC
    @Autowired
    private ExchangeRatesParser exchangeRatesParser = null;
}
