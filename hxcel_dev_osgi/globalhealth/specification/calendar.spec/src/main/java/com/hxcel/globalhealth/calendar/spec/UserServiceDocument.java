package com.hxcel.globalhealth.calendar.spec;

/**
 * User: Bjorn Harvold
 * Date: Dec 27, 2008
 * Time: 7:32:17 PM
 * Responsibility:
 */
public interface UserServiceDocument {
    String getUsername();
    String getCmp();
    String getDav();
    String getDavPrincipal();
    String getDavCalendarHome();
    String getAtom();
    String getMc();
    String getAtomBase();
    String getMcBase();
    String getPimBase();
    String getWebcalBase();
    String getCmpContentType();
    String getDavContentType();
    String getDavPrincipalContentType();
    String getDavCalendarHomeContentType();
    String getAtomContentType();
    String getMcContentType();
    String getAtomBaseContentType();
    String getMcBaseContentType();
    String getPimBaseContentType();
    String getWebcalBaseContentType();

}
