/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.web.ws.impl;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.phr.PhrManager;
import com.hxcel.globalhealth.domain.phr.dto.*;
import com.hxcel.globalhealth.web.ws.PhrWebService;
import com.hxcel.globalhealth.web.ws.AbstractWebService;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.interceptor.Fault;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: Bjorn Harvold
 * Date: Jan 22, 2007
 * Time: 4:23:12 PM
 */
@WebService(serviceName = "PhrWebService", endpointInterface = "com.hxcel.globalhealth.web.ws.PhrWebService")
public class PhrWebServiceImpl extends AbstractWebService implements PhrWebService {
    public final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @WebMethod
    @WebResult(name = "PhrLocations")
    public List<PhrLocationDto> getPhrLocations(@WebParam(name = "phrId")String phrId) throws Fault {
        List<PhrLocationDto> result = null;

        try {
            result = phrManager.getPhrLocations(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "PhrLocation")
    public PhrLocationDto savePhrLocation(@WebParam(name = "PhrLocation")PhrLocationDto value) throws Fault {

        try {
            value = phrManager.savePhrLocation(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        log.info("Save / Update successful");

        return value;
    }

    @WebMethod
    public String removePhrLocation(@WebParam(name = "id")String phrLocationId) throws Fault {

        if (StringUtils.isBlank(phrLocationId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removePhrLocation(phrLocationId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return phrLocationId;
    }

    @WebMethod
    @WebResult(name = "AdvanceDirectives")
    public List<AdvanceDirectiveDto> getAdvanceDirectives(@WebParam(name = "phrId") String phrId) throws Fault {
        List<AdvanceDirectiveDto> result = null;

        try {
            result = phrManager.getAdvanceDirectives(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "AdvanceDirective")
    public AdvanceDirectiveDto saveAdvanceDirective(@WebParam(name = "value") AdvanceDirectiveDto value) throws Fault {
        AdvanceDirectiveDto result = null;

        try {
            result = phrManager.saveAdvanceDirective(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeAdvanceDirective(@WebParam(name = "id")String advanceDirectiveId) throws Fault {

        if (StringUtils.isBlank(advanceDirectiveId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }
        try {
            phrManager.removeAdvanceDirective(advanceDirectiveId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return advanceDirectiveId;
    }

    @WebMethod
    @WebResult(name = "Allergies")
    public List<AllergyDto> getAllergies(@WebParam(name = "phrId") String phrId) throws Fault {
        List<AllergyDto> result = null;

        try {
            result = phrManager.getAllergies(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Allergy")
    public AllergyDto saveAllergy(@WebParam(name = "value")AllergyDto value) throws Fault {

        try {
            value = phrManager.saveAllergy(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeAllergy(@WebParam(name = "id")String allergyId) throws Fault {

        if (StringUtils.isBlank(allergyId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }
        try {
            phrManager.removeAllergy(allergyId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return allergyId;
    }

    @WebMethod
    @WebResult(name = "AlternativeTreatment")
    public List<AlternativeTreatmentDto> getAlternativeTreatments(@WebParam(name = "phrId") String phrId) throws Fault {
        List<AlternativeTreatmentDto> result = null;

        try {
            result = phrManager.getAlternativeTreatments(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "AlternativeTreatment")
    public AlternativeTreatmentDto saveAlternativeTreatment(@WebParam(name = "value") AlternativeTreatmentDto value) throws Fault {
        AlternativeTreatmentDto result = null;

        try {
            result = phrManager.saveAlternativeTreatment(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeAlternativeTreatment(@WebParam(name = "id")String alternativeTreatmentId) throws Fault {

        if (StringUtils.isBlank(alternativeTreatmentId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeAlternativeTreatment(alternativeTreatmentId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return alternativeTreatmentId;
    }

    @WebMethod
    @WebResult(name = "Dentals")
    public List<DentalDto> getDentals(@WebParam(name = "phrId") String phrId) throws Fault {
        List<DentalDto> result = null;

        try {
            result = phrManager.getDentals(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Dental")
    public DentalDto saveDental(@WebParam(name = "value") DentalDto value) throws Fault {
        DentalDto result;

        try {
            result = phrManager.saveDental(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeDental(@WebParam(name = "id")String dentalId) throws Fault {

        if (StringUtils.isBlank(dentalId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeDental(dentalId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return dentalId;
    }

    @WebMethod
    @WebResult(name = "Employers")
    public List<EmployerDto> getEmployers(@WebParam(name = "phrId") String phrId) throws Fault {
        List<EmployerDto> result = null;

        try {
            result = phrManager.getEmployers(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Employer")
    public EmployerDto saveEmployer(@WebParam(name = "value") EmployerDto value) throws Fault {

        try {
            value = phrManager.saveEmployer(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeEmployer(@WebParam(name = "id")String employerId) throws Fault {

        if (StringUtils.isBlank(employerId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeEmployer(employerId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return employerId;
    }

    @WebMethod
    @WebResult(name = "FamilyHistories")
    public List<FamilyHistoryDto> getFamilyHistories(@WebParam(name = "phrId") String phrId) throws Fault {
        List<FamilyHistoryDto> result = null;

        try {
            result = phrManager.getFamilyHistories(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "FamilyHistory")
    public FamilyHistoryDto saveFamilyHistory(@WebParam(name = "value") FamilyHistoryDto value) throws Fault {

        try {
            value = phrManager.saveFamilyHistory(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeFamilyHistory(@WebParam(name = "id")String familyHistoryId) throws Fault {

        if (StringUtils.isBlank(familyHistoryId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeFamilyHistory(familyHistoryId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return familyHistoryId;

    }

    @WebMethod
    @WebResult(name = "Hospitalizeds")
    public List<HospitalizedDto> getHospitalizeds(@WebParam(name = "phrId") String phrId) throws Fault {
        List<HospitalizedDto> result = null;

        try {
            result = phrManager.getHospitalizeds(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Hospitalized")
    public HospitalizedDto saveHospitalized(@WebParam(name = "value") HospitalizedDto value) throws Fault {

        try {
            value = phrManager.saveHospitalized(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeHospitalized(@WebParam(name = "id")String hospitalizedId) throws Fault {

        if (StringUtils.isBlank(hospitalizedId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeHospitalized(hospitalizedId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return hospitalizedId;

    }

    @WebMethod
    @WebResult(name = "Immunizations")
    public List<ImmunizationDto> getImmunizations(@WebParam(name = "phrId") String phrId) throws Fault {
        List<ImmunizationDto> result = null;

        try {
            result = phrManager.getImmunizations(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Immunization")
    public ImmunizationDto saveImmunization(@WebParam(name = "value") ImmunizationDto value) throws Fault {

        try {
            value = phrManager.saveImmunization(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeImmunization(@WebParam(name = "id")String immunizationId) throws Fault {

        if (StringUtils.isBlank(immunizationId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeImmunization(immunizationId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return immunizationId;

    }

    @WebMethod
    @WebResult(name = "Insurances")
    public List<InsuranceDto> getInsurances(@WebParam(name = "phrId") String phrId) throws Fault {
        List<InsuranceDto> result = null;

        try {
            result = phrManager.getInsurances(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Insurance")
    public InsuranceDto saveInsurance(@WebParam(name = "value") InsuranceDto value) throws Fault {
        InsuranceDto result = null;

        try {
            result = phrManager.saveInsurance(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeInsurance(@WebParam(name = "id")String insuranceId) throws Fault {

        if (StringUtils.isBlank(insuranceId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeInsurance(insuranceId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return insuranceId;
    }

    @WebMethod
    @WebResult(name = "MedicalConditions")
    public List<MedicalConditionDto> getMedicalConditions(@WebParam(name = "phrId") String phrId) throws Fault {
        List<MedicalConditionDto> result = null;

        try {
            result = phrManager.getMedicalConditions(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "MedicalCondition")
    public MedicalConditionDto saveMedicalCondition(@WebParam(name = "value") MedicalConditionDto value) throws Fault {

        MedicalConditionDto result = null;

        try {
            result = phrManager.saveMedicalCondition(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeMedicalCondition(@WebParam(name = "id")String medicalConditionId) throws Fault {

        if (StringUtils.isBlank(medicalConditionId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeMedicalCondition(medicalConditionId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return medicalConditionId;

    }

    @WebMethod
    @WebResult(name = "Medications")
    public List<MedicationDto> getMedications(@WebParam(name = "phrId") String phrId) throws Fault {
        List<MedicationDto> result = null;

        try {
            result = phrManager.getMedications(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Medication")
    public MedicationDto saveMedication(@WebParam(name = "value") MedicationDto value) throws Fault {

        try {
            value = phrManager.saveMedication(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeMedication(@WebParam(name = "id")String medicationId) throws Fault {

        if (StringUtils.isBlank(medicationId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeMedication(medicationId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return medicationId;
    }

    @WebMethod
    @WebResult(name = "PhrContacts")
    public List<PhrContactDto> getPhrContacts(@WebParam(name = "phrId") String phrId) throws Fault {
        List<PhrContactDto> result = null;

        try {
            result = phrManager.getPhrContacts(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "PhrContact")
    public PhrContactDto savePhrContact(@WebParam(name = "value")PhrContactDto value) throws Fault {

        try {
            value = phrManager.savePhrContact(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removePhrContact(@WebParam(name = "id")String phrContactId) throws Fault {

        if (StringUtils.isBlank(phrContactId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removePhrContact(phrContactId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return phrContactId;
    }

    @WebMethod
    @WebResult(name = "PersonalProfile")
    public PersonalProfileDto getPersonalProfile(@WebParam(name = "phrId") String phrId) throws Fault {
        PersonalProfileDto result = null;

        try {
            result = phrManager.getPersonalProfile(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "PersonalProfile")
    public PersonalProfileDto savePersonalProfile(@WebParam(name = "value") PersonalProfileDto value) throws Fault {

        try {
            value = phrManager.savePersonalProfile(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    @WebResult(name = "PhrFiles")
    public List<PhrFileDto> getPhrFiles(@WebParam(name = "phrId") String phrId) throws Fault {
        List<PhrFileDto> result = null;

        try {
            result = phrManager.getFiles(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "PhrFile")
    public PhrFileDto savePhrFile(@WebParam(name = "value") PhrFileDto value) throws Fault {

        try {
            value = phrManager.saveFile(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removePhrFile(@WebParam(name = "phrFileId")String phrFileId, @WebParam(name = "phrId")String phrId, @WebParam(name = "filename")String filename) throws Fault {

        if (StringUtils.isBlank(phrFileId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            // remove it from the db
            phrManager.removePhrFile(phrFileId, phrId, filename);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return phrFileId;
    }

    @WebMethod
    @WebResult(name = "Practitioners")
    public List<PractitionerDto> getPractitioners(@WebParam(name = "phrId") String phrId) throws Fault {
        List<PractitionerDto> result = null;

        try {
            result = phrManager.getPractitioners(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Practitioner")
    public PractitionerDto savePractitioner(@WebParam(name = "value") PractitionerDto value) throws Fault {

        try {
            value = phrManager.savePractitioner(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removePractitioner(@WebParam(name = "id")String practitionerId) throws Fault {

        if (StringUtils.isBlank(practitionerId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removePractitioner(practitionerId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return practitionerId;
    }

    @WebMethod
    @WebResult(name = "Surgeries")
    public List<SurgeryDto> getSurgeries(@WebParam(name = "phrId") String phrId) throws Fault {
        List<SurgeryDto> result = null;

        try {
            result = phrManager.getSurgeries(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "Surgery")
    public SurgeryDto saveSurgery(@WebParam(name = "value") SurgeryDto value) throws Fault {
        SurgeryDto result = null;

        try {
            result = phrManager.saveSurgery(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeSurgery(@WebParam(name = "id")String surgeryId) throws Fault {

        if (StringUtils.isBlank(surgeryId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeSurgery(surgeryId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return surgeryId;
    }

    @WebMethod
    @WebResult(name = "TravelImmunizations")
    public List<TravelImmunizationDto> getTravelImmunizations(@WebParam(name = "phrId") String phrId) throws Fault {
        List<TravelImmunizationDto> result = null;

        try {
            result = phrManager.getTravelImmunizations(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "TravelImmunization")
    public TravelImmunizationDto saveTravelImmunization(@WebParam(name = "value") TravelImmunizationDto value) throws Fault {

        try {
            value = phrManager.saveTravelImmunization(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return value;
    }

    @WebMethod
    public String removeTravelImmunization(@WebParam(name = "id")String travelImmunizationId) throws Fault {

        if (StringUtils.isBlank(travelImmunizationId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeTravelImmunization(travelImmunizationId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return travelImmunizationId;
    }

    @WebMethod
    @WebResult(name = "VisionContactLenses")
    public List<VisionContactLensesDto> getVisionContactLenses(@WebParam(name = "phrId") String phrId) throws Fault {
        List<VisionContactLensesDto> result = null;

        try {
            result = phrManager.getVisionContactLenses(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "VisionContactLenses")
    public VisionContactLensesDto saveVisionContactLenses(@WebParam(name = "value") VisionContactLensesDto value) throws Fault {
        VisionContactLensesDto result = null;

        try {
            result = phrManager.saveVisionContactLenses(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeVisionContactLenses(@WebParam(name = "id")String visionContactLensesId) throws Fault {

        if (StringUtils.isBlank(visionContactLensesId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeVisionContactLenses(visionContactLensesId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return visionContactLensesId;
    }

    @WebMethod
    @WebResult(name = "VisionEyeExams")
    public List<VisionExamDto> getVisionExams(@WebParam(name = "phrId") String phrId) throws Fault {
        List<VisionExamDto> result = null;

        try {
            result = phrManager.getVisionExams(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "VisionEyeExam")
    public VisionExamDto saveVisionExam(@WebParam(name = "value")VisionExamDto value) throws Fault {

        VisionExamDto result = null;

        try {
            result = phrManager.saveVisionExam(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeVisionExam(@WebParam(name = "id")String visionExamId) throws Fault {

        if (StringUtils.isBlank(visionExamId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeVisionExam(visionExamId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return visionExamId;
    }

    @WebMethod
    @WebResult(name = "VisionGlasses")
    public List<VisionGlassesDto> getVisionGlasses(@WebParam(name = "phrId") String phrId) throws Fault {
        List<VisionGlassesDto> result = null;

        try {
            result = phrManager.getVisionGlasses(phrId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    @WebResult(name = "VisionGlasses")
    public VisionGlassesDto saveVisionGlasses(@WebParam(name = "value")VisionGlassesDto value) throws Fault {
        VisionGlassesDto result = null;

        try {
            result = phrManager.saveVisionGlasses(value);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    @WebMethod
    public String removeVisionGlasses(@WebParam(name = "id")String visionGlassesId) throws Fault {

        if (StringUtils.isBlank(visionGlassesId)) {
            Message m = new Message("id cannot be null", resourceBundle, null);
            throw new Fault(m);
        }

        try {
            phrManager.removeVisionGlasses(visionGlassesId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return visionGlassesId;
    }
    
    // Spring IoC
    @Autowired
    private PhrManager phrManager;

}
