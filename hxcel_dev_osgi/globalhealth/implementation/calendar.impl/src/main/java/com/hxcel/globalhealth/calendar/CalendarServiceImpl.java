package com.hxcel.globalhealth.calendar;

import com.hxcel.globalhealth.calendar.spec.CalendarService;
import com.hxcel.globalhealth.calendar.spec.CmpService;

/**
 * User: Bjorn Harvold
 * Date: Dec 30, 2008
 * Time: 2:13:26 PM
 * Responsibility:
 */
public class CalendarServiceImpl extends AbstractCalendarService implements CalendarService {

    @Override
    public CmpService getCmpService() {
        return cmpService;
    }

    // Spring IoC
    private CmpService cmpService;

    public void setCmpService(CmpService cmpService) {
        this.cmpService = cmpService;
    }
}
