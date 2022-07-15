/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws;

import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.phr.dto.PhrContactDto;
import com.hxcel.globalhealth.domain.phr.dto.PractitionerDto;
import com.hxcel.globalhealth.domain.DtoTestFactory;
import com.hxcel.globalhealth.domain.patient.dto.PatientDto;
import com.hxcel.globalhealth.jetty.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: bjorn
 * Date: Dec 5, 2007
 * Time: 12:24:32 AM
 * This is a light-weight base class for web services to extend. Contains some convenience methods that are needed for some tests
 */
@ContextConfiguration(locations = {"/test-spring-web-client-ws-beans.xml", "/test-spring-web-property-configurer-bean.xml"})
public abstract class AbstractWebServiceBaseTest extends AbstractJUnit4SpringContextTests {
    private final static Logger log = LoggerFactory.getLogger(AbstractWebServiceBaseTest.class);
    protected UserDto udto = null;
    protected PatientDto pdto = null;
    private JettyServer server = null;

    @Before
    public void setUp() {
        try {
            server = new JettyServer();
            server.start();
        } catch (Exception e) {
            log.error("Problem starting JettyServer", e);
            System.exit(1);
        }

        try {
            log.info("Starting " + this.getClass().getName() + " web service test");
            log.info("Creating test user for unit test");
            udto = createUser();
            pdto = getPatient(udto.getId());
        } catch (Exception e) {
            log.error("Problem setting up test data", e);
            System.exit(1);
        }
    }

    // shutdown server
    @After
    public void tearDown() throws Exception {
        if (server != null) {
            server.stop();
        }
        log.info("Ending " + this.getClass().getName() + " web service test");
    }

    private UserDto createUser() {
        UserDto dto = null;

        try {
            log.info("Checking to see if our test user is already in the database.... testUser...... where are you....");

            dto = userWSClient.getUserByUsername("testUser4WS");
            if (dto == null) {
                log.info("Could not find our test user. Creating new user");
                dto = DtoTestFactory.createUserDto();
                dto = userWSClient.saveNewUser(dto);
            } else {
                log.info("Found user. No need to create new one");
            }

            assertNotNull("New userdto cannot be null", dto);

        } catch (Fault f) {
            log.error(f.getMessage(), f);
            assertTrue("Experienced web services fault", false);
        }

        return dto;
    }

    protected PhrContactDto createPhrContact(PatientDto patientDto) {
        PhrContactDto dto = DtoTestFactory.createPhrContactDto(patientDto.getPhr());
        List<PhrContactDto> list = null;

        try {
            log.info("Checking to see if our test phr contact is already in the database.");
            list = phrWSClient.getPhrContacts(patientDto.getPhr());

            if (list != null && list.size() > 0) {
                log.info("Found existing phr contact. No need to create new one");
                dto = list.get(0);
            } else {
                dto = phrWSClient.savePhrContact(dto);
            }
        } catch (Fault f) {
            log.error(f.getMessage(), f);
            assertTrue("Experienced web services fault", false);
        }

        return dto;
    }

    protected PractitionerDto createPractitioner(PatientDto patientDto) {
        PractitionerDto dto = DtoTestFactory.createPractitionerDto(patientDto.getPhr());
        List<PractitionerDto> list = null;

        try {
            log.info("Checking to see if our test practitioner is already in the database.");
            list = phrWSClient.getPractitioners(patientDto.getPhr());

            if (list != null && list.size() > 0) {
                log.info("Found existing practitioner. No need to create new one");
                dto = list.get(0);
            } else {
                dto = phrWSClient.savePractitioner(dto);
            }
        } catch (Fault f) {
            log.error(f.getMessage(), f);
            assertTrue("Experienced web services fault", false);
        }

        return dto;
    }

    private PatientDto getPatient(String userId) {
        PatientDto result = null;

        try {
            log.info("Retrieving patient dto from ws.");
            result = patientWSClient.getPrimaryPatientByUserId(userId);

            assertNotNull("PatientDto is null. It shouldn't be.", result);
        } catch (Fault f) {
            log.error(f.getMessage(), f);
            assertTrue("Experienced web services fault", false);
        }

        return result;
    }

    // Spring IoC
    @Resource(name = "userWSClient")
    protected UserWebService userWSClient;

    @Resource(name = "phrWSClient")
    protected PhrWebService phrWSClient;

    @Resource(name = "referenceDataWSClient")
    protected ReferenceDataWebService referenceDataWSClient;

    @Resource(name = "patientWSClient")
    protected PatientWebService patientWSClient;

}
