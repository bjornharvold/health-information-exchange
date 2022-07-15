/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.TravelImmunizationDto;
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
 * Date: Jan 29, 2007
 * Time: 11:49:53 PM
 */
public class TravelImmunizationWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(TravelImmunizationWebServiceTest.class);
private TravelImmunizationDto dto = null;

    @Test
    public void testTravelImmunization() {
        testCreateTravelImmunization();
        testGetTravelImmunizations(1);
        testRemoveTravelImmunization(dto.getId());
        testGetTravelImmunizations(0);
    }

    private void testCreateTravelImmunization() {
        log.info("Creating dto");
        TravelImmunizationDto ad = DtoTestFactory.createTravelImmunizationDto(pdto.getPhr());

        ad = phrWSClient.saveTravelImmunization(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetTravelImmunizations(int results) {
        List<TravelImmunizationDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getTravelImmunizations(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveTravelImmunization(String id) {
        List<TravelImmunizationDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeTravelImmunization(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
