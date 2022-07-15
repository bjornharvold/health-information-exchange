package com.hxcel.globalhealth.calendar;

import com.hxcel.globalhealth.calendar.spec.*;
import com.hxcel.globalhealth.calendar.factory.chandler.CmpFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Dec 26, 2008
 * Time: 5:12:41 PM
 * Responsibility:
 */
public class CmpServiceImpl extends AbstractCalendarService implements CmpService {
    private final static Logger log = LoggerFactory.getLogger(CmpServiceImpl.class);

    /**
     * Retrieves a list of users
     * @param name - filter on name
     * @param sortBy - sort by a column name
     * @param orderBy - order ascending or descending
     * @param index - page number
     * @param maxResults - number of results
     * @return
     * @throws CalendarException
     */
    @Override
    public List<CalendarUser> getUsers(String name, CalendarSortType sortBy, CalendarSortOrder orderBy, Integer index, Integer maxResults) throws CalendarException {
        List<CalendarUser> result = null;

        String url = cmpFactory.createGetUsersRequest(name, sortBy, orderBy, index, maxResults);

        if (log.isTraceEnabled()) {
            log.trace("Retrieving list of users from calendar server: \n" + url);
        }

        String response = doGetRequest(url, 200, null);
        result = cmpFactory.handleGetUsersResponse(response);

        return result;
    }

    /**
     * Gets a single user by username
     * @param username
     * @return
     * @throws CalendarException
     */
    @Override
    public CalendarUser getUser(String username) throws CalendarException {
        CalendarUser result = null;

        if (StringUtils.isBlank(username)) {
            log.error("username is empty");

            throw new CalendarException("error.missing.argument.exception", "username");
        }

        String url = cmpFactory.createGetUserRequest(username);

        if (log.isTraceEnabled()) {
            log.trace("Retrieving user from calendar server: \n" + url);
        }


        String response = doGetRequest(url, 200, null);

        result = cmpFactory.handleGetUserResponse(response);

        return result;
    }

    /**
     * Creates a new user with the calendar server
     * @param username
     * @param firstName
     * @param lastName
     * @param password
     * @param email
     * @throws CalendarException
     */
    @Override
    public void createUser(String username, String firstName, String lastName,
                                   String password, String email) throws CalendarException {

        if (StringUtils.isBlank(username)) {
            log.error("username is empty");

            throw new CalendarException("error.missing.argument.exception", "username");
        }

        if (StringUtils.isBlank(firstName)) {
            log.error("firstName is empty");

            throw new CalendarException("error.missing.argument.exception", "firstName");
        }

        if (StringUtils.isBlank(lastName)) {
            log.error("lastName is empty");

            throw new CalendarException("error.missing.argument.exception", "lastName");
        }

        if (StringUtils.isBlank(password)) {
            log.error("password is empty");

            throw new CalendarException("error.missing.argument.exception", "password");
        }

        if (StringUtils.isBlank(email)) {
            log.error("email is empty");

            throw new CalendarException("error.missing.argument.exception", "email");
        }

        String url = cmpFactory.createGetUserRequest(username);

        if (log.isTraceEnabled()) {
            log.trace("Creating user on calendar server: \n" + url);
        }

        String body = cmpFactory.createCreateUserBody(username, firstName, lastName, password, email);

        Map<Integer, String> errorCodes = new HashMap<Integer, String>();
        errorCodes.put(400, "the XML representation was improperly constructed, an attribute value was missing or invalid, or the username specified in the XML does not match the username in the request URI");
        errorCodes.put(431, "the specified user name is already used by an existing account");
        errorCodes.put(432, "the specified email address is already used by an existing account");

        Integer successCode = 201;

        // if this method doesn't throw an error we are good to go
        doPutRequest(url, body, successCode, errorCodes);
    }

    /**
     * Updates a new user with the calendar server. Only the values that need changing are necessary
     * @param username
     * @param firstName
     * @param lastName
     * @param password
     * @param email
     * @throws CalendarException
     */
    @Override
    public void updateUser(String currentUsername, String username, String firstName, String lastName,
                                   String password, String email) throws CalendarException {
        if (StringUtils.isBlank(currentUsername)) {
            log.error("currentUsername is empty");

            throw new CalendarException("error.missing.argument.exception", "currentUsername");
        }

        String url = cmpFactory.createGetUserRequest(currentUsername);

        if (log.isTraceEnabled()) {
            log.trace("Updating user on calendar server: \n" + url);
        }

        String body = cmpFactory.createCreateUserBody(username, firstName, lastName, password, email);

        Map<Integer, String> errorCodes = new HashMap<Integer, String>();
        errorCodes.put(400, "the XML representation was improperly constructed, an attribute value was missing or invalid, or the username specified in the XML does not match the username in the request URI");
        errorCodes.put(431, "the specified user name is already used by an existing account");
        errorCodes.put(432, "the specified email address is already used by an existing account");

        Integer successCode = 204;

        // if this method doesn't throw an error we are good to go
        doPutRequest(url, body, successCode, errorCodes);
    }

    @Override
    public void deleteUser(String username) throws CalendarException {
        if (StringUtils.isBlank(username)) {
            log.error("username is empty");

            throw new CalendarException("error.missing.argument.exception", "username");
        }

        String url = cmpFactory.createGetUserRequest(username);
        if (log.isTraceEnabled()) {
            log.trace("Deleting user from calendar server: \n" + url);
        }

        Map<Integer, String> errorCodes = new HashMap<Integer, String>();
        errorCodes.put(403, "the attempt to delete the root user account was not allowed");

        Integer successCode = 204;

        // if this method doesn't throw an error we are good to go
        doDeleteRequest(url, successCode, errorCodes);
    }

    @Override
    public Integer getUserCount() throws CalendarException {
        Integer result = null;

        String url = cmpFactory.createGetUserCountRequest();

        if (log.isTraceEnabled()) {
            log.trace("Retrieving user count from calendar server: \n" + url);
        }

        String response = doGetRequest(url, 200, null);

        response = response.replaceAll("\n", "");
        result = Integer.parseInt(response);

        return result;
    }

    @Override
    public UserServiceDocument getUserServiceDocument(String username) throws CalendarException {
        UserServiceDocument result = null;

        if (StringUtils.isBlank(username)) {
            log.error("username is empty");

            throw new CalendarException("error.missing.argument.exception", "username");
        }

        String url = cmpFactory.createGetUserServiceDocumentRequest(username);

        if (log.isTraceEnabled()) {
            log.trace("Retrieving user service document for " + username + " from calendar server: \n" + url);
        }

        Map<Integer, String> errorCodes = new HashMap<Integer, String>();
        errorCodes.put(403, "the authenticated user is not an administrator and requested another user's USD");

        Integer successCode = 200;

        // if this method doesn't throw an error we are good to go
        String response = doGetRequest(url, successCode, errorCodes);

        result = cmpFactory.handleGetUserServiceDocumentResponse(response);

        return result;
    }

    // Spring IoC
    private CmpFactory cmpFactory;

    public void setCmpFactory(CmpFactory cmpFactory) {
        this.cmpFactory = cmpFactory;
    }
}
