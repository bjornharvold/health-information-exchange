package com.hxcel.globalhealth.calendar.spec;

/**
 * User: Bjorn Harvold
 * Date: Dec 26, 2008
 * Time: 5:08:53 PM
 * Responsibility:
 */
public class CalendarException extends Exception {
    String[] params = null;

    public CalendarException() {
        super();
    }

    public CalendarException(String s) {
        super(s);
    }

    public CalendarException(String s, String ... params) {
        super(s);
    }

    public CalendarException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CalendarException(String s, Throwable throwable, String ... params) {
        super(s, throwable);
    }

    public CalendarException(Throwable throwable) {
        super(throwable);
    }
}
