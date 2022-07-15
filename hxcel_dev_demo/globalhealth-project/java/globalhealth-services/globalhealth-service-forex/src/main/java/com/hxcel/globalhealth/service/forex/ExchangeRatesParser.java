/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.forex;

import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Nov 28, 2005
 * Time: 6:49:55 PM

 * <p/>
 * Description:
 */
public interface ExchangeRatesParser {
    Map<String, Rate> parse(String xml) throws ForexException;
}
