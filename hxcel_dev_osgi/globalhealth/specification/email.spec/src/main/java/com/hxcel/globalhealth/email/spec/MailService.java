package com.hxcel.globalhealth.email.spec;

import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Apr 24, 2007
 * Time: 11:50:44 AM
 */
public interface MailService {
    void sendPlainEmail(String template, Map<String, Object> params) throws MailServiceException;

    void sendMIMEEmail(String template, Map<String, Object> params, Map<String, String> imageAssets,
                       Map<String, String> attachments, Map<String, String> headers) throws MailServiceException;

    Boolean isAvailable();
}
