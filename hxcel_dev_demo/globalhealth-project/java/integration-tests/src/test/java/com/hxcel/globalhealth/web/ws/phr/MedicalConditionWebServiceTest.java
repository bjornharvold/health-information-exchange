/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.MedicalConditionDto;
import com.hxcel.globalhealth.domain.phr.dto.PractitionerDto;
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
public class MedicalConditionWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(MedicalConditionWebServiceTest.class);
    private MedicalConditionDto dto = null;
    private PractitionerDto pc = null;

    @Before
    public void setUp() {
        super.setUp();
        pc = createPractitioner(pdto);
    }

    @Test
    public void testMedicalCondition() {
        testCreateMedicalCondition();
        testGetMedicalConditions(1);
        testRemoveMedicalCondition(dto.getId());
        testGetMedicalConditions(0);
    }

    private void testCreateMedicalCondition() {
        log.info("Creating dto");
        MedicalConditionDto ad = DtoTestFactory.createMedicalConditionDto(pdto.getPhr(), pc.getId());

        ad = phrWSClient.saveMedicalCondition(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetMedicalConditions(int results) {
        List<MedicalConditionDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getMedicalConditions(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveMedicalCondition(String id) {
        List<MedicalConditionDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeMedicalCondition(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
