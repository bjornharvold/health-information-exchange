/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.PersonalProfileDto;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 7:57:56 PM
 */
public class PersonalProfileWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(PhrContactWebServiceTest.class);

    @Test
    public void testPersonalProfile() {

        testGetPersonalProfile();

    }

    private void testGetPersonalProfile() {
        PersonalProfileDto dto = null;

        log.info("Retrieving dto");
        dto = phrWSClient.getPersonalProfile(pdto.getPhr());

        assertNotNull("Expecting personal profile not to be a null object", dto);

        log.info("dto retrieved");
    }

}