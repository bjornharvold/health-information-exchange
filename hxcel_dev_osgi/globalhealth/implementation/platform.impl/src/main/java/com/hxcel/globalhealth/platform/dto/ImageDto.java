package com.hxcel.globalhealth.platform.dto;

import com.hxcel.globalhealth.common.dto.AbstractDto;
import com.hxcel.globalhealth.platform.spec.dto.IImageDto;

/**
 * User: bjorn
 * Date: Sep 7, 2008
 * Time: 7:24:04 PM
 */
public class ImageDto extends AbstractDto implements IImageDto {
    private String filename;

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String value) {
        this.filename = value;
    }
}
