/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.DentalDto;
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
public class DentalWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(DentalWebServiceTest.class);
    private DentalDto dto = null;

    @Test
    public void testDental() {
        testCreateDental();
        testGetDentals(1);
        testRemoveDental(dto.getId());
        testGetDentals(0);
    }

    private void testCreateDental() {
        log.info("Creating dto");
        DentalDto ad = DtoTestFactory.createDentalDto(pdto.getPhr());

        ad = phrWSClient.saveDental(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetDentals(int results) {
        List<DentalDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getDentals(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveDental(String id) {
        List<DentalDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeDental(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
