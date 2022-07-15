/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws;

import com.hxcel.globalhealth.domain.common.dto.UserDto;
import org.apache.cxf.interceptor.Fault;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * User: Bjorn Harvold
 * Date: Jul 14, 2007
 * Time: 4:00:11 PM
 */
@WebService
public interface UserWebService {
    @WebMethod
    @WebResult(name = "User")
    UserDto getUser(@WebParam(name="id") String id) throws Fault;

    @WebMethod
    @WebResult(name = "User")
    UserDto getUserByUsername(@WebParam(name="username") String username) throws Fault;

    @WebMethod
    UserDto saveNewUser(@WebParam(name = "dto")UserDto dto) throws Fault;

    @WebMethod
    @WebResult(name = "isUsernameTaken")
    Boolean isUsernameTaken(@WebParam(name="username") String username) throws Fault;

    @WebMethod
    @WebResult(name = "isUserUnique")
    Boolean isUserUnique(@WebParam(name = "id") String id, @WebParam(name="username") String username) throws Fault;

    @WebMethod
    @WebResult(name = "User")
    UserDto login(@WebParam(name = "username") String username, @WebParam(name="password") String password) throws Fault;

}
