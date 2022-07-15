/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex.impl.jfl;

import com.hxcel.globalhealth.service.forex.ForexException;
import net.neurotech.currency.ConversionException;
import net.neurotech.currency.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author bjorn
 */
public class JFLCurrencyConverter {
    private static final Logger log = LoggerFactory.getLogger(JFLCurrencyConverter.class);
    private static JFLCurrencyConverter _inst = null;
    private static Object _lock = JFLCurrencyConverter.class;
    private Converter _converter = null;

    private String _class = "net.neurotech.currency.OandaSource"; // other option net.neurotech.currency.YahooParser
    private String _from = null;
    private String _to = null;

    /**
     * Gets the JFLCurrencyConverter attribute of the JFLCurrencyConverter
     * class
     *
     * @return The JFLCurrencyConverter value
     */
    public static JFLCurrencyConverter getJFLCurrencyConverter() {
        if (_inst == null) {
            synchronized (_lock) {
                if (_inst == null) {
                    _inst = new JFLCurrencyConverter();
                }
            }
        }
        return _inst;
    }

    /**
     * The main program for the JFLCurrencyConverter class
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        try {
            JFLCurrencyConverter.getJFLCurrencyConverter().setParserClass("net.neurotech.currency.YahooParser");
            JFLCurrencyConverter conv = JFLCurrencyConverter.getJFLCurrencyConverter();

            System.out.println("Result: " + conv.getExchangeRate(new BigDecimal(518), "USD", "CAD"));

        }
        catch (ForexException ce) {
            System.out.println("Error converting currency! Check connection??? " + ce.getMessage());
        }

    }

    /**
     * Sets the ParserClass attribute of the JFLCurrencyConverter object
     *
     * @param inst The new ParserClass value
     */
    public void setParserClass(String inst) {
        _class = inst;
    }

    /**
     * Sets the FromCurrency attribute of the JFLCurrencyConverter object
     *
     * @param cur The new FromCurrency value
     */
    public void setFromCurrency(String cur) {
        _from = cur;
    }

    /**
     * Sets the ToCurrency attribute of the JFLCurrencyConverter object
     *
     * @param cur The new ToCurrency value
     */
    public void setToCurrency(String cur) {
        _to = cur;
    }

    /**
     * Gets the Price attribute of the JFLCurrencyConverter object
     *
     * @param price Description of Parameter
     * @param to    Description of Parameter
     * @param from  Description of Parameter
     * @return The Price value
     * @throws com.hxcel.globalhealth.service.forex.ForexException
     */
    public BigDecimal getExchangeRate(BigDecimal price, String to, String from) throws ForexException {
        BigDecimal result = null;
        _converter = new Converter(_class);

        try {
            if (price.doubleValue() > 0 && to != null && from != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Converting: " + price + " from " + from + " ==> to " + to);
                }
                result = new BigDecimal(_converter.convert(price.floatValue(), from, to));
                if (log.isDebugEnabled()) {
                    log.debug(price + " " + from + " is " + result + " " + to);
                }
            }
        }
        catch (NumberFormatException ex) {
            log.info("Could not rertieve exchange rate from: " + to);
            log.info("This currency will not be updated");
        }
        catch (ConversionException ce) {
            log.info("Could not convert: " + price + ", To: " + to + ", From: " + from);
            log.info(ce.getMessage());
            //ce.printStackTrace();
            throw new ForexException("Could not convert: " + price + ", To: " + to + ", From: " + from + " : " +
                    ce.getMessage());

        }
        if (result != null && result.doubleValue() == -1) {
            throw new ForexException("Could not convert: " + price + ", To: " + to + ", From: " + from);
        }
        return result;
    }

    /**
     * Gets the Price attribute of the JFLCurrencyConverter object
     *
     * @param price Description of Parameter
     * @return The Price value
     * @throws com.hxcel.globalhealth.service.forex.ForexException
     */
    public BigDecimal getExchangeRate(BigDecimal price) throws ForexException {
        return getExchangeRate(price, _to, _from);
    }
}
