/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar.impl;

import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import com.hxcel.globalhealth.service.calendar.XCal2ICalTransformer;

/**
 * @author Bjorn Harvold
 *         Transforms xCal XML to iCalendar ics file
 */
public class XCal2ICalTransformerImpl implements XCal2ICalTransformer {
    private static final Logger log = LoggerFactory.getLogger(XCal2ICalTransformerImpl.class);

    private TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private Transformer transformer = null;

    /**
     * Called once by Spring container
     */
    public void init() {
        // assign xsl transformation file
        try {
            transformer = transformerFactory.newTransformer(new StreamSource(xslTransformer.getInputStream()));
        } catch (TransformerConfigurationException e) {
            log.error("Could not configure XSL transformer", e);
        } catch (IOException e) {
            log.error("Could not find XSLT resource", e);
        }
    }

    /**
     * Transforms XML to iCalendar format
     * @param is
     * @return
     * @throws TransformerException
     */
    public String transform(InputStream is) throws TransformerException {
        StringWriter sw = new StringWriter();

        Source s = new StreamSource(is);
        Result r = new StreamResult(sw);
        // transform source
        transformer.transform(s, r);

        return sw.toString();
    }

    // Spring IoC
    private Resource xslTransformer = null;

    public void setXslTransformer(Resource xslTransformer) {
        this.xslTransformer = xslTransformer;
    }
}
