/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import javax.xml.transform.TransformerException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Nov 6, 2006
 * Time: 1:11:17 PM
 */
public interface XCal2ICalTransformer {
    String transform(InputStream is) throws TransformerException;
}
