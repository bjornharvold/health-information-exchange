package com.hxcel.globalhealth.calendar.dto;

import com.hxcel.globalhealth.calendar.spec.UserServiceDocument;

/**
 * User: Bjorn Harvold
 * Date: Dec 30, 2008
 * Time: 12:04:09 PM
 * Responsibility:
 */
public class ChandlerUserServiceDocument implements UserServiceDocument {
    private String username;
    private String cmp;
    private String cmpContentType;
    private String dav;
    private String davContentType;
    private String davPrincipal;
    private String davPrincipalContentType;
    private String davCalendarHome;
    private String davCalendarHomeContentType;
    private String atom;
    private String atomContentType;
    private String mc;
    private String mcContentType;
    private String atomBase;
    private String atomBaseContentType;
    private String mcBase;
    private String mcBaseContentType;
    private String pimBase;
    private String pimBaseContentType;
    private String webcalBase;
    private String webcalBaseContentType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getDav() {
        return dav;
    }

    public void setDav(String dav) {
        this.dav = dav;
    }

    public String getDavPrincipal() {
        return davPrincipal;
    }

    public void setDavPrincipal(String davPrincipal) {
        this.davPrincipal = davPrincipal;
    }

    public String getDavCalendarHome() {
        return davCalendarHome;
    }

    public void setDavCalendarHome(String davCalendarHome) {
        this.davCalendarHome = davCalendarHome;
    }

    public String getAtom() {
        return atom;
    }

    public void setAtom(String atom) {
        this.atom = atom;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getAtomBase() {
        return atomBase;
    }

    public void setAtomBase(String atomBase) {
        this.atomBase = atomBase;
    }

    public String getMcBase() {
        return mcBase;
    }

    public void setMcBase(String mcBase) {
        this.mcBase = mcBase;
    }

    public String getPimBase() {
        return pimBase;
    }

    public void setPimBase(String pimBase) {
        this.pimBase = pimBase;
    }

    public String getWebcalBase() {
        return webcalBase;
    }

    public void setWebcalBase(String webcalBase) {
        this.webcalBase = webcalBase;
    }

    public String getCmpContentType() {
        return cmpContentType;
    }

    public void setCmpContentType(String cmpContentType) {
        this.cmpContentType = cmpContentType;
    }

    public String getDavContentType() {
        return davContentType;
    }

    public void setDavContentType(String davContentType) {
        this.davContentType = davContentType;
    }

    public String getDavPrincipalContentType() {
        return davPrincipalContentType;
    }

    public void setDavPrincipalContentType(String davPrincipalContentType) {
        this.davPrincipalContentType = davPrincipalContentType;
    }

    public String getDavCalendarHomeContentType() {
        return davCalendarHomeContentType;
    }

    public void setDavCalendarHomeContentType(String davCalendarHomeContentType) {
        this.davCalendarHomeContentType = davCalendarHomeContentType;
    }

    public String getAtomContentType() {
        return atomContentType;
    }

    public void setAtomContentType(String atomContentType) {
        this.atomContentType = atomContentType;
    }

    public String getMcContentType() {
        return mcContentType;
    }

    public void setMcContentType(String mcContentType) {
        this.mcContentType = mcContentType;
    }

    public String getAtomBaseContentType() {
        return atomBaseContentType;
    }

    public void setAtomBaseContentType(String atomBaseContentType) {
        this.atomBaseContentType = atomBaseContentType;
    }

    public String getMcBaseContentType() {
        return mcBaseContentType;
    }

    public void setMcBaseContentType(String mcBaseContentType) {
        this.mcBaseContentType = mcBaseContentType;
    }

    public String getPimBaseContentType() {
        return pimBaseContentType;
    }

    public void setPimBaseContentType(String pimBaseContentType) {
        this.pimBaseContentType = pimBaseContentType;
    }

    public String getWebcalBaseContentType() {
        return webcalBaseContentType;
    }

    public void setWebcalBaseContentType(String webcalBaseContentType) {
        this.webcalBaseContentType = webcalBaseContentType;
    }
}
