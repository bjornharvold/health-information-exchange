package com.hxcel.globalhealth.service.cms;

import java.util.Map;
import java.io.InputStream;
import java.io.File;

/**
 * User: bjorn
 * Date: Oct 1, 2008
 * Time: 4:20:11 PM
 */
public interface CmsService {
    Boolean isAvailable();

    String createNode(String url, Map<String, String> nodeProperties) throws CmsException;

    String createNode(String url, Map<String, String> nodeProperties, Map<String, String> requestHeaders, boolean multiPart) throws CmsException;

    int mkdirs(String path) throws CmsException;

    int delete(String url) throws CmsException;

    int mkdir(String url) throws CmsException;

    String upload(String toUrl, String fileName, InputStream is) throws CmsException;

    int uploadFileNode(String url, File localFile, String fieldName, String typeHint) throws CmsException;
}
