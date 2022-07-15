/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.webdav.lib.util.WebdavStatus;
import org.osaf.caldav4j.CalDAV4JException;
import org.osaf.caldav4j.CalDAV4JProtocolException;
import org.osaf.caldav4j.CalDAVCalendarCollection;
import org.osaf.caldav4j.methods.CalDAV4JMethodFactory;
import org.osaf.caldav4j.methods.GetMethod;
import org.osaf.caldav4j.methods.MkCalendarMethod;
import org.osaf.caldav4j.methods.PutMethod;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * User: Bjorn Harvold
 * Date: Nov 12, 2006
 * Time: 10:22:31 AM
 */
public class CosmoCalDAVManager extends AbstractCosmoManager {
    private final static Logger log = LoggerFactory.getLogger(CosmoCalDAVManager.class);

    /**
     * Creates a calendar (directory to put ics files) for a specific user
     *
     * @throws CosmoException
     */
    public void createCalendarCollection(String username, String password, String collectionName) throws CosmoException {
        MkCalendarMethod method = null;
        if (log.isTraceEnabled()) {
            log.trace("Entering createCalendarCollection");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }
        if (StringUtils.isBlank(password)) {
            throw new CosmoException("error.missing.argument.exception", "password");
        }

        try {
            HttpClient httpClient = createHttpClient(username, password);

            method = calDAV4JMethodFactory.createMkCalendarMethod();
            StringBuffer sb = new StringBuffer(defaultBasePath);
            sb.append("/");
            sb.append(username);
            sb.append("/");
            if (StringUtils.isBlank(collectionName)) {
                sb.append(defaultCollection);
            } else {
                sb.append(collectionName);
            }

            method.setPath(sb.toString());

            int result = httpClient.executeMethod(method);

            if (result > 399) {
                throw new CosmoException("error.cosmo.server", method.getResponseBodyAsString());
            }
        } catch (HttpException ex) {
            log.error("Http exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex.getMessage());
        } catch (IOException ex) {
            log.error("IO exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex.getMessage());
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("Exiting createCalendarCollection");
        }
    }

    /**
     * Removes a calendar collection (directory) for the specified user
     *
     * @param username
     * @param password
     * @param collectionName
     * @throws CosmoException
     */
    public void removeCalendarCollection(String username, String password, String collectionName) throws CosmoException {
        DeleteMethod method = null;

        if (log.isTraceEnabled()) {
            log.trace("Entering removeCalendarCollection");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }
        if (StringUtils.isBlank(password)) {
            throw new CosmoException("error.missing.argument.exception", "password");
        }

        try {
            HttpClient httpClient = createHttpClient(username, password);

            method = new DeleteMethod();
            StringBuffer sb = new StringBuffer(defaultBasePath);
            sb.append("/");
            sb.append(username);
            sb.append("/");
            if (StringUtils.isBlank(collectionName)) {
                sb.append(defaultCollection);
            } else {
                sb.append(collectionName);
            }

            method.setPath(sb.toString());

            int result = httpClient.executeMethod(method);

            if (result > 399) {
                throw new CosmoException("error.cosmo.server", method.getResponseBodyAsString());
            }
        } catch (HttpException ex) {
            log.error("Http exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } catch (IOException ex) {
            log.error("IO exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("Exiting removeCalendarCollection");
        }
    }

    /**
     * Puts a VCALENDAR object into the specified calendar directory for the specified user
     * @param username
     * @param password
     * @param collectionName
     * @param calendar
     * @return
     * @throws CosmoException
     */
    public String createVCalendar(String username, String password, String collectionName, Calendar calendar) throws CosmoException {
        String result = null;
        PutMethod method = null;

        if (log.isTraceEnabled()) {
            log.trace("Entering createVCalendar");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }
        if (StringUtils.isBlank(password)) {
            throw new CosmoException("error.missing.argument.exception", "password");
        }
        if (calendar == null) {
            throw new CosmoException("error.missing.argument.exception", "calendar");
        }

        try {
            HttpClient httpClient = createHttpClient(username, password);
            method = calDAV4JMethodFactory.createPutMethod();
            // set a bunch of props on the method that is unique to this PutMethod
            method.setIfNoneMatch(true);
            method.setAllEtags(true);
            method.setRequestBody(calendar);

            StringBuffer sb = new StringBuffer(defaultBasePath);
            sb.append("/");
            sb.append(username);
            sb.append("/");
            if (StringUtils.isBlank(collectionName)) {
                sb.append(defaultCollection);
            } else {
                sb.append(collectionName);
            }
            sb.append("/");
            // generate a uid for the event
            result = UUID.randomUUID().toString();
            sb.append(result);
            // append with ics file type
            sb.append(".ics");
            method.setPath(sb.toString());

            int resultCode = httpClient.executeMethod(method);

            if (resultCode == WebdavStatus.SC_PRECONDITION_FAILED) {
                throw new CosmoException("error.event.duplicate");
            }
            if (resultCode > 399) {
                throw new CosmoException("error.cosmo.server", method.getResponseBodyAsString());
            }

        } catch (HttpException ex) {
            log.error("Http exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } catch (IOException ex) {
            log.error("IO exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Exiting createVCalendar");
        }

        return result;
    }

    /**
     * Removes a VCALENDAR object from the specified calendar directory for the specified user
     * @param username
     * @param collectionName
     * @param uid
     * @throws CosmoException
     */
    public void removeVCalendar(String username, String collectionName, String uid) throws CosmoException {
        DeleteMethod method = null;

        if (log.isTraceEnabled()) {
            log.trace("Entering removeVCalendar");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }
        if (StringUtils.isBlank(uid)) {
            throw new CosmoException("error.missing.argument.exception", "uid");
        }

        try {
            // remove using the root user
            HttpClient httpClient = createHttpClient(null, null);
            method = new DeleteMethod();

            StringBuffer sb = new StringBuffer(defaultBasePath);
            sb.append("/");
            sb.append(username);
            sb.append("/");
            if (StringUtils.isBlank(collectionName)) {
                sb.append(defaultCollection);
            } else {
                sb.append(collectionName);
            }
            sb.append("/");
            sb.append(uid);
            // append with ics file type
            sb.append(".ics");
            method.setPath(sb.toString());

            int resultCode = httpClient.executeMethod(method);

            if (resultCode > 399) {
                throw new CosmoException("error.cosmo.server", method.getResponseBodyAsString());
            }

        } catch (HttpException ex) {
            log.error("Http exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } catch (IOException ex) {
            log.error("IO exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Exiting removeVCalendar");
        }

    }

    /**
     * Returns a calendar object based on the specified username, collection and uid
     * @param username
     * @param collectionName
     * @param uid
     * @return calendar
     * @throws CosmoException
     */
    public Calendar getVCalendar(String username, String collectionName, String uid) throws CosmoException {
        GetMethod method = null;
        Calendar result = null;

        if (log.isTraceEnabled()) {
            log.trace("Entering getVCalendar");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }
        if (StringUtils.isBlank(uid)) {
            throw new CosmoException("error.missing.argument.exception", "uid");
        }

        try {
            // remove using the root user
            HttpClient httpClient = createHttpClient(null, null);
            method = calDAV4JMethodFactory.createGetMethod();

            StringBuffer sb = new StringBuffer(defaultBasePath);
            sb.append("/");
            sb.append(username);
            sb.append("/");
            if (StringUtils.isBlank(collectionName)) {
                sb.append(defaultCollection);
            } else {
                sb.append(collectionName);
            }
            sb.append("/");
            sb.append(uid);
            // append with ics file type
            sb.append(".ics");
            method.setPath(sb.toString());

            int resultCode = httpClient.executeMethod(method);

            if (resultCode > 399) {
                throw new CosmoException("error.cosmo.server", method.getResponseBodyAsString());
            }

            result = method.getResponseBodyAsCalendar();

        } catch (HttpException ex) {
            log.error("Http exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } catch (IOException ex) {
            log.error("IO exception: " + ex.getMessage());
            throw new CosmoException("error.3rd.party.exception", ex, ex.getMessage());
        } catch (ParserException e) {
            log.error("Parser exception: " + e.getMessage());
            throw new CosmoException("error.3rd.party.exception", e, e.getMessage());
        } catch (CalDAV4JProtocolException e) {
            log.error("CalDAV4J protocol exception: " + e.getMessage());
            throw new CosmoException("error.3rd.party.exception", e, e.getMessage());
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Exiting getVCalendar");
        }

        return result;
    }

    /**
     * Returns a list of calendar objects based on a user, optional collection and a start/end date
     * @param username
     * @param collectionName
     * @param startDate
     * @param endDate
     * @return
     * @throws CosmoException
     */
    public List<Calendar> getVCalendars(String username, String password, String collectionName, Date startDate, Date endDate) throws CosmoException {
        GetMethod method = null;
        List<Calendar> result = null;

        if (log.isTraceEnabled()) {
            log.trace("Entering getVCalendar");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }
        if (StringUtils.isBlank(password)) {
            throw new CosmoException("error.missing.argument.exception", "password");
        }
        if (startDate == null) {
            throw new CosmoException("error.missing.argument.exception", "startDate");
        }
        if (endDate == null) {
            throw new CosmoException("error.missing.argument.exception", "endDate");
        }

        try {
            // remove using the root user
            HttpClient httpClient = createHttpClient(username, password);

            if (StringUtils.isBlank(collectionName)) {
                collectionName = defaultCollection;
            }

            CalDAVCalendarCollection collection = createCalDAVCalendarCollection(username, collectionName);
            result = collection.getEventResources(httpClient, startDate, endDate);


        } catch (CalDAV4JException e) {
            log.error("CalDAV4J exception: " + e.getMessage());
            throw new CosmoException("error.3rd.party.exception", e, e.getMessage());
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Exiting getVCalendar");
        }

        return result;
    }

    /**
     * Creates a CalDAVCalendarCollection object to play with
     *
     * @param username
     * @param collectionName
     * @return
     */
    private CalDAVCalendarCollection createCalDAVCalendarCollection(String username, String collectionName) throws CosmoException {
        CalDAVCalendarCollection result = null;

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username");
        }

        StringBuffer sb = new StringBuffer(defaultBasePath);
        sb.append("/");
        sb.append(username);
        sb.append("/");
        if (StringUtils.isBlank(collectionName)) {
            sb.append(defaultCollection);
        } else {
            sb.append(collectionName);
        }

        result = new CalDAVCalendarCollection(sb.toString(), createHostConfiguration(), calDAV4JMethodFactory, calDAV4JMethodFactory.getProdID());

        return result;
    }

    /**
     * The only reason we are overwriting this method is that the CalDAV client uses a specialized HttpClient
     * @return HttpClient
     */
//    protected HttpClient createHttpClient(String username, String password) {
//        if (log.isTraceEnabled()) {
//            log.trace("Entering createHttpClient");
//        }
//
//        HttpClient httpClient = new HttpClient();
//        String u = username;
//        String p = password;
//
//        if (StringUtils.isBlank(u) || StringUtils.isBlank(p)) {
//            u = cosmoRootUser;
//            p = cosmoRootPassword;
//        }
//
//        Credentials credentials = new UsernamePasswordCredentials(u, p);
//        httpClient.getState().setCredentials(new AuthScope(cosmoHost, cosmoPort, AuthScope.ANY_REALM), credentials);
//        httpClient.getParams().setAuthenticationPreemptive(true);
//
//
//        httpClient.setHostConfiguration(createHostConfiguration());
//
//        if (log.isTraceEnabled()) {
//            log.trace("Exiting createHttpClient");
//        }
//
//        return httpClient;
//    }

    // Spring IoC
    private String defaultCollection;
    private String defaultBasePath;
    private CalDAV4JMethodFactory calDAV4JMethodFactory;

    public void setDefaultCollection(String defaultCollection) {
        this.defaultCollection = defaultCollection;
    }

    public void setDefaultBasePath(String defaultBasePath) {
        this.defaultBasePath = defaultBasePath;
    }

    public void setCalDAV4JMethodFactory(CalDAV4JMethodFactory calDAV4JMethodFactory) {
        this.calDAV4JMethodFactory = calDAV4JMethodFactory;
    }
}
