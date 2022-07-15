package com.hxcel.globalhealth.domain.common.dto;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 7:24:04 PM
 */
public class ImageDto extends AbstractDto {
    private String filename;

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String value) {
        this.filename = value;
    }
}
