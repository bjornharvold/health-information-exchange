/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.phr.model;

import org.hibernate.annotations.*;
import org.jasypt.hibernate.type.EncryptedStringType;
import org.jasypt.hibernate.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate.type.EncryptedIntegerAsStringType;

import javax.persistence.Entity;
import java.io.Serializable;

import com.hxcel.globalhealth.domain.utils.hibernate.type.EnumUserType;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@TypeDefs({
        @TypeDef(name = "rstatus", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordStatusCd")
                }),
        @TypeDef(name = "rtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.common.model.enums.RecordTypeCd")
                }),
        @TypeDef(name = "entitytype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd")
                }),
        @TypeDef(name = "phrtype", typeClass = EnumUserType.class, parameters = {
        @Parameter(name = "enumClassName", value = "com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd")
                }),
        @TypeDef(
                name = "encryptedString",
                typeClass = EncryptedStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        ),
        @TypeDef(
                name = "encryptedBoolean",
                typeClass = EncryptedBooleanAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        ),
        @TypeDef(
                name = "encryptedInteger",
                typeClass = EncryptedIntegerAsStringType.class,
                parameters = {
                @Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
                        }
        )
        })
public class PhrFile extends AbstractPhrEntity implements Serializable {

    private String filename;
    private Integer fileSize;
    private String fileId;

    @Type(type = "encryptedString")
    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String value) {
        this.filename = value;
    }

    @Type(type = "encryptedInteger")
    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    @Type(type = "encryptedString")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
