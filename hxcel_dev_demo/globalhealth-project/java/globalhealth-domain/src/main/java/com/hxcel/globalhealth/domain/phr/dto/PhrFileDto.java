/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.dto;

import java.io.Serializable;

public class PhrFileDto extends AbstractPhrDto implements Serializable {

    private Integer fileSize;
    private String filename;
    private String fileId;

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String value) {
        this.filename = value;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
