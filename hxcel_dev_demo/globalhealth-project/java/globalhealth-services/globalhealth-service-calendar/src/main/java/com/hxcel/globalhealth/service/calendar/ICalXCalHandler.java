/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import net.fortuna.ical4j.model.Calendar;
import org.dom4j.Document;

import java.io.InputStream;

import com.hxcel.globalhealth.service.calendar.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Nov 6, 2006
 * Time: 12:49:29 PM
 */
public interface ICalXCalHandler {
    Document toXCal(InputStream is) throws CalendarException;
    Calendar toICS(InputStream is) throws CalendarException;
}
