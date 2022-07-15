/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.emr.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;

/**
 * User: Bjorn Harvold
 * Date: Jul 11, 2006
 * Time: 9:12:17 PM
 */
public class BasicEmrEntryDAOTest extends AbstractIntegrationBaseTest {
    private static final Logger log = LoggerFactory.getLogger(BasicEmrEntryDAOTest.class);

    @Test
    public void testCaseInformationDAO() {
        assertTrue(true);
    }

    // Spring IoC
    @Autowired
    private BasicEmrEntryDAO basicEmrEntryDAO = null;

}
