/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.professional.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;

/**
 * User: Bjorn Harvold
 * Date: Jun 27, 2006
 * Time: 7:07:25 PM
 */
public class ProfessionalDAOTest extends AbstractIntegrationBaseTest {
    private static final Logger log = LoggerFactory.getLogger(ProfessionalDAOTest.class);

    @Test
    public void testTrue() {
        assertTrue(true);
    }
    
    /*
    @Test
    public void testGetProfessionalByUserId() {
        Professional pro = null;
        try {
            log.info("Getting professional by user id");


            assertNotNull(u);
            log.info("Inserted test user successfully");

            pro = professionalDAO.getProfessionalByUserId(getUser().getId());
            assertNotNull(pro);

            log.info("Retrieved the same user as a professional successfully");

        } catch (PersistenceException ex) {
            log.error("Error: \n", ex);
            assertNotNull(pro);
        }
    }


    public void testGetProfessionalsByLocale() {
        List<Professional> list = null;
        try {
            log.info("Getting pros by locale");

            create10Users();
            log.info("Inserted test users successfully");

            CountryCd countryCd;
            countryCd = countryDAO.findCountryByName(CountryCd.UNITED_STATES);

            list = professionalDAO.getProfessionalsByLocale(countryCd);
            assertNotNull(list);
            assertTrue(list.size() == 10);
            log.info("Yes we got back the U.S. pros");

            countryCd = countryDAO.findCountryByName(CountryCd.NORWAY);
            list = professionalDAO.getProfessionalsByLocale(countryCd);
            assertTrue(list.isEmpty());
            log.info("Yes there were no Norwegian pros");


        } catch (PersistenceException ex) {
            log.error("Error: \n", ex);
            assertNotNull(list);
        }
    }

*/
    // Spring IoC
    @Autowired
    private ProfessionalDAO professionalDAO = null;

}
