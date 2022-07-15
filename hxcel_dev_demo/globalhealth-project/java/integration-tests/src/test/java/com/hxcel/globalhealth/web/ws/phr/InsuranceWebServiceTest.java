/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.InsuranceDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrContactDto;
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
public class InsuranceWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(InsuranceWebServiceTest.class);
    private InsuranceDto dto = null;
    private PhrContactDto pc = null;

    @Before
    public void setUp() {
        super.setUp();
        pc = createPhrContact(pdto);
    }

    @Test
    public void testInsurance() {
        testCreateInsurance();
        testGetInsurances(1);
        testRemoveInsurance(dto.getId());
        testGetInsurances(0);
    }

    private void testCreateInsurance() {
        log.info("Creating dto");
        InsuranceDto ad = DtoTestFactory.createInsuranceDto(pdto.getPhr(), pc.getId());

        ad = phrWSClient.saveInsurance(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetInsurances(int results) {
        List<InsuranceDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getInsurances(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveInsurance(String id) {
        List<InsuranceDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeInsurance(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
