/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.common;

import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.*;

import com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.*;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd;
import com.hxcel.globalhealth.domain.professional.model.enums.WaitinglistPriorityCd;

import com.hxcel.globalhealth.web.ws.AbstractWebServiceBaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.Fault;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * User: Bjorn Harvold
 * Date: Jan 21, 2007
 * Time: 6:19:20 PM
 * As the WS tests need to be light weight and cannot be allowed to start up the whole context. This class does not extend out usual base test class
 */
public class ReferenceDataWebServiceTest extends AbstractWebServiceBaseTest {
    private final static Logger log = LoggerFactory.getLogger(UserWebServiceTest.class);

    @Test
    public void testGetAdvanceDirectiveTypes() {
        try {
            log.info("Retrieving advance directives...");
            AdvanceDirectiveTypeCd[] list = referenceDataWSClient.getAdvanceDirectiveTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving advance directives complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetAllergyReactionTypes() {
        try {
            log.info("Retrieving allergic reactions...");
            AllergyReactionTypeCd[] list = referenceDataWSClient.getAllergyReactionTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving allergic reactions complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetAllergyTypes() {
        try {
            log.info("Retrieving allergy types...");
            AllergyTypeCd[] list = referenceDataWSClient.getAllergyTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving allergy types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetAlternativeTreatmentTypes() {
        try {
            log.info("Retrieving alternative treatment types...");
            AlternativeTreatmentTypeCd[] list = referenceDataWSClient.getAlternativeTreatmentTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving alternative treatment types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetBloodTypes() {
        try {
            log.info("Retrieving blood types...");
            BloodTypeCd[] list = referenceDataWSClient.getBloodTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving blood types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetClientStatuses() {
        try {
            log.info("Retrieving client statuses...");
            PatientStatusCd[] list = referenceDataWSClient.getClientStatuses();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving client statuses complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetContactTypes() {
        try {
            log.info("Retrieving contact types...");
            ContactTypeCd[] list = referenceDataWSClient.getContactTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving contact types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetCountries() {
        try {
            log.info("Retrieving countries...");
            CountryCd[] list = referenceDataWSClient.getCountries();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving countries types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetCountry() {
        try {
            log.info("Retrieving country by type...");
            Country country = referenceDataWSClient.getCountry(CountryCd.UNITED_STATES.name());
            assertNotNull("Checking that we actually got a list back", country);
            assertEquals("Expecting united states country", CountryCd.UNITED_STATES.name(), country.getStatusCode());
            log.info("Retrieving contact types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetCreditcardTypes() {
        try {
            log.info("Retrieving creditcard types...");
            CreditcardTypeCd[] list = referenceDataWSClient.getCreditcardTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving creditcard types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetDentalExamReasonTypes() {
        try {
            log.info("Retrieving dental exam reason types...");
            DentalExamReasonTypeCd[] list = referenceDataWSClient.getDentalExamReasonTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving dental exam reason types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetDiagnoserTypes() {
        try {
            log.info("Retrieving diagnoser types...");
            DiagnoserTypeCd[] list = referenceDataWSClient.getDiagnoserTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving diagnoser types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetEmailTypes() {
        try {
            log.info("Retrieving email types...");
            EmailTypeCd[] list = referenceDataWSClient.getEmailTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving email types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetEmrStatuses() {
        try {
            log.info("Retrieving emr statuses...");
            EmrStatusCd[] list = referenceDataWSClient.getEmrStatuses();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving emr statuses complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetFrequencyTypes() {
        try {
            log.info("Retrieving frequency types...");
            FrequencyTypeCd[] list = referenceDataWSClient.getFrequencyTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving frequency types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetImmunizationTypes() {
        try {
            log.info("Retrieving immunization types...");
            ImmunizationTypeCd[] list = referenceDataWSClient.getImmunizationTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving immunization types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetInstantMessageTypes() {
        try {
            log.info("Retrieving instant message types...");
            InstantMessageTypeCd[] list = referenceDataWSClient.getInstantMessageTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving instant message types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetLanguages() {
        try {
            log.info("Retrieving languages...");
            LanguageCd[] list = referenceDataWSClient.getLanguages();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving languages complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetLocationTypes() {
        try {
            log.info("Retrieving location types...");
            LocationTypeCd[] list = referenceDataWSClient.getLocationTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving location types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetMaritalStatuses() {
        try {
            log.info("Retrieving marital statuses...");
            MaritalStatusCd[] list = referenceDataWSClient.getMaritalStatuses();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving marital statuses complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetMedicalConditionTypes() {
        try {
            log.info("Retrieving medical condition types...");
            MedicalConditionTypeCd[] list = referenceDataWSClient.getMedicalConditionTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving medical condition types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetMedicationFrequency() {
        try {
            log.info("Retrieving medication frequencies...");
            MedicationFrequencyCd[] list = referenceDataWSClient.getMedicationFrequencies();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving medication frequencies types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetMedicationTypes() {
        try {
            log.info("Retrieving medication types...");
            MedicationTypeCd[] list = referenceDataWSClient.getMedicationTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving medication types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetPhoneTypes() {
        try {
            log.info("Retrieving phone types...");
            PhoneTypeCd[] list = referenceDataWSClient.getPhoneTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving phone types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetProfessionalStatuses() {
        try {
            log.info("Retrieving professional statuses...");
            ProfessionalStatusCd[] list = referenceDataWSClient.getProfessionalStatuses();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving professional statuses complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetProfessionalTypes() {
        try {
            log.info("Retrieving instant message types...");
            ProfessionalTypeCd[] list = referenceDataWSClient.getProfessionalTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving professional types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetProfessions() {
        try {
            log.info("Retrieving professions...");
            ProfessionCd[] list = referenceDataWSClient.getProfessions();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving professions complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetRaces() {
        try {
            log.info("Retrieving races...");
            RaceCd[] list = referenceDataWSClient.getRaces();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving races complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetRecordTypes() {
        try {
            log.info("Retrieving record types...");
            RecordTypeCd[] list = referenceDataWSClient.getRecordTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving record types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetRelationshipStatuses() {
        try {
            log.info("Retrieving relationship statuses types...");
            RelationshipStatusCd[] list = referenceDataWSClient.getRelationshipStatuses();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving relationship statuses complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetRelativeConditionTypes() {
        try {
            log.info("Retrieving relative condition types...");
            RelativeConditionTypeCd[] list = referenceDataWSClient.getRelativeConditionTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving relative condition types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetRelativeTypes() {
        try {
            log.info("Retrieving relative types...");
            RelativeTypeCd[] list = referenceDataWSClient.getRelativeTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving relative types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetReligions() {
        try {
            log.info("Retrieving religions...");
            ReligionCd[] list = referenceDataWSClient.getReligions();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving religions complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetSalutations() {
        try {
            log.info("Retrieving salutions...");
            SalutationCd[] list = referenceDataWSClient.getSalutations();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving salutations complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetSexes() {
        try {
            log.info("Retrieving sexes...");
            SexCd[] list = referenceDataWSClient.getSexes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving sexes complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetSpecialtyTypes() {
        try {
            log.info("Retrieving specialty types...");
            SpecialtyTypeCd[] list = referenceDataWSClient.getSpecialtyTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving specialty types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetSurgeryTypes() {
        try {
            log.info("Retrieving surgery types...");
            SurgeryTypeCd[] list = referenceDataWSClient.getSurgeryTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving surgery types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetTreatmentTypes() {
        try {
            log.info("Retrieving treatment types...");
            TreatmentTypeCd[] list = referenceDataWSClient.getTreatmentTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving treatment types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetUserStatuses() {
        try {
            log.info("Retrieving user statuses...");
            UserStatusCd[] list = referenceDataWSClient.getUserStatuses();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving user statuses complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetContactLensTypes() {
        try {
            log.info("Retrieving contact lens types...");
            VisionContactLensTypeCd[] list = referenceDataWSClient.getVisionContactLensTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving contact lens types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetVisionExamTypes() {
        try {
            log.info("Retrieving vision exam types...");
            VisionExamReasonTypeCd[] list = referenceDataWSClient.getVisionExamReasonTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving vision exam types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetNonPrescriptionGlassesTypes() {
        try {
            log.info("Retrieving non prescription glasses types...");
            VisionNonPrescriptionGlassesTypeCd[] list = referenceDataWSClient.getVisionNonPrescriptionGlassesTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving non prescription glasses types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetPrescriptionGlassesTypes() {
        try {
            log.info("Retrieving prescription glasses types...");
            VisionPrescriptionGlassesTypeCd[] list = referenceDataWSClient.getVisionPrescriptionGlassesTypes();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving prescription glasses types complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }

    @Test
    public void testGetWaitinglistPriorities() {
        try {
            log.info("Retrieving waitinglist priorities types...");
            WaitinglistPriorityCd[] list = referenceDataWSClient.getWaitinglistPriorities();
            assertNotNull("Checking that we actually got a list back", list);
            assertTrue("Expecting > 0 items in list", list.length > 0);
            log.info("Retrieving waitinglist priorities complete!");
        } catch (Fault f) {
            log.error("Error:\n"+ f.getMessage());
            assertTrue("Something went wrong", false);
        }
    }
}