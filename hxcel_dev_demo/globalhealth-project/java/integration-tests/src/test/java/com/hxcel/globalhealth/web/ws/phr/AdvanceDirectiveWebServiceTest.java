/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.phr;


import com.hxcel.globalhealth.domain.phr.dto.AdvanceDirectiveDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrContactDto;
import com.hxcel.globalhealth.domain.DtoTestFactory;
import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
public class AdvanceDirectiveWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(AdvanceDirectiveWebServiceTest.class);
    private AdvanceDirectiveDto dto = null;
    private PhrContactDto pc = null;

    @Before
    public void setUp() {
        super.setUp();
        pc = createPhrContact(pdto);
    }

    @Test
    public void testAdvanceDirective() {

        testCreateAdvanceDirective();
        testGetAdvanceDirectives(1);
        testRemoveAdvanceDirective(dto.getId());
        testGetAdvanceDirectives(0);
    }

    private void testCreateAdvanceDirective() {
        log.info("Creating dto");
        AdvanceDirectiveDto ad = DtoTestFactory.createAdvanceDirectiveDto(pdto.getPhr(), pc.getId());

        ad = phrWSClient.saveAdvanceDirective(ad);
        assertNotNull("Expecting dto has id", ad.getId());

        log.info("dto created");
        dto = ad;
    }

    private void testGetAdvanceDirectives(int results) {
        List<AdvanceDirectiveDto> list = null;

        log.info("Retrieving dtos");
        list = phrWSClient.getAdvanceDirectives(pdto.getPhr());

        if (results > 0) {
            assertNotNull(list);
            assertEquals(results, list.size());
        } else {
            assertNull(list);
        }
        log.info("dtos retrieved");
    }

    private void testRemoveAdvanceDirective(String id) {
        List<AdvanceDirectiveDto> list = null;

        try {
            log.info("Removing dto");
            phrWSClient.removeAdvanceDirective(id);
        } catch (Fault e) {
            log.error("Problem removing dto: " + e.getMessage(), e);
            assertTrue("This is an error. Could not remove dto.", false);
        }
        log.info("dto removed");
    }

}
