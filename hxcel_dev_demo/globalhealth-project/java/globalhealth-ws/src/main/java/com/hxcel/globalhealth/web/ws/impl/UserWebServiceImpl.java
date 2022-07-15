/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.impl;

import com.hxcel.globalhealth.web.ws.AbstractWebService;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.web.ws.UserWebService;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.interceptor.Fault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ResourceBundle;

/**
 * User: Bjorn Harvold
 * Date: Jan 13, 2007
 * Time: 4:12:48 PM
 */
@WebService(serviceName = "UserWebService", endpointInterface = "com.hxcel.globalhealth.web.ws.UserWebService")
public class UserWebServiceImpl extends AbstractWebService implements UserWebService {
    private final static Log log = LogFactory.getLog(UserWebServiceImpl.class);

    /**
     * Returns the user by that id
     * @param id
     * @return
     */
    @WebMethod
    @WebResult(name = "User")
    public UserDto getUser(@WebParam(name="id") String id) throws Fault {
        UserDto result = null;

        try {
            result = userManager.loadUser(id);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    /**
     * Returns the user by that username
     * @param username
     * @return
     */
    @WebMethod
    @WebResult(name = "User")
    public UserDto getUserByUsername(@WebParam(name="username") String username) throws Fault {
        UserDto result = null;

        try {
            result = userManager.getUserDtoByUsername(username);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    /**
     * Persists the user with the db
     * @param dto
     * @throws Fault
     */
    @WebMethod
    @WebResult(name = "User")
    public UserDto saveNewUser(@WebParam(name = "dto")UserDto dto) throws Fault {
        UserDto result = null;

        try {
            result = userManager.saveNewUserDto(dto);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    /**
     * Gets user by username so see if it is already taken
     * @param username
     * @return boolean
     * @throws Fault
     */
    @WebMethod
    @WebResult(name = "isUsernameTaken")
    public Boolean isUsernameTaken(@WebParam(name="username") String username) throws Fault {
        Boolean result;

        try {

            result = userManager.isUsernameTaken(username);

        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    /**
     * Checks to see if user is unique for an existing user. isUsernameTaken can return true because the user is querying
     * herself. Not using this method however.
     * @param id
     * @param username
     * @return
     * @throws Fault
     */
    @WebMethod
    @WebResult(name = "isUserUnique")
    public Boolean isUserUnique(@WebParam(name = "id") String id, @WebParam(name="username") String username) throws Fault {
        Boolean result;

        try {
            result = userManager.isUserUnique(id, username);

        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    /**
     * Called by login
     * @param username
     * @param password
     * @return
     * @throws Fault
     */
    @WebMethod
    @WebResult(name = "User")
    public UserDto login(@WebParam(name = "username") String username, @WebParam(name="password") String password) throws Fault {
        UserDto result = null;

        try {
            result = userManager.getUserDto(username, password);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private UserManager userManager;

    private ResourceBundle resourceBundle;

    public void setResourceBundle(String resourceBundle) {
        this.resourceBundle = ResourceBundle.getBundle(resourceBundle);
    }
}
