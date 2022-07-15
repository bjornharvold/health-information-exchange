package com.hxcel.globalhealth.calendar.spec;

/**
 * User: Bjorn Harvold
 * Date: Dec 30, 2008
 * Time: 2:12:48 PM
 * Responsibility:
 */
public interface CalendarService {
    Boolean isAvailable();
    
    CmpService getCmpService();
}
