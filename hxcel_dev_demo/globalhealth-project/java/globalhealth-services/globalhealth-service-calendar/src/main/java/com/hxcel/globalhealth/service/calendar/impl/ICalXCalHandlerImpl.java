/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar.impl;

import com.hxcel.globalhealth.service.calendar.impl.ComponentHandler;
import com.hxcel.globalhealth.service.calendar.ICalXCalHandler;
import com.hxcel.globalhealth.service.calendar.CalendarException;
import com.hxcel.globalhealth.service.calendar.XCal2ICalTransformer;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

/**
 * User: bharvold
 * Date: Nov 2, 2006
 * Time: 3:39:46 PM
 * Description:
 */
public class ICalXCalHandlerImpl implements ICalXCalHandler {
    private ComponentHandler componentHandler = new ComponentHandler();
    private CalendarBuilder builder = new CalendarBuilder();

    /**
     * Converts an ics Calendar file to xCal compliant XML
     *
     * @param is
     * @return xml document
     */
    public Document toXCal(InputStream is) throws CalendarException {
        Document iCalendar = null;

        try {
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(is);

            iCalendar = DocumentHelper.createDocument();
            Element root = iCalendar.addElement("iCalendar");     // Create Root Element

            List components = calendar.getComponents();

            for (Object component1 : components) {
                Component component = (Component) component1;
                componentHandler.toXCal(root, component);
            }
        } catch (IOException e) {
            throw new CalendarException("Could not create Calendar object", e);
        } catch (ParserException e) {
            throw new CalendarException("Could not parse iCalendar information", e);
        }
        return iCalendar;
    }

    /**
     * Converts an XML xCal document to an iCal4j Calendar object. Uses an XSL transformer
     *
     * @param is
     * @return
     * @throws CalendarException
     */
    public Calendar toICS(InputStream is) throws CalendarException {
        Calendar calendar = null;

        try {
            String ics = xCalTransformer.transform(is);
            StringReader reader = new StringReader(ics);
            calendar = builder.build(reader);
            is.close();
        } catch (TransformerException e) {
            throw new CalendarException("Could not transform xCal to iCal", e);
        } catch (IOException e) {
            throw new CalendarException("Could not create Calendar object", e);
        } catch (ParserException e) {
            throw new CalendarException("Could not parse iCalendar information", e);
        }

        return calendar;
    }

    // Spring IoC
    private XCal2ICalTransformer xCalTransformer = null;

    public void setXCal2ICalTransformer(XCal2ICalTransformer xCalTransformer) {
        this.xCalTransformer = xCalTransformer;
    }
}
