/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.impl;

import com.hxcel.globalhealth.web.ws.AbstractWebService;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.patient.model.enums.PatientStatusCd;
import com.hxcel.globalhealth.domain.common.ReferenceManager;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.*;
import com.hxcel.globalhealth.web.ws.ReferenceDataWebService;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnoserTypeCd;
import com.hxcel.globalhealth.domain.emr.model.enums.EmrStatusCd;
import com.hxcel.globalhealth.domain.phr.model.enums.*;
import com.hxcel.globalhealth.domain.professional.model.enums.*;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.RelationshipStatusCd;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.SpecialtyTypeCd;
import com.hxcel.globalhealth.domain.professional.model.enums.WaitinglistPriorityCd;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.logging.Logger;

/**
 * User: Bjorn Harvold
 * Date: Jan 12, 2007
 * Time: 3:04:16 PM
 */
@WebService(serviceName = "ReferenceDataWebService", endpointInterface = "com.hxcel.globalhealth.web.ws.ReferenceDataWebService")
public class ReferenceDataWebServiceImpl extends AbstractWebService implements ReferenceDataWebService {
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @WebMethod
    @WebResult(name = "AdvanceDirectiveType")
    public AdvanceDirectiveTypeCd[] getAdvanceDirectiveTypes() {
        AdvanceDirectiveTypeCd[] result = null;


        result = referenceManager.getAdvanceDirectiveTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "AllergyReactionType")
    public AllergyReactionTypeCd[] getAllergyReactionTypes() {
        AllergyReactionTypeCd[] result = null;

        result = referenceManager.getAllergyReactionTypes();

        return result;
    }

    @WebMethod
    @WebResult(name = "AllergyType")
    public AllergyTypeCd[] getAllergyTypes() {
        AllergyTypeCd[] result = null;

        result = referenceManager.getAllergyTypes();

        return result;
    }

    @WebMethod
    @WebResult(name = "AlternativeTreatmentType")
    public AlternativeTreatmentTypeCd[] getAlternativeTreatmentTypes() {
        AlternativeTreatmentTypeCd[] result = null;

        result = referenceManager.getAlternativeTreatmentTypes();

        return result;
    }

    @WebMethod
    @WebResult(name = "BloodType")
    public BloodTypeCd[] getBloodTypes() {
        BloodTypeCd[] result = null;

        result = referenceManager.getBloodTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "CaseStatuse")
    public EmrStatusCd[] getEmrStatuses() {
        EmrStatusCd[] result = null;


        result = referenceManager.getCaseStatuses();


        return result;
    }

    @WebMethod
    @WebResult(name = "ClientStatuse")
    public PatientStatusCd[] getClientStatuses() {
        PatientStatusCd[] result = null;


        result = referenceManager.getClientStatuses();


        return result;
    }

    @WebMethod
    @WebResult(name = "ContactType")
    public ContactTypeCd[] getContactTypes() {
        ContactTypeCd[] result = null;

        result = referenceManager.getContactTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "Countrie")
    public CountryCd[] getCountries() throws Fault {
        CountryCd[] result = null;

        result = referenceManager.getCountries();

        return result;
    }

