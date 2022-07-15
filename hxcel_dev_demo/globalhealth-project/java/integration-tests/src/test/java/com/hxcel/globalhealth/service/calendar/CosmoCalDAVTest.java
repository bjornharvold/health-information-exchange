/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.service.calendar.AbstractCalendarBaseTest;

/**
 * User: Bjorn Harvold
 * Date: Nov 12, 2006
 * Time: 3:47:51 PM
 */
public class CosmoCalDAVTest extends AbstractCalendarBaseTest {
    private final static Logger log = LoggerFactory.getLogger(CosmoCalDAVTest.class);

    public void testTrue() {
        assertTrue(true);
    }
//    public void testMakeCalendar() {
//        try {
//            log.info("Creating calendar collection");
//            // passing null for calendarName will just create default calendar
//            manager.createCalendarCollection("bjorn", "lactose8", null);
//
//            log.info("Removing calendar collection");
//            // now remove it since it is only a test
//            manager.removeCalendarCollection("bjorn", "lactose8", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            assertTrue(false);
//        }
//    }
//
//    public void testMakeCalendarEvent() {
//        try {
//            // passing null for calendarName will just create default calendar
//            Resource resource = new ClassPathResource("testIcs.ics");
//
//            log.info("Creating calendar collection");
//            // passing null for calendarName will just create default calendar
//            manager.createCalendarCollection("bjorn", "lactose8", null);
//
//            if (resource.exists()) {
//                log.info("Creating calendar event");
//                // creating calendar item
//                CalendarBuilder builder = new CalendarBuilder();
//                Calendar calendar = builder.build(resource.getInputStream());
//                VEvent event = ICalendarUtils.getFirstEvent(calendar);
//                String uid0 = ICalendarUtils.getUIDValue(event);
//
//                String uid1 = manager.createVCalendar("bjorn", "lactose8", null, calendar);
//
//                // get calendar object back
//                calendar = manager.getVCalendar("bjorn", null, uid1);
//                event = ICalendarUtils.getFirstEvent(calendar);
//                String uid2 = ICalendarUtils.getUIDValue(event);
//                assertEquals(uid0, uid2);
//
//                // remove event
//                log.info("Removing calendar event");
//                manager.removeVCalendar("bjorn", null, uid1);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            assertTrue(false);
//        } finally {
//            log.info("Removing calendar collection");
//            // now remove it since it is only a test
//            try {
//                manager.removeCalendarCollection("bjorn", "lactose8", null);
//            } catch (CosmoException e) {
//                e.printStackTrace();
//                assertTrue(false);
//            }
//        }
//    }
//
//    public void testGetEvents() {
//        try {
//            // passing null for calendarName will just create default calendar
//            Resource resource = new ClassPathResource("testIcs.ics");
//
//            log.info("Creating calendar collection");
//            // passing null for calendarName will just create default calendar
//            manager.createCalendarCollection("bjorn", "lactose8", null);
//
//            if (resource.exists()) {
//                log.info("Creating calendar events");
//                // creating calendar item
//                CalendarBuilder builder = new CalendarBuilder();
//                Calendar calendar = builder.build(resource.getInputStream());
//
//                VEvent event = ICalendarUtils.getFirstEvent(calendar);
//                String uid0 = ICalendarUtils.getUIDValue(event);
//
//                String uid1 = manager.createVCalendar("bjorn", "lactose8", null, calendar);
//
//                // start date 1-1-2004
//                Date beginDate = ICalendarUtils.createDateTime(2006, 0, 1, 1, 0, 0, 0, null, true);
//                Date endDate = ICalendarUtils.createDateTime(2007, 0, 1, 1, 0, 0, 0, null, true);
//
//                List<Calendar> result = manager.getVCalendars("bjorn", "lactose8", null, beginDate, endDate);
//
//                for (Calendar cal : result) {
//                    log.error(cal.toString());
//                }
//                // remove event
//                log.info("Removing calendar event");
//                manager.removeVCalendar("bjorn", null, uid1);
//            }
//
//        } catch (Exception e) {
//            e.getCause().printStackTrace();
//            assertTrue(false);
//        } finally {
//            log.info("Removing calendar collection");
//            // now remove it since it is only a test
//            try {
//                manager.removeCalendarCollection("bjorn", "lactose8", null);
//            } catch (CosmoException e) {
//                e.printStackTrace();
//                assertTrue(false);
//            }
//        }
//    }

    // Spring IoC
//    private CosmoCalDAVManager manager;
//
//    public void setManager(CosmoCalDAVManager manager) {
//        this.manager = manager;
//    }

}
