/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain;

import com.hxcel.globalhealth.domain.common.dto.*;
import com.hxcel.globalhealth.domain.common.model.enums.*;
import com.hxcel.globalhealth.domain.phr.dto.*;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
import com.hxcel.globalhealth.domain.socialnetwork.dto.PublicProfileDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

/**
 * User: bjorn
 * Date: Dec 14, 2007
 * Time: 1:18:57 PM
 * Both the dao tests and the web service tests need to create dtos for testing. Use this class.
 */
public class DtoTestFactory {
    public static UserDto createUserDto() {
        UserDto dto = new UserDto();
        dto.setUsername("testUser4WS");
        dto.setCountry(CountryCd.UNITED_STATES);
        dto.setPassword("password");
        dto.setUserInfo(createUserInfoDto());

        return dto;
    }

    private static PublicProfileDto createPublicProfile() {
        PublicProfileDto dto = new PublicProfileDto();
        dto.setUserInfo(createUserInfoDto());

        return dto;
    }

    public static AdvanceDirectiveDto createAdvanceDirectiveDto(String phrId, String phrContactId) {
        AdvanceDirectiveDto ad = new AdvanceDirectiveDto();
        ad.setAdvanceDirectiveType(AdvanceDirectiveTypeCd.LIVING_WILL);
        ad.setEmergency(true);
        ad.setNotes("A note");
        ad.setRecordType(RecordTypeCd.PHR);
        ad.setRecordStatus(RecordStatusCd.ACTIVE);
        ad.setType(PhrTypeCd.ADVANCE_DIRECTIVE);
        ad.setPhr(phrId);
        ad.setPhrContact(phrContactId);

        return ad;
    }

    public static PhrContactDto createPhrContactDto(String phrId) {
        PhrContactDto contact = new PhrContactDto();
        contact.setEmergency(true);
        contact.setPhr(phrId);
        contact.setRecordType(RecordTypeCd.PHR);
        contact.setRecordCreator(null);
        contact.setType(PhrTypeCd.CONTACT);
        contact.setRecordStatus(RecordStatusCd.ACTIVE);
        contact.setContactType(ContactTypeCd.BROTHER);
        contact.setContact(createContactDto("phrcontact"));
        return contact;
    }

    public static AllergyDto createAllergyDto(String phrId) {
        AllergyDto dto = new AllergyDto();
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.ALLERGY);
        dto.setAllergyTypeCd(AllergyTypeCd.ANIMAL_DANDER);
        dto.setNotes("NOTE TEST");
        dto.setAllergyReactionTypeCd(AllergyReactionTypeCd.ABDOMINAL_CRAMPS);
        dto.setFrequencyTypeCd(FrequencyTypeCd.CONTINUAL);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setRecordCreator(null);
        dto.setEmergency(true);

