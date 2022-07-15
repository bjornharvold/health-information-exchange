/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.hxcel.globalhealth.service.calendar.CosmoUserManager;
import com.hxcel.globalhealth.service.calendar.AbstractCalendarBaseTest;

/**
 * User: Bjorn Harvold
 * Date: Nov 11, 2006
 * Time: 8:01:36 PM
 */
public class CosmoUserManagerTest extends AbstractCalendarBaseTest {
    private static final Logger log = LoggerFactory.getLogger(CosmoUserManager.class);

    public void testTrue() {
        assertTrue(true);
    }
    
//    public void testCreateCosmoUser() {
//        try {
//            cosmoUserManager.createUser("test100", "test100", "test100@test100.com", "test100", "test100");
//        } catch (CosmoException e) {
//            log.error("Error during create user test", e);
//            assertTrue(false);
//        }
//    }
//
//    public void testRemoveCosmoUser() {
//        try {
//            cosmoUserManager.removeUser("test100");
//        } catch (CosmoException e) {
//            log.error("Error during remove user test", e);
//            assertTrue(false);
//        }
//    }

    // Spring IoC
    private CosmoUserManager cosmoUserManager;

    public void setCosmoUserManager(CosmoUserManager cosmoUserManager) {
        this.cosmoUserManager = cosmoUserManager;
    }
}
