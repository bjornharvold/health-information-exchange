/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex;

import java.math.BigDecimal;

/**
 * User: Bjorn Harvold
 * Date: Nov 28, 2005
 * Time: 6:51:30 PM

 * <p/>
 * Description:
 */
public interface Rate {
    void convert();

    void setRate(BigDecimal rate);

    void setDate(String date);

    BigDecimal getRate();

    String getDate();

    String getCurrency();

    void setCurrency(String currency);

    String toString();
}
