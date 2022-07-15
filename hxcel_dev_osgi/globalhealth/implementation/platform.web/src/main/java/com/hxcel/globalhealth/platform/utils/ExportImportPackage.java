package com.hxcel.globalhealth.platform.utils;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: May 9, 2009
 * Time: 11:09:06 PM
 * Responsibility:
 */
public class ExportImportPackage {
    private String pakkage;
    private List<String> uses;
    private String version;

    public String getPackage() {
        return pakkage;
    }

    public void setPackage(String pakkage) {
        this.pakkage = pakkage;
    }

    public List<String> getUses() {
        return uses;
    }

    public void setUses(List<String> uses) {
        this.uses = uses;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
