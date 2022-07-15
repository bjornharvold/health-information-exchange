package com.hxcel.globalhealth.domain.patient;


import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.patient.dto.PatientDto;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

/**
 * User: bjorn
 * Date: Sep 8, 2008
 * Time: 1:52:03 PM
 */
public class PatientManagerTest extends AbstractIntegrationBaseTest {
    private final static Logger log = LoggerFactory.getLogger(PatientManagerTest.class);

    @Test
    public void testGetPrimaryPatientByUserId() {
        try {
            log.info("Retrieving primary patient entity by user id");
            PatientDto dto = patientManager.getPrimaryPatientDtoByUserId(getUser().getId());

            assertNotNull("Dto result is empty", dto);
            assertEquals("Dto id doesn't match up with registered id", getPatient().getId(), dto.getId());
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail(e.getMessage());
        }

    }

    // Spring IoC
    @Autowired
    private PatientManager patientManager;
}
