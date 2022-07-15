/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex.impl.fed;

import com.hxcel.globalhealth.service.forex.ExchangeRatesParser;
import com.hxcel.globalhealth.service.forex.ForexException;
import com.hxcel.globalhealth.service.forex.Rate;
import com.hxcel.globalhealth.service.forex.impl.RateImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: FedExchangeRatesParser</p>
 * <p> Description: Parses federal</p>
 * <p/>
 *
 * @author Bjorn Harvold,
 */
public class FedExchangeRatesParser implements ExchangeRatesParser {
    /**
     * log4j logging mechanism
     */
    private static final Logger log = LoggerFactory.getLogger(FedExchangeRatesParser.class);


    /**
     * Creates RateImpl objects which it returns in a hash with the rate id as the key
     *
     * @param aNode - aNode
     * @return ConcurrentHashMap
     */
    private Rate makeRate(Node aNode) throws ForexException {
        Rate rate = null;

        try {
            if (aNode != null) {
                rate = new RateImpl();

                String currency = aNode.valueOf("frbny:Key/frbny:CURR");

                // set date
                rate.setDate(aNode.valueOf("frbny:Obs/frbny:TIME_PERIOD").trim());
                rate.setRate(new BigDecimal(aNode.valueOf("frbny:Obs/frbny:OBS_VALUE").trim()));

                // if in usd convert value to usd
                if (currency.equals("USD")) {
                    rate.convert();
                    rate.setCurrency(aNode.valueOf("@UNIT"));
                }
                else {
                    rate.setCurrency(currency);
                }

                if (log.isDebugEnabled()) {
                    log.debug("RateImpl date: " + rate.getDate());
                    log.debug("RateImpl value: " + rate.getRate());
                    log.debug("Currency value: " + rate.getCurrency());
                }
            }
        }

        catch (NullPointerException npe) {
            log.error(npe.getMessage());
            throw new ForexException("Could not find information on dataobject", npe);
        }

        return rate;
    }

    /**
     * Is given a string from the Fed's Web Service from which to make the RateImpl objects
     *
     * @param xml - String of xml to init
     * @return Map<String, Rate>
     * @throws ForexException
     */
    public Map<String, Rate> parse(String xml) throws ForexException {
        Map<String, Rate> result = new ConcurrentHashMap<String, Rate>();
        Rate rate;

        try {
            log.info("Parsing XML data: Starting");
            SAXReader reader = new SAXReader();
            Document document = reader.read(new StringReader(xml));

            // get all rates
            List<Node> nodes = document.selectNodes("//UtilityData/frbny:DataSet/frbny:Series");
            if (nodes != null && nodes.size() > 0) {
                // looping through all tags named dataobject
                for (Node node : nodes) {
                    if (log.isDebugEnabled()) {
                        log.debug("Creating RateImpl for node: " + node.toString());
                    }
                    rate = makeRate(node);
                    result.put(rate.getCurrency(), rate);
                }
            }

            log.info("Parsing XML: complete");

        }
        catch (DocumentException de) {
            log.error("Couldn't find file: \n\n" + de);
            throw new ForexException("IO Exception. Could not find file?", de);
        }

        return result;
    }
}
