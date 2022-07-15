/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex.impl;


import com.hxcel.globalhealth.service.forex.Rate;

import java.math.BigDecimal;

/**
 * <p>Title: </p>
 * <p/>
 * <p>Description: </p>
 * <p/>

 * <p/>

 *
 * @author Bjorn Harvold

 */
public class RateImpl implements Rate {
    private BigDecimal rate;
    private String date;
    private String currency;

    /**
     * any non usd value gets converted to a usd value
     */
    public void convert() {
        double d = getRate().doubleValue();
        d = 1 / d;
        setRate(new BigDecimal(d));
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(date);
        sb.append(" : ");
        sb.append(currency);
        sb.append(" : ");
        sb.append(rate);

        return sb.toString();
    }
}
