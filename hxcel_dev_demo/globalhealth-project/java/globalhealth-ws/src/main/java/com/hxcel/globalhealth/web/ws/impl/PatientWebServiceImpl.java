package com.hxcel.globalhealth.web.ws.impl;

import com.hxcel.globalhealth.web.ws.AbstractWebService;
import com.hxcel.globalhealth.web.ws.PatientWebService;
import com.hxcel.globalhealth.domain.patient.dto.PatientDto;
import com.hxcel.globalhealth.domain.patient.PatientManager;
import com.hxcel.globalhealth.domain.DomainException;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebParam;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.common.i18n.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: bjorn
 * Date: Sep 8, 2008
 * Time: 2:47:03 PM
 */
public class PatientWebServiceImpl extends AbstractWebService implements PatientWebService {
    @WebMethod
    @WebResult(name = "Patient")
    public PatientDto getPrimaryPatientByUserId(@WebParam(name = "userId")String userId) throws Fault {
        PatientDto result = null;

        try {
            result = patientManager.getPrimaryPatientDtoByUserId(userId);
        } catch (DomainException e) {
            Message m = new Message(e.getMessage(), resourceBundle, e.getParams());
            throw new Fault(m, e);
        }

        return result;
    }

    // Spring IoC
    @Autowired
    private PatientManager patientManager;
}
