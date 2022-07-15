/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar.impl;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.*;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bharvold
 * Date: Nov 2, 2006
 * Time: 6:45:28 PM
 * Description:
 */
public class PropertyHandler {
    private static final Logger log = LoggerFactory.getLogger(PropertyHandler.class);

    private static ParameterHandler parameterHandler = new ParameterHandler();
    public void printPropertyList(PropertyList propertyList) {
        for (Object obj : propertyList) {
            Property property = (Property) obj;
            printProperty(property);
        }
    }

    public Element toXCalPropertyList(Element e, PropertyList propertyList) {
        for (Object obj : propertyList) {
            Property property = (Property) obj;
            toXCalProperty(e, property);
        }

        return e;
    }

    public void printProperty(Property property) {
        log.info("---------------------------------------------- BEGIN");
        log.info("Property: " + property.getClass());
        if (property instanceof Action) {
            printAction((Action)property);
        } else if (property instanceof Attach) {
            printAttach((Attach)property);
        } else if (property instanceof Attendee) {
            printAttendee((Attendee)property);
        } else if (property instanceof CalScale) {
            printCalScale((CalScale)property);
        } else if (property instanceof Categories) {
            printCategories((Categories)property);
        } else if (property instanceof Clazz) {
            printClazz((Clazz)property);
        } else if (property instanceof Comment) {
            printComment((Comment)property);
        } else if (property instanceof Completed) {
            printCompleted((Completed)property);
        } else if (property instanceof Contact) {
            printContact((Contact)property);
        } else if (property instanceof Created) {
            printCreated((Created)property);
        } else if (property instanceof Description) {
            printDescription((Description)property);
        } else if (property instanceof DtEnd) {
            printDtEnd((DtEnd)property);
        } else if (property instanceof DtStamp) {
            printDtStamp((DtStamp)property);
        } else if (property instanceof DtStart) {
            printDtStart((DtStart)property);
        } else if (property instanceof Due) {
            printDue((Due)property);
        } else if (property instanceof Duration) {
            printDuration((Duration)property);
        } else if (property instanceof ExDate) {
            printExDate((ExDate)property);
        } else if (property instanceof ExRule) {
            printExRule((ExRule)property);
        } else if (property instanceof FreeBusy) {
            printFreeBusy((FreeBusy)property);
        } else if (property instanceof Geo) {
            printGeo((Geo)property);
        } else if (property instanceof LastModified) {
            printLastModified((LastModified)property);
        } else if (property instanceof Location) {
            printLocation((Location)property);
        } else if (property instanceof Method) {
            printMethod((Method)property);
        } else if (property instanceof Organizer) {
            printOrganizer((Organizer)property);
        } else if (property instanceof PercentComplete) {
            printPercentComplete((PercentComplete)property);
        } else if (property instanceof Priority) {
            printPriority((Priority)property);
        } else if (property instanceof ProdId) {
            printProdId((ProdId)property);
        } else if (property instanceof RDate) {
            printRDate((RDate)property);
        } else if (property instanceof RecurrenceId) {
            printRecurrenceId((RecurrenceId)property);
        } else if (property instanceof RelatedTo) {
            printRelatedTo((RelatedTo)property);
        } else if (property instanceof Repeat) {
            printRepeat((Repeat)property);
        } else if (property instanceof RequestStatus) {
            printRequestStatus((RequestStatus)property);
        } else if (property instanceof Resources) {
            printResources((Resources)property);
        } else if (property instanceof RRule) {
            printRRule((RRule)property);
        } else if (property instanceof Sequence) {
            printSequence((Sequence)property);
        } else if (property instanceof Status) {
            printStatus((Status)property);
        } else if (property instanceof Summary) {
            printSummary((Summary)property);
        } else if (property instanceof Transp) {
            printTransp((Transp)property);
        } else if (property instanceof Trigger) {
            printTrigger((Trigger)property);
        } else if (property instanceof TzId) {
            printTzId((TzId)property);
        } else if (property instanceof TzName) {
            printTzName((TzName)property);
        } else if (property instanceof TzOffsetFrom) {
            printTzOffsetFrom((TzOffsetFrom)property);
        } else if (property instanceof TzOffsetTo) {
            printTzOffsetTo((TzOffsetTo)property);
        } else if (property instanceof TzUrl) {
            printTzUrl((TzUrl)property);
        } else if (property instanceof Uid) {
            printUid((Uid)property);
        } else if (property instanceof Url) {
            printUrl((Url)property);
        } else if (property instanceof Version) {
            printVersion((Version)property);
        } else if (property instanceof XProperty) {
            printXProperty((XProperty)property);
        }
        log.info("---------------------------------------------- END");
    }

