/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.PhrContactDto;
import com.hxcel.globalhealth.domain.DtoTestFactory;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 7:57:56 PM
 */
public class PhrContactWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(PhrContactWebServiceTest.class);
    private PhrContactDto dto = null;

    @Test
    public void testPhrContact() {
        testCreatePhrContact();
        testGetPhrContacts(1);
        testRemovePhrContact(dto.getId());
        testGetPhrContacts(0);
    }

    private void testCreatePhrContact() {
        log.info("Creating dto");
        PhrContactDto ad = DtoTestFactory.createPhrContactDto(pdto.getPhr());

        ad = phrWSClient.savePhrContact(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetPhrContacts(int results) {
        List<PhrContactDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getPhrContacts(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertTrue("Should be at least on phr contact in the system", list.size() > 0);
        } else {
            if (list == null) {
                // means we ran this test for the first time and there are no contacts. this is ok
                assertNull(list);
            } else {
                // means other tests have created a phr contact before this test ran, this is ok too
                assertNotNull(list);
                assertTrue("Should be at least on phr contact in the system", list.size() > 0);
            }
        }
        log.info("dtos retrieved");
    }

    private void testRemovePhrContact(String id) {
        List<PhrContactDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removePhrContact(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }

        log.info("dto removed");
    }
}
