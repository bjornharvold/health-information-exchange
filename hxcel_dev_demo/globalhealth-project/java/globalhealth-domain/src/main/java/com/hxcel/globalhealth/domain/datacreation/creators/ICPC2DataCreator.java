/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.datacreation.creators;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.common.ReferenceManager;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreatorException;
import com.hxcel.globalhealth.domain.emr.EMRException;
import com.hxcel.globalhealth.domain.emr.JournalManager;
import com.hxcel.globalhealth.domain.emr.model.Diagnosis;
import com.hxcel.globalhealth.domain.emr.model.enums.DiagnosisTypeCd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * It expects a regular sql file to parse and creates icd-10 objects for.
 * It expects a line like this:
 * 138, 138, 'Y410', 'Sulfonamides', 'Sulfonamides', 1, '2004-07-16 11:44:15.783371', false, NULL, '2006-07-01 20:44:06.970059'
 * IMPORTANT: This wil only run on a clean database as it ony checks to see if there is something in the database from before
 * and wont proceed if there is.
 */
public class ICPC2DataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(ICPC2DataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private static final String DIAGNOSIS_VERSION = "revision_2E";

    public void create() throws DataCreatorException {

        try {
            Integer count = journalManager.getDiagnosisCount(DiagnosisTypeCd.ICPC2);

            if (count == 0) {
                if (file.exists()) {
                    processCreation();
                } else {
                    throw new DataCreatorException("File could not be found");
                }
            } else {
                log.info("Database already contains records. Will not continue");
            }
        } catch (EMRException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }

        log.info("Populated " + populated + " icpc-2 records in db");
        log.info("Omitted " + omitted + " icpc-2 from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            persist(parseXMLFile());

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private List<Diagnosis> parseXMLFile() throws IOException, DataCreatorException {
        List<Diagnosis> result = new ArrayList<Diagnosis>();

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> diagnoses = document.selectNodes("//icpc2/diagnosis");
            Node nCountry = document.selectSingleNode("//icpc2");
            String countryS = nCountry.valueOf("@country");
            CountryCd c = CountryCd.valueOf(countryS);
            Country country = null;

            if (c != null) {
                country = referenceManager.getCountry(c);

            } else {
                log.error("Cannot find country in XML!! Exiting so we can fix the problem. " + countryS);
                throw new DataCreatorException("Cannot find country!! Exiting so we can fix the problem.  " + countryS);
            }
            for (Element e : diagnoses) {
                List<Element> cells = e.elements();
                Diagnosis diagnosis;


                if (cells.size() == 2) {
                    String code = cells.get(0).getTextTrim();
                    String name = cells.get(1).getTextTrim();

                    diagnosis = new Diagnosis();
                    diagnosis.setCode(code);
                    diagnosis.setName(name);
                    diagnosis.setCountryCd(country);

                    diagnosis.setDiagnosisVersion(DIAGNOSIS_VERSION);
                    diagnosis.setDiagnosisType(DiagnosisTypeCd.ICPC2);

                    result.add(diagnosis);
                }
            }

        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. " + e.getMessage(), e);
            throw new DataCreatorException("Couldn't parse XML document. " + e.getMessage(), e);
        } catch (DomainException e) {
            log.error("Cannot find country!! Exiting so we can fix the problem." + e.getMessage(), e);
            throw new DataCreatorException("Cannot find country!! Exiting so we can fix the problem. " + e.getMessage(), e);
        }

        return result;
    }

    /**
     * Saves the country if country not already in db
     *
     * @param diagnoses
     * @throws com.hxcel.globalhealth.domain.datacreation.DataCreatorException
     *
     */
    private void persist(List<Diagnosis> diagnoses) throws DataCreatorException {
        try {

            log.info("Persisting " + diagnoses.size() + " records. This might take some time the first time you populate the db.");
            // blow away existing icd entries  only way
            journalManager.saveOrUpdateAllDiagnoses(diagnoses);
            populated = diagnoses.size();

        } catch (EMRException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    // Spring IoC
    private Resource file;

    @Autowired
    private ReferenceManager referenceManager;

    @Autowired
    private JournalManager journalManager;

    @Required
    public void setFile(Resource sqlFile) {
        this.file = sqlFile;
    }

}