    public Element toXCalProperty(Element e, Property property) {
        if (property instanceof Action) {
            toXCalAction(e, (Action)property);
        } else if (property instanceof Attach) {
            toXCalAttach(e, (Attach)property);
        } else if (property instanceof Attendee) {
            toXCalAttendee(e, (Attendee)property);
        } else if (property instanceof CalScale) {
            toXCalCalScale(e, (CalScale)property);
        } else if (property instanceof Categories) {
            toXCalCategories(e, (Categories)property);
        } else if (property instanceof Clazz) {
            toXCalClazz(e, (Clazz)property);
        } else if (property instanceof Comment) {
            toXCalComment(e, (Comment)property);
        } else if (property instanceof Completed) {
            toXCalCompleted(e, (Completed)property);
        } else if (property instanceof Contact) {
            toXCalContact(e, (Contact)property);
        } else if (property instanceof Created) {
            toXCalCreated(e, (Created)property);
        } else if (property instanceof Description) {
            toXCalDescription(e, (Description)property);
        } else if (property instanceof DtEnd) {
            toXCalDtEnd(e, (DtEnd)property);
        } else if (property instanceof DtStamp) {
            toXCalDtStamp(e, (DtStamp)property);
        } else if (property instanceof DtStart) {
            toXCalDtStart(e, (DtStart)property);
        } else if (property instanceof Due) {
            toXCalDue(e, (Due)property);
        } else if (property instanceof Duration) {
            toXCalDuration(e, (Duration)property);
        } else if (property instanceof ExDate) {
            toXCalExDate(e, (ExDate)property);
        } else if (property instanceof ExRule) {
            toXCalExRule(e, (ExRule)property);
        } else if (property instanceof FreeBusy) {
            toXCalFreeBusy(e, (FreeBusy)property);
        } else if (property instanceof Geo) {
            toXCalGeo(e, (Geo)property);
        } else if (property instanceof LastModified) {
            toXCalLastModified(e, (LastModified)property);
        } else if (property instanceof Location) {
            toXCalLocation(e, (Location)property);
        } else if (property instanceof Method) {
            toXCalMethod(e, (Method)property);
        } else if (property instanceof Organizer) {
            toXCalOrganizer(e, (Organizer)property);
        } else if (property instanceof PercentComplete) {
            toXCalPercentComplete(e, (PercentComplete)property);
        } else if (property instanceof Priority) {
            toXCalPriority(e, (Priority)property);
        } else if (property instanceof ProdId) {
            toXCalProdId(e, (ProdId)property);
        } else if (property instanceof RDate) {
            toXCalRDate(e, (RDate)property);
        } else if (property instanceof RecurrenceId) {
            toXCalRecurrenceId(e, (RecurrenceId)property);
        } else if (property instanceof RelatedTo) {
            toXCalRelatedTo(e, (RelatedTo)property);
        } else if (property instanceof Repeat) {
            toXCalRepeat(e, (Repeat)property);
        } else if (property instanceof RequestStatus) {
            toXCalRequestStatus(e, (RequestStatus)property);
        } else if (property instanceof Resources) {
            toXCalResources(e, (Resources)property);
        } else if (property instanceof RRule) {
            toXCalRRule(e, (RRule)property);
        } else if (property instanceof Sequence) {
            toXCalSequence(e, (Sequence)property);
        } else if (property instanceof Status) {
            toXCalStatus(e, (Status)property);
        } else if (property instanceof Summary) {
            toXCalSummary(e, (Summary)property);
        } else if (property instanceof Transp) {
            toXCalTransp(e, (Transp)property);
        } else if (property instanceof Trigger) {
            toXCalTrigger(e, (Trigger)property);
        } else if (property instanceof TzId) {
            toXCalTzId(e, (TzId)property);
        } else if (property instanceof TzName) {
            toXCalTzName(e, (TzName)property);
        } else if (property instanceof TzOffsetFrom) {
            toXCalTzOffsetFrom(e, (TzOffsetFrom)property);
        } else if (property instanceof TzOffsetTo) {
            toXCalTzOffsetTo(e, (TzOffsetTo)property);
        } else if (property instanceof TzUrl) {
            toXCalTzUrl(e, (TzUrl)property);
        } else if (property instanceof Uid) {
            toXCalUid(e, (Uid)property);
        } else if (property instanceof Url) {
            toXCalUrl(e, (Url)property);
        } else if (property instanceof Version) {
            toXCalVersion(e, (Version)property);
        } else if (property instanceof XProperty) {
            toXCalXProperty(e, (XProperty)property);
        }

        return e;
    }

