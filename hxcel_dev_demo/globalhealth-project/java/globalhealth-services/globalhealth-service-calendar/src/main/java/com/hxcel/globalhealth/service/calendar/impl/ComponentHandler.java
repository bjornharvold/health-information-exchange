/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar.impl;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Element;

import java.util.List;

import com.hxcel.globalhealth.service.calendar.impl.PropertyHandler;

/**
 * User: bharvold
 * Date: Nov 2, 2006
 * Time: 6:45:17 PM
 * Description:
 */
public class ComponentHandler {
    private static final Logger log = LoggerFactory.getLogger(ComponentHandler.class);

    private static PropertyHandler propertyHandler = new PropertyHandler();
    
    public void printComponent(Component component) {
        log.info("+++++++++++++++++++++++++++++++++++++++++");
        log.info("Component: " + component.getClass());
        if (component instanceof VTimeZone) {
            printVTimeZone((VTimeZone)component);
        } else if (component instanceof VEvent) {
            printVEvent((VEvent)component);
        } else if (component instanceof VAlarm) {
            printVAlarm((VAlarm)component);
        } else if (component instanceof VFreeBusy) {
            printVFreeBusy((VFreeBusy)component);
        } else if (component instanceof VJournal) {
            printVJournal((VJournal)component);
        } else if (component instanceof VToDo) {
            printVToDo((VToDo)component);
        }
        log.info("+++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * Converts calendar component to xCal compliant XML
     * @param component
     */
    public Element toXCal(Element root, Component component) {
        if (log.isTraceEnabled()) {
            log.trace("Entering toXCal()");
            log.trace("XML before: " + root.asXML());
        }

        if (component instanceof VTimeZone) {
            xCalVTimeZone(root, (VTimeZone)component);
        } else if (component instanceof VEvent) {
            xCalVEvent(root, (VEvent)component);
        } else if (component instanceof VAlarm) {
            xCalVAlarm(root, (VAlarm)component);
        } else if (component instanceof VFreeBusy) {
            xCalVFreeBusy(root, (VFreeBusy)component);
        } else if (component instanceof VJournal) {
            xCalVJournal(root, (VJournal)component);
        } else if (component instanceof VToDo) {
            xCalVToDo(root, (VToDo)component);
        }

        if (log.isTraceEnabled()) {
            log.trace("Exiting toXCal()");
            log.trace("XML after: " + root.asXML());
        }

        return root;
    }

    /**
     * Creates the correct xml for an iCalendar VTimeZone
     * @param element xml entity
     * @param v iCal4j component
     * @return element same element that was passed in
     */
    private Element xCalVTimeZone(Element element, VTimeZone v) {
        if (log.isTraceEnabled()) {
            log.trace("Entering xCalVTimeZone()");
        }

        Element e = element.addElement("vtimezone");

        // add properties here

        if (log.isTraceEnabled()) {
            log.trace("Exiting xCalVTimeZone()");
        }

        return element;
    }

    /**
     * Creates the correct xml for an iCalendar VEvent
     * @param element xml entity
     * @param v iCal4j component
     * @return element same element that was passed in
     */
    private Element xCalVEvent(Element element, VEvent v) {
        if (log.isTraceEnabled()) {
            log.trace("Entering xCalVEvent()");
        }

        Element e = element.addElement("vevent");

        // handle valarms
        List alarms = v.getAlarms();
        for (Object o : alarms) {
            VAlarm alarm = (VAlarm) o;
            xCalVAlarm(e, alarm);
        }

        // add properties here
        propertyHandler.toXCalPropertyList(e, v.getProperties());

        if (log.isTraceEnabled()) {
            log.trace("Exiting xCalVEvent()");
        }

        return element;
    }

    /**
     * Creates the correct xml for an iCalendar VAlarm
     * @param element xml entity
     * @param v iCal4j component
     * @return element same element that was passed in
     */
    private Element xCalVAlarm(Element element, VAlarm v) {
        if (log.isTraceEnabled()) {
            log.trace("Entering xCalVAlarm()");
        }

        Element e = element.addElement("valarm");

        // add properties here
        propertyHandler.toXCalPropertyList(e, v.getProperties());

        if (log.isTraceEnabled()) {
            log.trace("Exiting xCalVAlarm()");
        }

        return element;
    }

    /**
     * Creates the correct xml for an iCalendar VFreeBusy
     * @param element xml entity
     * @param v iCal4j component
     * @return element same element that was passed in
     */
    private Element xCalVFreeBusy(Element element, VFreeBusy v) {
        if (log.isTraceEnabled()) {
            log.trace("Entering xCalVFreeBusy()");
        }

        Element e = element.addElement("vfreebusy");

        // add properties here
        propertyHandler.toXCalPropertyList(e, v.getProperties());

        if (log.isTraceEnabled()) {
            log.trace("Exiting xCalVFreeBusy()");
        }

        return element;
    }

    /**
     * Creates the correct xml for an icalendar VJournal
     * @param element xml entity
     * @param v iCal4j component
     * @return element same element that was passed in
     */
    private Element xCalVJournal(Element element, VJournal v) {
        if (log.isTraceEnabled()) {
            log.trace("Entering xCalVJournal()");
        }

        Element e = element.addElement("vjournal");

        // add properties here
        propertyHandler.toXCalPropertyList(e, v.getProperties());

        if (log.isTraceEnabled()) {
            log.trace("Exiting xCalVJournal()");
        }

        return element;
    }

    /**
     * Creates the correct xml for an icalendar VToDo
     * @param element xml entity
     * @param v iCal4j component
     * @return element same element that was passed in
     */
    private Element xCalVToDo(Element element, VToDo v) {
        if (log.isTraceEnabled()) {
            log.trace("Entering xCalVToDo()");
        }

        Element e = element.addElement("vtodo");

        // add properties here
        propertyHandler.toXCalPropertyList(e, v.getProperties());

        if (log.isTraceEnabled()) {
            log.trace("Exiting xCalVToDo()");
        }

        return element;
    }

    private void printVToDo(VToDo v) {

        log.info("====================== BEGIN VToDo ======================");

        log.info("Alarms:");
        List alarms = v.getAlarms();

        if (alarms != null) {
            for (Object alarm1 : alarms) {
                VAlarm alarm = (VAlarm) alarm1;
                printVAlarm(alarm);
            }
        }
        propertyHandler.printPropertyList(v.getProperties());

        log.info("====================== END   VToDo ======================\n\n");
    }

    private void printVJournal(VJournal v) {

        log.info("====================== BEGIN VJournal ======================");
        propertyHandler.printPropertyList(v.getProperties());
        log.info("====================== END VJournal ======================\n\n");

    }

    private void printVFreeBusy(VFreeBusy v) {

        log.info("====================== BEGIN VFreeBusy ======================");
        propertyHandler.printPropertyList(v.getProperties());
        log.info("====================== END VFreeBusy ======================\n\n");
    }

    private void printVAlarm(VAlarm v) {

        log.info("====================== BEGIN VAlarm ======================");
        propertyHandler.printPropertyList(v.getProperties());
        log.info("====================== END VAlarm ======================\n\n");
    }

    private void printVEvent(VEvent v) {
        log.info("====================== BEGIN VEvent ======================");
        log.info("Alarms:");
        List alarms = v.getAlarms();

        if (alarms != null) {
            for (Object alarm1 : alarms) {
                VAlarm alarm = (VAlarm) alarm1;
                printVAlarm(alarm);
            }
        }

        propertyHandler.printPropertyList(v.getProperties());
        log.info("====================== END VEvent ======================\n\n");
    }

    private void printVTimeZone(VTimeZone v) {
        log.info("====================== BEGIN VTimeZone ======================");

        List observances = v.getObservances();

        if (observances != null) {
            for (Object observance1 : observances) {
                Observance observance = (Observance) observance1;

                propertyHandler.printPropertyList(observance.getProperties());
//                List properties = observance.getProperties();
//                for (Object property1 : properties) {
//                    Property property = (Property) property1;
//
//                    log.info("    Property name: " + property.getName() + " -- Property value: " + property);
//                    ParameterList parameters = property.getParameters();
//
//                    for (Iterator iter = parameters.iterator(); iter.hasNext();) {
//                        Parameter parameter = (Parameter) iter.next();
//                        log.info("        Parameter name: " + parameter.getName() + " -- Parameter value: " + parameter);
//                    }
//                }
            }
        }
        propertyHandler.printPropertyList(v.getProperties());
        log.info("====================== END VTimeZone ======================\n\n");
    }


}
