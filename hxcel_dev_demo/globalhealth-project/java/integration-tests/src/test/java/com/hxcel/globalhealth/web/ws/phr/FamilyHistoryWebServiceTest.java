/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.FamilyHistoryDto;
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
public class FamilyHistoryWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(FamilyHistoryWebServiceTest.class);
    private FamilyHistoryDto dto = null;

    @Test
    public void testFamilyHistory() {
        testCreateFamilyHistory();
        testGetFamilyHistories(1);
        testRemoveFamilyHistory(dto.getId());
        testGetFamilyHistories(0);
    }

    private void testCreateFamilyHistory() {
        log.info("Creating dto");
        FamilyHistoryDto ad = DtoTestFactory.createFamilyHistoryDto(pdto.getPhr());

        ad = phrWSClient.saveFamilyHistory(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetFamilyHistories(int results) {
        List<FamilyHistoryDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getFamilyHistories(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveFamilyHistory(String id) {
        List<FamilyHistoryDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeFamilyHistory(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