    @WebMethod
    @WebResult(name = "Country")
    public Country getCountry(String code) throws Fault {
        Country result = null;

        try {
            result = referenceManager.getCountry(CountryCd.valueOf(code));
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), log, e.getParams());
            throw new Fault(m, e);
        }


        return result;
    }

    @WebMethod
    @WebResult(name = "CreditcardType")
    public CreditcardTypeCd[] getCreditcardTypes() {
        CreditcardTypeCd[] result = null;

        result = referenceManager.getCreditcardTypes();

        return result;
    }

    @WebMethod
    @WebResult(name = "DentalExamReasonType")
    public DentalExamReasonTypeCd[] getDentalExamReasonTypes() {
        DentalExamReasonTypeCd[] result = null;


        result = referenceManager.getDentalExamReasonTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "DiagnosisByType")
    public DiagnoserTypeCd[] getDiagnoserTypes() {
        DiagnoserTypeCd[] result = null;


        result = referenceManager.getDiagnosisByTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "EmailType")
    public EmailTypeCd[] getEmailTypes() {
        EmailTypeCd[] result = null;


        result = referenceManager.getEmailTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "FrequencyType")
    public FrequencyTypeCd[] getFrequencyTypes() {
        FrequencyTypeCd[] result = null;


        result = referenceManager.getFrequencyTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "InstantMessageType")
    public InstantMessageTypeCd[] getInstantMessageTypes() {
        InstantMessageTypeCd[] result = null;


        result = referenceManager.getInstantMessageTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "ImmunizationType")
    public ImmunizationTypeCd[] getImmunizationTypes() {
        ImmunizationTypeCd[] result = null;


        result = referenceManager.getImmunizationTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "Language")
    public LanguageCd[] getLanguages() {
        LanguageCd[] result = null;


        result = referenceManager.getLanguages();


        return result;
    }

    @WebMethod
    @WebResult(name = "LocationType")
    public LocationTypeCd[] getLocationTypes() {
        LocationTypeCd[] result = null;


        result = referenceManager.getLocationTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "MaritalStatuse")
    public MaritalStatusCd[] getMaritalStatuses() {
        MaritalStatusCd[] result = null;


        result = referenceManager.getMaritalStatuses();


        return result;
    }

    @WebMethod
    @WebResult(name = "MedicalConditionType")
    public MedicalConditionTypeCd[] getMedicalConditionTypes() {
        MedicalConditionTypeCd[] result = null;


        result = referenceManager.getMedicalConditionTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "MedicationFrequencie")
    public MedicationFrequencyCd[] getMedicationFrequencies() {
        MedicationFrequencyCd[] result = null;


        result = referenceManager.getMedicationFrequencies();


        return result;
    }

    @WebMethod
    @WebResult(name = "MedicationType")
    public MedicationTypeCd[] getMedicationTypes() {
        MedicationTypeCd[] result = null;


        result = referenceManager.getMedicationTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "PhoneType")
    public PhoneTypeCd[] getPhoneTypes() {
        PhoneTypeCd[] result = null;


        result = referenceManager.getPhoneTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "ProfessionalClientStatuse")
    public RelationshipStatusCd[] getRelationshipStatuses() {
        RelationshipStatusCd[] result = null;


        result = referenceManager.getProfessionalClientStatuses();


        return result;
    }

    @WebMethod
    @WebResult(name = "ProfessionalStatuse")
    public ProfessionalStatusCd[] getProfessionalStatuses() {
        ProfessionalStatusCd[] result = null;


        result = referenceManager.getProfessionalStatuses();


        return result;
    }

    @WebMethod
    @WebResult(name = "ProfessionalType")
    public ProfessionalTypeCd[] getProfessionalTypes() {
        ProfessionalTypeCd[] result = null;


        result = referenceManager.getProfessionalTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "ProfessionalWaitinglistPrioritie")
    public WaitinglistPriorityCd[] getWaitinglistPriorities() {
        WaitinglistPriorityCd[] result = null;


        result = referenceManager.getProfessionalWaitinglistPriorities();


        return result;
    }

    @WebMethod
    @WebResult(name = "Profession")
    public ProfessionCd[] getProfessions() {
        ProfessionCd[] result = null;


        result = referenceManager.getProfessions();


        return result;
    }

    @WebMethod
    @WebResult(name = "Race")
    public RaceCd[] getRaces() {
        RaceCd[] result = null;


        result = referenceManager.getRaces();


        return result;
    }

    @WebMethod
    @WebResult(name = "RecordType")
    public RecordTypeCd[] getRecordTypes() {
        RecordTypeCd[] result = null;


        result = referenceManager.getRecordTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "RelativeConditionType")
    public RelativeConditionTypeCd[] getRelativeConditionTypes() {
        RelativeConditionTypeCd[] result = null;


        result = referenceManager.getRelativeConditionTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "RelativeType")
    public RelativeTypeCd[] getRelativeTypes() {
        RelativeTypeCd[] result = null;


        result = referenceManager.getRelativeTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "Religion")
    public ReligionCd[] getReligions() {
        ReligionCd[] result = null;


        result = referenceManager.getReligions();


        return result;
    }

    @WebMethod
    @WebResult(name = "Salutation")
    public SalutationCd[] getSalutations() {
        SalutationCd[] result = null;


        result = referenceManager.getSalutations();


        return result;
    }

    @WebMethod
    @WebResult(name = "Sexe")
    public SexCd[] getSexes() {
        SexCd[] result = null;


        result = referenceManager.getSexes();


        return result;
    }

    @WebMethod
    @WebResult(name = "SpecialtyType")
    public SpecialtyTypeCd[] getSpecialtyTypes() {
        SpecialtyTypeCd[] result = null;


        result = referenceManager.getSpecialtyTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "SurgeryType")
    public SurgeryTypeCd[] getSurgeryTypes() {
        SurgeryTypeCd[] result = null;


        result = referenceManager.getSurgeryTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "TreatmentType")
    public TreatmentTypeCd[] getTreatmentTypes() {
        TreatmentTypeCd[] result = null;


        result = referenceManager.getTreatmentTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "UserStatue")
    public UserStatusCd[] getUserStatuses() {
        UserStatusCd[] result = null;


        result = referenceManager.getUserStatus();


        return result;
    }

    @WebMethod
    @WebResult(name = "VisionExamReasonType")
    public VisionExamReasonTypeCd[] getVisionExamReasonTypes() {
        VisionExamReasonTypeCd[] result = null;


        result = referenceManager.getVisionExamReasonTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "VisionNonPrescriptionGlassesType")
    public VisionNonPrescriptionGlassesTypeCd[] getVisionNonPrescriptionGlassesTypes() {
        VisionNonPrescriptionGlassesTypeCd[] result = null;


        result = referenceManager.getVisionNonPrescriptionGlassesTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "VisionPrescriptionGlassesType")
    public VisionPrescriptionGlassesTypeCd[] getVisionPrescriptionGlassesTypes() {
        VisionPrescriptionGlassesTypeCd[] result = null;


        result = referenceManager.getVisionPrescriptionGlassesTypes();


        return result;
    }

    @WebMethod
    @WebResult(name = "VisionContactLensType")
    public VisionContactLensTypeCd[] getVisionContactLensTypes() {
        VisionContactLensTypeCd[] result = null;


        result = referenceManager.getVisionContactLensTypes();


        return result;
    }

    // Spring IoC
    @Autowired
    private ReferenceManager referenceManager;

}
