/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.MedicationDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrContactDto;
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
public class MedicationWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(MedicationWebServiceTest.class);
    private MedicationDto dto = null;
    private PractitionerDto practitionerDto = null;
    private PhrContactDto pc = null;

    @Before
    public void setUp() {
        super.setUp();
        practitionerDto = createPractitioner(pdto);
        pc = createPhrContact(pdto);
    }
    @Test
    public void testMedication() {
        testCreateMedication();
        testGetMedications(1);
        testRemoveMedication(dto.getId());
        testGetMedications(0);
    }

    private void testCreateMedication() {
        log.info("Creating dto");
        MedicationDto ad = DtoTestFactory.createMedicationDto(pdto.getPhr(), practitionerDto.getId(), pc.getId());

        ad = phrWSClient.saveMedication(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetMedications(int results) {
        List<MedicationDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getMedications(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveMedication(String id) {
        List<MedicationDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeMedication(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