        return dto;
    }

    public static AlternativeTreatmentDto createAlternativeTreatmentDto(String phrId) {
        AlternativeTreatmentDto dto = new AlternativeTreatmentDto();
        dto.setAlternativeTreatmentTypeCd(AlternativeTreatmentTypeCd.YOGA);
        dto.setNotes("NOTE TEST");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.ALTERNATIVE_TREATMENT);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setRecordCreator(null);
        dto.setEmergency(true);

        return dto;
    }

    public static DentalDto createDentalDto(String phrId) {
        DentalDto dto = new DentalDto();
        dto.setDentalExamReasonTypeCd(DentalExamReasonTypeCd.ABSCESS_PAIN);
        dto.setNotes("NOTE TEST");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.DENTAL);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setRecordCreator(null);
        dto.setEmergency(true);

        return dto;
    }

    public static EmployerDto createEmployerDto(String phrId) {
        EmployerDto dto = new EmployerDto();
        dto.setCompanyName("Company");
        dto.setEndDate(new Date());
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.EMPLOYER);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        dto.setUserInfo(createUserInfoDto());
        dto.setPhr(phrId);

        List<LocationDto> ls = new ArrayList<LocationDto>();
        ls.add(createLocationDto());
        dto.setLocations(ls);
        dto.setProfessionCd(ProfessionCd.EDUCATION_COUNSELOR);
        dto.setStartDate(new Date());

        return dto;
    }

    public static LocationDto createLocationDto() {
        LocationDto location = new LocationDto();
        location.setAddress1("1");
        location.setAddress2("2");
        location.setAddress3("3");
        location.setCity("city");
        location.setState("state");
        location.setZip("zip");
        location.setCountryCd(CountryCd.UNITED_STATES);

        return location;
    }

    public static EmailDto createEmailDto() {
        EmailDto email = new EmailDto();
        email.setEmail("hello@hello.com");

        return email;
    }

    public static PhoneDto createPhoneDto() {
        PhoneDto phone = new PhoneDto();
        phone.setNumber("5551212");

        return phone;
    }

    public static InstantMessageDto createInstantMessageDto() {
        InstantMessageDto im = new InstantMessageDto();
        im.setNickname("wtfr5");

        return im;
    }

    public static FamilyHistoryDto createFamilyHistoryDto(String phrId) {
        FamilyHistoryDto dto = new FamilyHistoryDto();
        dto.setNotes("Notes");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.FAMILY_HISTORY);
        dto.setPhr(phrId);
        dto.setAlive(Boolean.TRUE);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);
        dto.setRelativeConditionTypeCd(RelativeConditionTypeCd.ALCOHOL_DEPENDENCE);
        dto.setRelativeTypeCd(RelativeTypeCd.BROTHER);

        return dto;
    }

    public static HospitalizedDto createHospitalizedDto(String phrId) {
        HospitalizedDto dto = new HospitalizedDto();
        dto.setNotes("Notes");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.HOSPITALIZED);
        dto.setPhr(phrId);
        dto.setStartDate(new Date());
        dto.setEndDate(new Date());
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static ImmunizationDto createImmunizationDto(String phrId) {
        ImmunizationDto dto = new ImmunizationDto();
        dto.setNotes("Notes");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.IMMUNIZATION);
        dto.setPhr(phrId);
        dto.setDate(new Date());
        dto.setImmunizationTypeCd(ImmunizationTypeCd.DTP1);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static InsuranceDto createInsuranceDto(String phrId, String phrContactId) {
        InsuranceDto dto = new InsuranceDto();
        dto.setNotes("Notes");
        dto.setPhrContact(phrContactId);
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.INSURANCE);
        dto.setPhr(phrId);
        dto.setInsuranceGroupNo("Number");
        dto.setInsuranceNo("Number");
        dto.setName("This name");
        dto.setInsuranceType("Insurance type");
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static MedicalConditionDto createMedicalConditionDto(String phrId, String practitionerId) {
        MedicalConditionDto dto = new MedicalConditionDto();
        dto.setNotes("Notes");
        dto.setPractitioner(practitionerId);
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.MEDICAL_CONDITION);
        dto.setPhr(phrId);
        dto.setCurrentMedicalCondition(Boolean.FALSE);
        dto.setDiagnosisDate(new Date());
        dto.setEndDate(new Date());
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);
        dto.setMedicalConditionTypeCd(MedicalConditionTypeCd.ADDISON_DISEASE);

        return dto;
    }

    public static MedicationDto createMedicationDto(String phrId, String practitionerId, String phrContactId) {
        MedicationDto dto = new MedicationDto();
        dto.setPhrContact(phrContactId);
        dto.setPractitioner(practitionerId);
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.MEDICATION);
        dto.setPhr(phrId);
        dto.setMedicationTypeCd(MedicationTypeCd.TEST1);
        dto.setFrequency(MedicationFrequencyCd.AFTER_MEALS);
        dto.setConditionType(MedicalConditionTypeCd.ADDISON_DISEASE);
        dto.setMedicationDosage("HUUUGE!!");
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static PersonalProfileDto createPersonalProfileDto(String phrId) {
        PersonalProfileDto dto = new PersonalProfileDto();
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.PERSONAL_PROFILE);
        dto.setEmergency(true);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setPhr(phrId);
        dto.setRaceCd(RaceCd.ABORIGINAL);

        return dto;
    }

    public static PhrFileDto createPhrFileDto(String phrId) {
        PhrFileDto dto = new PhrFileDto();
        dto.setNotes("Notes");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.FILES);
        dto.setPhr(phrId);
        dto.setFilename("filename.jpg");
        dto.setFileSize(245);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static PhrLocationDto createPhrLocationDto(String phrId) {
        PhrLocationDto dto = new PhrLocationDto();
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.LOCATION);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);
        dto.setAddress1("1");
        dto.setAddress2("2");
        dto.setAddress3("3");
        dto.setCity("city");
        dto.setState("state");
        dto.setZip("zip");
        dto.setCountry(CountryCd.UNITED_STATES);
 
        return dto;
    }

    public static PractitionerDto createPractitionerDto(String phrId) {
        PractitionerDto dto = new PractitionerDto();
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setPhr(phrId);
        dto.setTitle("Mr Doctor");
        dto.setType(PhrTypeCd.PRACTITIONER);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        dto.setContact(createContactDto("practitioner"));

        return dto;
    }

    public static SurgeryDto createSurgeryDto(String phrId, String practitionerId) {
        SurgeryDto dto = new SurgeryDto();
        dto.setNotes("Notes");
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.SURGERY);
        dto.setPhr(phrId);
        dto.setPractitioner(practitionerId);
        dto.setSurgeryType(SurgeryTypeCd.ABDOMEN_ABDOMINAL_ANEURYSM_REPAIR);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static TravelImmunizationDto createTravelImmunizationDto(String phrId) {
        TravelImmunizationDto dto = new TravelImmunizationDto();
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.TRAVEL_IMMUNIZATION);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static VisionContactLensesDto createVisionContactLensesDto(String phrId, String practitionerId) {
        VisionContactLensesDto dto = new VisionContactLensesDto();
        dto.setEndDate(new Date());
        dto.setStartDate(new Date());
        dto.setPractitioner(practitionerId);
        dto.setLensType(VisionContactLensTypeCd.COLORTINT);
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.VISION_LENS);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static VisionExamDto createVisionExamDto(String phrId, String practitionerId) {
        VisionExamDto dto = new VisionExamDto();
        dto.setExamDate(new Date());
        dto.setNotes("Notes");
        dto.setPractitioner(practitionerId);
        dto.setReasonType(VisionExamReasonTypeCd.CATARACT);
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.VISION_EXAM);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    public static VisionGlassesDto createVisionGlassesDto(String phrId, String practitionerId) {
        VisionGlassesDto dto = new VisionGlassesDto();
        dto.setNotes("Notes");
        dto.setPractitioner(practitionerId);
        dto.setPrescriptionType(VisionPrescriptionGlassesTypeCd.ATHLETIC_EYEWEAR);
        dto.setRecordType(RecordTypeCd.PHR);
        dto.setType(PhrTypeCd.VISION_GLASSES);
        dto.setPhr(phrId);
        dto.setRecordStatus(RecordStatusCd.ACTIVE);
        dto.setEmergency(true);

        return dto;
    }

    private static ContactDto createContactDto(String contactType) {
        ContactDto dto = new ContactDto();
        dto.setContactTypeOther(contactType);
        dto.setUserInfo(createUserInfoDto());
        dto.addLocation(createLocationDto());

        return dto;
    }

    private static UserInfoDto createUserInfoDto() {
        UserInfoDto dto = new UserInfoDto();
        dto.setFirstName(RandomStringUtils.randomAlphabetic(7));
        dto.setLastName(RandomStringUtils.randomAlphabetic(7));
        dto.setMiddleName(RandomStringUtils.randomAlphabetic(7));
        dto.setSalutationCd(SalutationCd.MR);
        dto.setSexCd(SexCd.MALE);

        ImageDto iDto = new ImageDto();
        iDto.setFilename(RandomStringUtils.randomAlphabetic(10));
        dto.setImage(iDto);

        for (int i = 0; i < 5; i++) {
            dto.addEmail(createEmailDto());
        }
        for (int i = 0; i < 5; i++) {
            dto.addInstantMessage(createInstantMessageDto());
        }
        for (int i = 0; i < 5; i++) {
            dto.addPhone(createPhoneDto());
        }

        return dto;
    }

}
