package com.hxcel.globalhealth.service.cms.impl;

import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.net.URL;

import com.hxcel.globalhealth.service.cms.CmsException;
import com.hxcel.globalhealth.service.cms.CmsService;
import com.hxcel.globalhealth.service.cms.utils.HttpAnyMethod;

/**
 * User: Bjorn Harvold
 * Date: Nov 23, 2006
 * Time: 4:32:15 PM
 */
public class CmsServiceImpl implements CmsService {
    private final static Logger log = LoggerFactory.getLogger(CmsServiceImpl.class);
    private static final int HTTP_201 = 201; // this means that we're added a new file successfully
    private static final int HTTP_204 = 204; // this means that we are overwriting a new file successfully

    /**
     * Sets the default login credentials to Sling since we can't easily do this
     * using spring configuration
     */
    public void init() {
        if (authenticationEnabled) {
            httpClient.getParams().setAuthenticationPreemptive(true);
            Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
            httpClient.getState().setCredentials(new AuthScope(httpBaseUrl.getHost(), httpBaseUrl.getPort(), AuthScope.ANY_REALM), defaultcreds);
        }
    }

    /**
     * Helper method to check that external Sling service is available
     *
     * @return
     */
    public Boolean isAvailable() {
        Boolean result = Boolean.FALSE;

        try {

            final GetMethod get = new GetMethod(httpBaseUrl + "/");
            get.setFollowRedirects(false);
            final int status = httpClient.executeMethod(get);
            final Header h = get.getResponseHeader("Location");
            if (status == 302 && h.getValue().endsWith("index.html")) {
                result = Boolean.TRUE;
            }

        } catch (HttpException e) {
            // we just want to swallow this because it WILL throw an exception if it is not available
            if (log.isTraceEnabled()) {
                log.trace(e.getMessage(), e);
            }
        } catch (IOException e) {
            // we just want to swallow this because it WILL throw an exception if it is not available
            if (log.isTraceEnabled()) {
                log.trace(e.getMessage(), e);
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("Is CMS service available: " + result);
        }

        return result;
    }

    /**
     * Same as below but with no headers
     *
     * @param url
     * @param nodeProperties
     * @return
     * @throws CmsException
     */
    public String createNode(String url, Map<String, String> nodeProperties) throws CmsException {
        return createNode(url, nodeProperties, null, false);
    }

    /**
     * Method for creating a node in Sling. This was nabbed straight out of Sling unit tests
     *
     * @param path
     * @param nodeProperties
     * @param requestHeaders
     * @param multiPart
     * @return
     * @throws CmsException
     */
    public String createNode(String path, Map<String, String> nodeProperties, Map<String, String> requestHeaders, boolean multiPart) throws CmsException {
        String location = null;

        try {
            final PostMethod post = new PostMethod(httpBaseUrl.toString() + path);
            post.setFollowRedirects(false);

            // create a private copy of the properties to not tamper with
            // the properties of the client
            Map<String, String> props = new HashMap<String, String>();

            // add sling specific properties
            props.put(":redirect", httpBaseUrl + path);
            props.put(":displayExtension", "");
            props.put(":status", "browser");

            // take over any client provided properties
            if (nodeProperties != null) {
                props.putAll(nodeProperties);
            } else {
                // add fake property - otherwise the node is not created
                props.put("jcr:created", "");
            }

            // force form encoding to UTF-8, which is what we use to convert the
            // string parts into stream data
            props.put("_charset_", "UTF-8");

            if (props.size() > 0) {
                if (multiPart) {
                    final List<Part> partList = new ArrayList<Part>();
                    for (Map.Entry<String, String> e : props.entrySet()) {
                        if (e.getValue() != null) {
                            partList.add(new StringPart(e.getKey().toString(), e.getValue().toString(), "UTF-8"));
                        }
                    }
                    final Part[] parts = partList.toArray(new Part[partList.size()]);
                    post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
                } else {
                    for (Map.Entry<String, String> e : props.entrySet()) {
                        post.addParameter(e.getKey(), e.getValue());
                    }
                }
            }

            if (requestHeaders != null) {
                for (Map.Entry<String, String> e : requestHeaders.entrySet()) {
                    post.addRequestHeader(e.getKey(), e.getValue());
                }
            }

            if (log.isTraceEnabled()) {
                log.trace("Creating node: " + post.getURI());
            }
            final int status = httpClient.executeMethod(post);

            if (status != 302) {
                if (log.isTraceEnabled()) {
                    log.trace(post.getResponseBodyAsString());
                }

                throw new CmsException("service.cms.response.status.code.invalid", new String[]{"302", Integer.toString(status), "POST", path});
            }

            location = post.getResponseHeader("Location").getValue();
            post.releaseConnection();
            // simple check if host is missing
            if (!location.startsWith("http://")) {
                String host = null;
                int idx = httpBaseUrl.toString().indexOf('/', 8);
                if (idx > 0) {
                    host = httpBaseUrl.toString().substring(0, idx);
                }
                location = httpBaseUrl + location;
            }
        } catch (HttpException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        }

        return path;
    }

    /**
     * Make a directory in Sling
     *
     * @param path
     * @throws CmsException
     */
    public int mkdirs(String path) throws CmsException {
        int status = -1;

        try {
            final String[] paths = path.split("/");

            String currentPath = "";
            for (String pathElement : paths) {
                if (pathElement.length() == 0) {
                    continue;
                }
                currentPath += "/" + pathElement;
                mkdir(currentPath);
            }

            final String url = httpBaseUrl.toString() + path;
            status = httpClient.executeMethod(new GetMethod(url));

            if (status != 200) {
                throw new CmsException("service.cms.response.status.code.invalid", new String[]{"200", Integer.toString(status), "GET", url});
            }
        } catch (HttpException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        }

        return status;
    }

    /**
     * Delete a file from the Sling repository
     *
     * @return the HTTP status code
     */
    public int delete(String path) throws CmsException {
        int result = -1;
        final DeleteMethod delete = new DeleteMethod(httpBaseUrl.toString() + path);

        try {

            if (log.isTraceEnabled()) {
                log.trace("Deleting node: " + delete.getURI());
            }

            result = httpClient.executeMethod(delete);

            if (result != 204) {
                throw new CmsException("service.cms.response.status.code.invalid", new String[]{"204", Integer.toString(result), "DELETE", path});
            }
        } catch (HttpException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Create a directory if needed. Directory might already exist in which case nothing is done
     *
     * @param path
     * @return
     * @throws CmsException
     */
    public int mkdir(String path) throws CmsException {
        int status = 0;

        try {
            GetMethod get = new GetMethod(httpBaseUrl.toString() + path);

            status = httpClient.executeMethod(get);
            if (status != 200) {
                if (log.isTraceEnabled()) {
                    log.trace("Creating directory: " + get.getURI());
                }
                status = httpClient.executeMethod(new HttpAnyMethod("MKCOL", httpBaseUrl.toString() + path));
                if (status != 201) {
                    throw new CmsException("service.cms.response.status.code.invalid", new String[]{"201", Integer.toString(status), "MKCOL", path});
                }
            }
        } catch (HttpException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        }

        return status;
    }

    /**
     * Upload a file to the Sling repository
     *
     * @return the HTTP status code
     */
    public String upload(String path, String fileName, InputStream is) throws CmsException {
        String result = null;

        try {
            // first we have to create the directories if needed
            mkdirs(path);
            // make sure there aren't any unwanted characters in the file name

            // strip out the extension
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
            String extension = fileName.substring(fileName.lastIndexOf("."));

            // replace all non-alphabetics
            fileNameWithoutExtension = fileNameWithoutExtension.replaceAll("\\W", "_");

            // add extension again
            fileName = fileNameWithoutExtension + extension;

            result = path + "/" + fileName;

            final PutMethod put = new PutMethod(httpBaseUrl.toString() + result);
            put.setRequestEntity(new InputStreamRequestEntity(is));

            if (log.isTraceEnabled()) {
                log.trace("Uploading stream to: " + put.getURI());
            }

            int status = httpClient.executeMethod(put);

            if (status != HTTP_201 && status != HTTP_204) {
                throw new CmsException("cms.invalid.status.code", new String[]{Integer.toString(HTTP_201), Integer.toString(status)});
            }
        } catch (HttpException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Uploads a File as a MultiPartRequestEntity
     *
     * @param path
     * @param localFile
     * @param fieldName
     * @param typeHint
     * @return
     * @throws CmsException
     */
    public int uploadFileNode(String path, File localFile, String fieldName, String typeHint) throws CmsException {
        int status = -1;

        try {
            final Part[] parts = new Part[typeHint == null ? 1 : 2];
            parts[0] = new FilePart(fieldName, localFile);
            if (typeHint != null) {
                parts[1] = new StringPart(fieldName + "@TypeHint", typeHint);
            }
            final PostMethod post = new PostMethod(httpBaseUrl.toString() + path);
            post.setFollowRedirects(false);
            post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));

            status = httpClient.executeMethod(post);
            if (status != 200) {
                throw new CmsException("service.cms.response.status.code.invalid", new String[]{"200", Integer.toString(status), "POST", path});
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (HttpException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new CmsException(e.getMessage(), e);
        }

        return status;
    }

    // Spring IoC
    @Autowired
    private HttpClient httpClient;
    private URL httpBaseUrl;
    private String username;
    private String password;
    private Boolean authenticationEnabled;

    public void setHttpBaseUrl(URL httpBaseUrl) {
        this.httpBaseUrl = httpBaseUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthenticationEnabled(Boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
    }
}
