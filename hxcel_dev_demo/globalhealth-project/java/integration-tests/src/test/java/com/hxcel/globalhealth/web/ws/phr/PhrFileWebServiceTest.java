/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.PhrFileDto;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 7:57:56 PM
 */
public class PhrFileWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(PhrFileWebServiceTest.class);
    private PhrFileDto dto = null;

    @Test
    public void testNotImplemented() {
        log.info("PHRFileWebServiceTest is not complete yet. Need to implement slide.");
        assertTrue(true);
    }

    /*
    @Test
    public void testPhrFile() {
        testCreatePhrFile();
        testGetPhrFiles(1);
        testRemovePhrFile(dto.getId());
        testGetPhrFiles(0);
    }

    private void testCreatePhrFile() {
        log.info("Creating dto");
        PhrFileDto ad = DtoTestFactory.createPhrFileDto(p.getPhr());

        ad = phrWSClient.savePhrFile(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetPhrFiles(int results) {
        List<MedicationDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getRecordsByPhrId(p.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemovePhrFile(String id) {
        List<PhrFileDto> list = null;

        try {
            log.info("Removing dto");
            //phrWSClient.removePhrFile(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
    */
}
