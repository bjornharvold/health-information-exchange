package com.hxcel.globalhealth.calendar;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import com.hxcel.globalhealth.calendar.spec.CalendarException;

/**
 * User: Bjorn Harvold
 * Date: Dec 28, 2008
 * Time: 7:28:01 PM
 * Responsibility:
 */
public class AbstractCalendarService {
    private final static Logger log = LoggerFactory.getLogger(AbstractCalendarService.class);

    /**
     * Sets the default login credentials to Sling since we can't easily do this
     * using spring configuration
     */
    public void init() {
        httpClient.getParams().setAuthenticationPreemptive(true);
        Credentials defaultcreds = new UsernamePasswordCredentials(username, password);
        httpClient.getState().setCredentials(new AuthScope(calendarUrl.getHost(), calendarUrl.getPort(), AuthScope.ANY_REALM), defaultcreds);
    }

    /**
     * Checks the availability of the service by querying the calendar server to see if it's there
     *
     * @return
     */
    public Boolean isAvailable() {
        Boolean result = Boolean.FALSE;

        try {

            final GetMethod get = new GetMethod(calendarUrl + "/");
            get.setFollowRedirects(false);
            final int status = httpClient.executeMethod(get);
            final Header h = get.getResponseHeader("Location");
            if (status == 302 && h.getValue().indexOf("/chandler/welcome") > -1) {
                result = Boolean.TRUE;
            }

        } catch (HttpException e) {
            // we just want to swallow this because it WILL throw an exception if it is not available
            /*
            if (log.isTraceEnabled()) {
                log.trace(e.getMessage(), e);
            }
            */
        } catch (IOException e) {
            // we just want to swallow this because it WILL throw an exception if it is not available
            /*
            if (log.isTraceEnabled()) {
                log.trace(e.getMessage(), e);
            }
            */
        }

        if (log.isTraceEnabled()) {
            if (result) {
                log.trace("The Chandler Calendar Server is operational");
            } else {
                log.trace("The Chandler Calendar Server is not ready. Make sure Chandler Server is running and you are pointing to the URL Chandler Server is running on");
            }
        }

        return result;
    }

    /**
     * Calls an HTTP Get on the specified url. If the response code is anything else than 200 it throws an error
     *
     * @param url
     * @return
     * @throws CalendarException
     */
    protected String doGetRequest(String url, Integer successCode, Map<Integer, String> errorCodes) throws CalendarException {
        String response = null;

        try {
            final GetMethod get = new GetMethod(calendarUrl + url);
            get.setFollowRedirects(false);
            final int status = httpClient.executeMethod(get);
            response = parseInputStreamToString(get.getResponseBodyAsStream());

            if (successCode != null && !successCode.equals(status)) {
                log.error(response);
                throw new CalendarException("Response code does not match expected successCode(" + successCode + "): " + status);
            }
            if (errorCodes != null && errorCodes.keySet().contains(status)) {
                log.error(response);
                throw new CalendarException("Response code matches an error code(" + status + "): " + errorCodes.get(status));
            }

        } catch (IOException e) {
            throw new CalendarException("service.connection.exception", e, e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a calendar user
     *
     * @param url
     * @return
     * @throws CalendarException
     */
    protected String doDeleteRequest(String url, Integer successCode, Map<Integer, String> errorCodes) throws CalendarException {
        String response = null;

        try {
            final DeleteMethod get = new DeleteMethod(calendarUrl + url);
            get.setFollowRedirects(false);
            final int status = httpClient.executeMethod(get);
            response = parseInputStreamToString(get.getResponseBodyAsStream());

            if (successCode != null && !successCode.equals(status)) {
                log.error(response);
                throw new CalendarException("Response code does not match expected successCode(" + successCode + "): " + status);
            } else if (errorCodes != null && errorCodes.keySet().contains(status)) {
                log.error(response);
                throw new CalendarException("Response code matches an error code(" + status + "): " + errorCodes.get(status));
            }
        } catch (IOException e) {
            throw new CalendarException("service.connection.exception", e, e.getMessage());
        }

        return response;
    }

    /**
     * Calls an HTTP Put with the specified url and body. If the successCode is anything other than 200 it should be specified.
     *
     * @param url
     * @param body
     * @param successCode The success code we can expect from the server. Default: 200
     * @param errorCodes  The error codes and their corresponding messages
     * @return
     */
    protected String doPutRequest(String url, String body, Integer successCode, Map<Integer, String> errorCodes) throws CalendarException {
        String response = null;

        try {
            final PutMethod put = new PutMethod(calendarUrl + url);
            put.setFollowRedirects(false);
            put.setRequestEntity(new StringRequestEntity(body, "text/xml", "utf-8"));
            final int status = httpClient.executeMethod(put);
            response = parseInputStreamToString(put.getResponseBodyAsStream());

            if (successCode != null && !successCode.equals(status)) {
                log.error(response);
                throw new CalendarException("Response code does not match expected successCode(" + successCode + "): " + status);
            } else if (errorCodes != null && errorCodes.keySet().contains(status)) {
                log.error(response);
                throw new CalendarException("Response code matches an error code(" + status + "): " + errorCodes.get(status));
            }
        } catch (IOException e) {
            throw new CalendarException("service.connection.exception", e, e.getMessage());
        }

        return response;
    }

    /**
     * Simple utility methd to get the contents of the response body
     *
     * @param is
     * @return
     */
    private String parseInputStreamToString(InputStream is) {
        String result = null;

        if (is != null) {
            StringBuffer sb = new StringBuffer();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            try {
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                try {
                    is.close();
                } catch (Exception ex) {
                    log.error("Could not close input stream!", ex);
                }
            }

            result = sb.toString();
        }

        return result;
    }

    // Spring IoC
    protected HttpClient httpClient;
    private URL calendarUrl;
    private String username;
    private String password;

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setCalendarUrl(URL calendarUrl) {
        this.calendarUrl = calendarUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
