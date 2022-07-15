package com.hxcel.globalhealth.web.ws;

import com.hxcel.globalhealth.domain.patient.dto.PatientDto;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebParam;

import org.apache.cxf.interceptor.Fault;

/**
 * User: bjorn
 * Date: Sep 8, 2008
 * Time: 2:42:07 PM
 */
@WebService
public interface PatientWebService {
    @WebMethod
    @WebResult(name = "Patient")
    PatientDto getPrimaryPatientByUserId(@WebParam(name = "userId") String userId) throws Fault;
}
