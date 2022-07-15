package com.hxcel.globalhealth.domain.common.dto;

import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Sep 11, 2008
 * Time: 1:34:09 PM
 * Generic status dto to report back to caller whether call succeeded or failed
 */
public class StatusDto implements Serializable {
    private String systemMessage;
    private String code;
    private Boolean success;
    private String entityId;
    private String status;
    private String[] codeParams;
    private String token;

    public StatusDto() {
    }

    public StatusDto(String systemMessage, String code, String[] codeParams, Boolean success, String entityId, String status) {
        this.systemMessage = systemMessage;
        this.success = success;
        this.entityId = entityId;
        this.status = status;
    }

    public StatusDto(String systemMessage, String code, String[] codeParams, Boolean success, String entityId, String token, String status) {
        this.systemMessage = systemMessage;
        this.success = success;
        this.entityId = entityId;
        this.status = status;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getCodeParams() {
        return codeParams;
    }

    public void setCodeParams(String[] codeParams) {
        this.codeParams = codeParams;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
