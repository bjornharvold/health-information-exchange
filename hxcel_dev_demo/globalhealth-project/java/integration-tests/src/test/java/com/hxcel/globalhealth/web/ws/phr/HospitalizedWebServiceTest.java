/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.HospitalizedDto;
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
public class HospitalizedWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(HospitalizedWebServiceTest.class);
    private HospitalizedDto dto = null;

    @Test
    public void testHospitalized() {
        testCreateHospitalized();
        testGetHospitalizeds(1);
        testRemoveHospitalized(dto.getId());
        testGetHospitalizeds(0);
    }

    private void testCreateHospitalized() {
        log.info("Creating dto");
        HospitalizedDto ad = DtoTestFactory.createHospitalizedDto(pdto.getPhr());

        ad = phrWSClient.saveHospitalized(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetHospitalizeds(int results) {
        List<HospitalizedDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getHospitalizeds(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveHospitalized(String id) {
        List<HospitalizedDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeHospitalized(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
