/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.hxcel.globalhealth.service.calendar.AbstractCosmoManager;

/**
 * User: Bjorn Harvold
 * Date: Nov 11, 2006
 * Time: 3:18:37 PM
 * This class is responsible for all interaction with Cosmo's user management servlet
 * It handles create, delete of users.
 */
public class CosmoUserManager extends AbstractCosmoManager {
    private static final Logger log = LoggerFactory.getLogger(CosmoUserManager.class);

    /**
     * Creates a cosmo user. All fields are required.
     *
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @throws CosmoException
     */
    public void createUser(String username, String password, String email,
                           String firstName, String lastName) throws CosmoException {
        if (log.isTraceEnabled()) {
            log.trace("Entering createUser");
        }

        // check that all values are present
        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username cannot be null");
        }
        if (StringUtils.isBlank(password)) {
            throw new CosmoException("error.missing.argument.exception", "password cannot be null");
        }
        if (StringUtils.isBlank(email)) {
            throw new CosmoException("error.missing.argument.exception", "email cannot be null");
        }
        if (StringUtils.isBlank(firstName)) {
            throw new CosmoException("error.missing.argument.exception", "firstName cannot be null");
        }
        if (StringUtils.isBlank(lastName)) {
            throw new CosmoException("error.missing.argument.exception", "lastName cannot be null");
        }

        // dom4j is fucked in the head when it comes to empty namespaces
        // using StringBuffer instead
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\" ?><user xmlns=\"http://osafoundation.org/cosmo/CMP\">");
        sb.append("<username>");
        sb.append(username);
        sb.append("</username>");
        sb.append("<password>");
        sb.append(password);
        sb.append("</password>");
        sb.append("<firstName>");
        sb.append(firstName);
        sb.append("</firstName>");
        sb.append("<lastName>");
        sb.append(lastName);
        sb.append("</lastName>");
        sb.append("<email>");
        sb.append(email);
        sb.append("</email>");
        sb.append("</user>");


        // shoot off "create user request" to cosmo
        processCreateUser(sb.toString());

        if (log.isTraceEnabled()) {
            log.trace("Exiting createUser");
        }
    }

    /**
     * Connnects to the cosmo server and sends a create user request
     *
     * @param s
     * @throws CosmoException
     */
    private void processCreateUser(String s) throws CosmoException {
        if (log.isTraceEnabled()) {
            log.trace("Entering processCreateUser");
        }

        PutMethod putMethod = null;

        try {
            putMethod = new PutMethod(cosmoSignupUserPath);
            RequestEntity requestEntity = new StringRequestEntity(s, "text/xml", "utf-8");
            putMethod.setRequestEntity(requestEntity);

            HttpClient httpClient = createHttpClient(null, null);
            int result = httpClient.executeMethod(putMethod);

            if (log.isTraceEnabled()) {
                log.trace("Status code: " + result);
                log.trace("Response body: ");
                log.trace(putMethod.getResponseBodyAsString());
            }

            if (result > 399) {
                throw new CosmoException("error.cosmo.server", putMethod.getResponseBodyAsString());
            }


        } catch (UnsupportedEncodingException ex) {
            log.error("Problem with encoding", ex);
            throw new CosmoException("Problem with encoding", ex);
        } catch (HttpException e) {
            log.error("HttpException. Problem with connection.", e);
            throw new CosmoException("HttpException. Problem with connection.", e);
        } catch (IOException e) {
            log.error("IOException", e);
        } finally {
            putMethod.releaseConnection();
        }
        if (log.isTraceEnabled()) {
            log.trace("Exiting processCreateUser");
        }
    }

    /**
     * Removes a cosmo users based on username
     *
     * @param username
     * @throws CosmoException
     */
    public void removeUser(String username) throws CosmoException {
        if (log.isTraceEnabled()) {
            log.trace("Entering removeUser");
        }

        if (StringUtils.isBlank(username)) {
            throw new CosmoException("error.missing.argument.exception", "username cannot be null");
        }

        // remove
        processRemoveUser(username);

        if (log.isTraceEnabled()) {
            log.trace("Exiting removeUser");
        }
    }

    /**
     * Conects to the cosmo server and sends a remove user request
     * @param username
     * @throws CosmoException
     */
    private void processRemoveUser(String username) throws CosmoException {
        if (log.isTraceEnabled()) {
            log.trace("Entering processRemoveUser");
        }

        DeleteMethod deleteMethod = null;
        try {
            StringBuffer sb = new StringBuffer(cosmoRemoveUserPath);
            sb.append("/");
            sb.append(username);

            deleteMethod = new DeleteMethod(sb.toString());

            HttpClient httpClient = createHttpClient(null, null);
            int result = httpClient.executeMethod(deleteMethod);

            if (log.isTraceEnabled()) {
                log.trace("Status code: " + result);
                log.trace("Response body: ");
                log.trace(deleteMethod.getResponseBodyAsString());
            }

            if (result > 399) {
                throw new CosmoException("error.cosmo.server", deleteMethod.getResponseBodyAsString());
            }
        } catch (UnsupportedEncodingException ex) {
            log.error("Problem with encoding", ex);
            throw new CosmoException("error.3rd.party.exception", ex.getMessage());
        } catch (HttpException e) {
            log.error("HttpException. Problem with connection.", e);
            throw new CosmoException("error.3rd.party.exception", e.getMessage());
        } catch (IOException e) {
            log.error("IOException", e);
        } finally {
            deleteMethod.releaseConnection();
        }
        if (log.isTraceEnabled()) {
            log.trace("Exiting processRemoveUser");
        }
    }

    private String cosmoSignupUserPath;
    private String cosmoRemoveUserPath;

    public void setCosmoSignupUserPath(String cosmoSignupUserPath) {
        this.cosmoSignupUserPath = cosmoSignupUserPath;
    }

    public void setCosmoRemoveUserPath(String cosmoRemoveUserPath) {
        this.cosmoRemoveUserPath = cosmoRemoveUserPath;
    }
}
