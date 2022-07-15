package com.hxcel.globalhealth.calendar.spec;

import java.util.Date;
import java.net.URL;

/**
 * User: Bjorn Harvold
 * Date: Dec 27, 2008
 * Time: 3:53:08 PM
 * Responsibility:
 */
public interface CalendarUser {
    String getUsername();
    String getFirstName();
    String getLastName();
    String getEmail();
    Date getCreated();
    Date getModified();
    Boolean getAdministrator();
    URL getUrl();
    String getHomedirUrl();
}
