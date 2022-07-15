/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.ImmunizationDto;
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
public class ImmunizationWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(ImmunizationWebServiceTest.class);
    private ImmunizationDto dto = null;

    @Test
    public void testImmunization() {
        testCreateImmunization();
        testGetImmunizations(1);
        testRemoveImmunization(dto.getId());
        testGetImmunizations(0);
    }

    private void testCreateImmunization() {
        log.info("Creating dto");
        ImmunizationDto ad = DtoTestFactory.createImmunizationDto(pdto.getPhr());

        ad = phrWSClient.saveImmunization(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetImmunizations(int results) {
        List<ImmunizationDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getImmunizations(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveImmunization(String id) {
        List<ImmunizationDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeImmunization(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
