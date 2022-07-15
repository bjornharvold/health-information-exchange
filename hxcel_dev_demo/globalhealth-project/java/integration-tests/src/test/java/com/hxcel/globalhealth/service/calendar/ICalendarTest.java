/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import com.hxcel.globalhealth.service.calendar.impl.ComponentHandler;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Nov 6, 2006
 * Time: 2:55:58 PM
 */
public class ICalendarTest extends AbstractCalendarBaseTest {
    private static final Logger log = LoggerFactory.getLogger(ICalendarTest.class);
    private ComponentHandler componentHandler = new ComponentHandler();
    private CalendarBuilder builder = new CalendarBuilder();
    private Resource icsFile = new ClassPathResource("testIcs.ics");
    private Resource xcalFile = new ClassPathResource("testXCal.xml");

    @Test
    public void testTrue() {
        assertTrue(true);
    }
    /*
    public void testXCAL2ICS() {
        try {
            // hand document over to handler
            ICalXCalHandler handler = getManager();

            BufferedReader isr = new BufferedReader(new InputStreamReader(xcalFile.getInputStream()));
            String line;
            while ((line = isr.readLine()) != null) {
                log.error(line);
            }

            Calendar calendar = handler.toICS(xcalFile.getInputStream());

            assertNotNull(calendar);

            List components = calendar.getComponents();

            for (Object component1 : components) {
                Component component = (Component) component1;

                System.out.println("Component name: " + component.getName());

                componentHandler.printComponent(component);
            }
        } catch (FileNotFoundException e) {
            log.error("Error in testXCal2ICS", e);
            assertTrue(false);
        } catch (IOException e) {
            log.error("Error in testXCal2ICS", e);
            assertTrue(false);
        } catch (CalendarException e) {
            log.error("Error in testXCal2ICS", e);
            assertTrue(false);
        }

    }

    public void testICS2XCal() {
        try {
            Document document = getManager().toXCal(icsFile.getInputStream());

            assertNotNull(document);

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.write(document);
        } catch (IOException e) {
            log.error("Error in testICS2XCal", e);
            assertTrue(false);
        } catch (CalendarException e) {
            log.error("Error in testXCal2ICS", e);
            assertTrue(false);
        }
    }

    // Spring IoC
    public ICalXCalHandler getManager() {
        return (ICalXCalHandler) applicationContext.getBean("iCalXCalManager");
    }
    */
}
