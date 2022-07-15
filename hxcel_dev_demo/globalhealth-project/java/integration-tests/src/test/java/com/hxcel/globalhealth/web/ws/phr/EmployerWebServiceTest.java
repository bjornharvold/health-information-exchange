/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.EmployerDto;
import com.hxcel.globalhealth.domain.DtoTestFactory;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 7:57:56 PM
 */
public class EmployerWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(EmployerWebServiceTest.class);
    private EmployerDto dto = null;

    @Test
    public void testEmployer() {
        testCreateEmployer();
        testGetEmployers(1);
        testRemoveEmployer(dto.getId());
        testGetEmployers(0);
    }

    private void testCreateEmployer() {
        log.info("Creating dto");
        EmployerDto ad = DtoTestFactory.createEmployerDto(pdto.getPhr());

        ad = phrWSClient.saveEmployer(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetEmployers(int results) {
        List<EmployerDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getEmployers(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveEmployer(String id) {
        List<EmployerDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeEmployer(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
