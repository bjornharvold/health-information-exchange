/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.common;


import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * User: Bjorn Harvold
 * Date: Jan 21, 2007
 * Time: 6:19:20 PM
 * As the WS tests need to be light weight and cannot be allowed to start up the whole context. This class does not extend out usual base test class
 */
public class UserWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(UserWebServiceTest.class);

    @Test
    public void testUserWebService() {
        try {
            assertNotNull("New u is not null", udto);

            udto = getUser(udto.getId());
            assertNotNull("Get u by id is not null", udto);

            Boolean isUsernameTaken = isUsernameTaken(udto.getUsername());
            assertTrue("Username should already be taken", isUsernameTaken);

            Boolean isUserUnique = isUserUnique(udto.getId(), udto.getUsername());
            assertTrue("User should be unique", isUserUnique);

            UserDto dto = login(udto.getUsername(), "password");
            assertEquals("Ids should be the same", udto.getId(), dto.getId());

        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage(), f);
            assertTrue("Something went wrong", false);
        }
    }

    private UserDto getUser(String id) throws Fault {
        log.info("Retrieving user by id");

        return userWSClient.getUser(id);
    }

    private Boolean isUsernameTaken(String username) throws Fault {
        log.info("checking if username is taken");

        return userWSClient.isUsernameTaken(username);
    }

    private Boolean isUserUnique(String id, String username) throws Fault {
        log.info("checking if username is unique. difference is we do a check on userid as well");

        return userWSClient.isUserUnique(id, username);
    }

    private UserDto login(String username, String password) throws Fault {
        log.info("logging in to the system");

        return userWSClient.login(username, password);
    }

}
