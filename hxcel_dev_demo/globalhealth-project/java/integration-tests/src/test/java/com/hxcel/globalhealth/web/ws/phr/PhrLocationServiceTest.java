/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.PhrLocationDto;
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
import org.junit.Before;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 7:57:56 PM
 */
public class PhrLocationServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(PhrLocationServiceTest.class);
    private PhrLocationDto dto = null;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testPhrLocation() {

        testCreatePhrLocation();
        testGetPhrLocations(1);
        testRemovePhrLocation(dto.getId());
        testGetPhrLocations(0);
    }

    private void testCreatePhrLocation() {
        log.info("Creating dto");
        PhrLocationDto ad = DtoTestFactory.createPhrLocationDto(pdto.getPhr());

        ad = phrWSClient.savePhrLocation(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetPhrLocations(int results) {
        List<PhrLocationDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getPhrLocations(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemovePhrLocation(String id) {
        List<PhrLocationDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removePhrLocation(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }
        log.info("dto removed");
    }
}