    private void printAction(Action v) {
        log.info(v.toString());
    }
    private void printAttach(Attach v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printAttendee(Attendee v) {
        log.info(v.toString());
        log.info("calAddress: " + v.getCalAddress());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printCalScale(CalScale v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printCategories(Categories v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printClazz(Clazz v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printComment(Comment v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printCompleted(Completed v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printContact(Contact v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printCreated(Created v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printDescription(Description v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printDtEnd(DtEnd v) {
        log.info(v.toString());
        log.info("Date: " + v.getDate());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printDtStamp(DtStamp v) {
        log.info(v.toString());
        log.info("Date: " + v.getDate());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printDtStart(DtStart v) {
        log.info(v.toString());
        log.info("Date: " + v.getDate());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printDue(Due v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printDuration(Duration v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printExDate(ExDate v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printExRule(ExRule v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printFreeBusy(FreeBusy v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printGeo(Geo v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printLastModified(LastModified v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printLocation(Location v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printMethod(Method v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printOrganizer(Organizer v) {
        log.info(v.toString());
        log.info("calAddress: " + v.getCalAddress());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printPercentComplete(PercentComplete v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printPriority(Priority v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printProdId(ProdId v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printRDate(RDate v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printRecurrenceId(RecurrenceId v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printRelatedTo(RelatedTo v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printRepeat(Repeat v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printRequestStatus(RequestStatus v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printResources(Resources v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printRRule(RRule v) {
        log.info(v.toString());
        log.info("Frequency:" + v.getRecur().getFrequency());
        log.info("Count:" + v.getRecur().getCount());
        log.info("Daylist:" + v.getRecur().getDayList());
        log.info("Hourlist:" + v.getRecur().getHourList());
        log.info("Minutelist:" + v.getRecur().getMinuteList());
        log.info("MonthDaylist:" + v.getRecur().getMonthDayList());
        log.info("Monthlist:" + v.getRecur().getMonthList());
        log.info("Secondlist:" + v.getRecur().getSecondList());
        log.info("SetPoslist:" + v.getRecur().getSetPosList());
        log.info("Until:" + v.getRecur().getUntil());
        log.info("WeekNo:" + v.getRecur().getWeekNoList());
        log.info("Week start day:" + v.getRecur().getWeekStartDay());
        log.info("YearDaylist:" + v.getRecur().getYearDayList());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printSequence(Sequence v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printStatus(Status v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printSummary(Summary v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTransp(Transp v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTrigger(Trigger v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTzId(TzId v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTzName(TzName v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTzOffsetFrom(TzOffsetFrom v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTzOffsetTo(TzOffsetTo v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printTzUrl(TzUrl v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printUid(Uid v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printUrl(Url v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printVersion(Version v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }
    private void printXProperty(XProperty v) {
        log.info(v.toString());
        parameterHandler.printParameterList(v.getParameters());
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalAction(Element e, Action v) {
        Element prop = e.addElement("action");
        prop.addText(v.getValue());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalAttach(Element e, Attach v) {
        Element prop = e.addElement("attach");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalAttendee(Element e, Attendee v) {
        Element prop = e.addElement("attendee");
        prop.addText(v.getCalAddress().toString());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalCalScale(Element e, CalScale v) {
        Element prop = e.addElement("calscale");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalCategories(Element e, Categories v) {
        Element prop = e.addElement("categories");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalClazz(Element e, Clazz v) {
        Element prop = e.addElement("class");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalComment(Element e, Comment v) {
        Element prop = e.addElement("comment");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalCompleted(Element e, Completed v) {
        Element prop = e.addElement("completed");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalContact(Element e, Contact v) {
        Element prop = e.addElement("contact");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalCreated(Element e, Created v) {
        Element prop = e.addElement("created");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalDescription(Element e, Description v) {
        Element prop = e.addElement("description");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalDtEnd(Element e, DtEnd v) {
        Element prop = e.addElement("dtend");
        prop.addText(v.getDate().toString());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalDtStamp(Element e, DtStamp v) {
        Element prop = e.addElement("dtstamp");
        prop.addText(v.getDate().toString());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalDtStart(Element e, DtStart v) {
        Element prop = e.addElement("dtstart");
        prop.addText(v.getDate().toString());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalDue(Element e, Due v) {
        Element prop = e.addElement("due");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalDuration(Element e, Duration v) {
        Element prop = e.addElement("duration");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalExDate(Element e, ExDate v) {
        Element prop = e.addElement("exdate");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalExRule(Element e, ExRule v) {
        Element prop = e.addElement("exrule");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalFreeBusy(Element e, FreeBusy v) {
        Element prop = e.addElement("freebusy");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalGeo(Element e, Geo v) {
        Element prop = e.addElement("geo");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalLastModified(Element e, LastModified v) {
        Element prop = e.addElement("last-modified");
        prop.addText(v.getDate().toString());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalLocation(Element e, Location v) {
        Element prop = e.addElement("location");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalMethod(Element e, Method v) {
        Element prop = e.addElement("method");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalOrganizer(Element e, Organizer v) {
        Element prop = e.addElement("organizer");
        prop.addText(v.getCalAddress().toString());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalPercentComplete(Element e, PercentComplete v) {
        Element prop = e.addElement("percent-complete");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalPriority(Element e, Priority v) {
        Element prop = e.addElement("priority");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalProdId(Element e, ProdId v) {
        Element prop = e.addElement("prodid");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     * TODO periodlist might have to be implemented here as well
     */
    private Element toXCalRDate(Element e, RDate v) {
        Element prop = e.addElement("rdate");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalRecurrenceId(Element e, RecurrenceId v) {
        Element prop = e.addElement("recurrence-id");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalRelatedTo(Element e, RelatedTo v) {
        Element prop = e.addElement("related-to");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalRepeat(Element e, Repeat v) {
        Element prop = e.addElement("repeat");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalRequestStatus(Element e, RequestStatus v) {
        Element prop = e.addElement("status");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     * TODO ResourceList might need to get added here
     */
    private Element toXCalResources(Element e, Resources v) {
        Element prop = e.addElement("resources");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalRRule(Element e, RRule v) {
        Element prop = e.addElement("rrule");
        prop.addText(v.getValue());
//        log.info("Frequency:" + v.getRecur().getFrequency());
//        log.info("Count:" + v.getRecur().getCount());
//        log.info("Daylist:" + v.getRecur().getDayList());
//        log.info("Hourlist:" + v.getRecur().getHourList());
//        log.info("Minutelist:" + v.getRecur().getMinuteList());
//        log.info("MonthDaylist:" + v.getRecur().getMonthDayList());
//        log.info("Monthlist:" + v.getRecur().getMonthList());
//        log.info("Secondlist:" + v.getRecur().getSecondList());
//        log.info("SetPoslist:" + v.getRecur().getSetPosList());
//        log.info("Until:" + v.getRecur().getUntil());
//        log.info("WeekNo:" + v.getRecur().getWeekNoList());
//        log.info("Week start day:" + v.getRecur().getWeekStartDay());
//        log.info("YearDaylist:" + v.getRecur().getYearDayList());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalSequence(Element e, Sequence v) {
        Element prop = e.addElement("sequence");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalStatus(Element e, Status v) {
        Element prop = e.addElement("status");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalSummary(Element e, Summary v) {
        Element prop = e.addElement("summary");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTransp(Element e, Transp v) {
        Element prop = e.addElement("transp");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTrigger(Element e, Trigger v) {
        Element prop = e.addElement("trigger");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTzId(Element e, TzId v) {
        Element prop = e.addElement("tzid");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTzName(Element e, TzName v) {
        Element prop = e.addElement("tzname");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTzOffsetFrom(Element e, TzOffsetFrom v) {
        Element prop = e.addElement("tzoffsetfrom");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTzOffsetTo(Element e, TzOffsetTo v) {
        Element prop = e.addElement("tzoffsetto");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalTzUrl(Element e, TzUrl v) {
        Element prop = e.addElement("tzurl");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalUid(Element e, Uid v) {
        Element prop = e.addElement("uid");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());


        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalUrl(Element e, Url v) {
        Element prop = e.addElement("url");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalVersion(Element e, Version v) {
        Element prop = e.addElement("version");
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }

    /**
     * Creates property xml for iCalendar components
     * @param e xml element
     * @param v iCal4j property
     * @return xml element
     */
    private Element toXCalXProperty(Element e, XProperty v) {
        Element prop = e.addElement("x-"+v.getName().toLowerCase());
        prop.addText(v.getValue());
        parameterHandler.toXCalParameterList(prop, v.getParameters());

        return e;
    }
}
