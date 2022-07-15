/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.PractitionerDto;
import com.hxcel.globalhealth.domain.DtoTestFactory;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 29, 2007
 * Time: 11:49:53 PM
 */
public class PractitionerWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(PractitionerWebServiceTest.class);
    private PractitionerDto dto = null;

    @Test
    public void testPractitioner() {
        testCreatePractitioner();
        testGetPractitioners(1);
        testRemovePractitioner(dto.getId());
        testGetPractitioners(0);
    }

    private void testCreatePractitioner() {
        log.info("Creating dto");
        PractitionerDto ad = DtoTestFactory.createPractitionerDto(pdto.getPhr());

        ad = phrWSClient.savePractitioner(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetPractitioners(int results) {
        List<PractitionerDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getPractitioners(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertTrue("Should be at least on phr contact in the system", list.size() > 0);
        } else {
            if (list == null) {
                // means we ran this test for the first time and there are no contacts. this is ok
                assertNull(list);
            } else {
                // means other tests have created a phr contact before this test ran, this is ok too
                assertNotNull(list);
                assertTrue("Should be at least on phr contact in the system", list.size() > 0);
            }
        }
        log.info("dtos retrieved");
    }

    private void testRemovePractitioner(String id) {
        List<PractitionerDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removePractitioner(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
