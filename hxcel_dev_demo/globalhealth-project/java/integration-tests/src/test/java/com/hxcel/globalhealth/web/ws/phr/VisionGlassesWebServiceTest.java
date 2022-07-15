/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.PractitionerDto;
import com.hxcel.globalhealth.domain.phr.dto.VisionGlassesDto;
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
 * Date: Jan 29, 2007
 * Time: 11:49:53 PM
 */
public class VisionGlassesWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(VisionGlassesWebServiceTest.class);
    private VisionGlassesDto dto = null;
    private PractitionerDto pc = null;

    @Before
    public void setUp() {
        super.setUp();
        pc = createPractitioner(pdto);
    }

    @Test
    public void testVisionGlasses() {
        testCreateVisionGlasses();
        testGetVisionGlassess(1);
        testRemoveVisionGlasses(dto.getId());
        testGetVisionGlassess(0);
    }

    private void testCreateVisionGlasses() {
        log.info("Creating dto");
        VisionGlassesDto ad = DtoTestFactory.createVisionGlassesDto(pdto.getPhr(), pc.getId());

        ad = phrWSClient.saveVisionGlasses(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetVisionGlassess(int results) {
        List<VisionGlassesDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getVisionGlasses(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveVisionGlasses(String id) {
        List<VisionGlassesDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeVisionGlasses(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
