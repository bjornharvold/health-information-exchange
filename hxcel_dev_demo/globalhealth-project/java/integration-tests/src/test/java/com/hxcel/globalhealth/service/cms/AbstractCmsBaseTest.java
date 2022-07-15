package com.hxcel.globalhealth.service.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.auth.AuthScope;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Context;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * User: Bjorn Harvold
 * Date: Jul 2, 2006
 * Time: 9:40:27 PM
 * Base class for cms testing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-service-cms-beans.xml", "/spring-service-cms-property-configurer-bean.xml"})
public abstract class AbstractCmsBaseTest {
    private final static Logger log = LoggerFactory.getLogger(AbstractCmsBaseTest.class);

    /** base path for test files */
    public static final String HOST = "http://localhost:8888";
    public static final String TEST_PATH = "/launchpad-integration-tests";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_XML = "text/xml";
    public static final String CONTENT_TYPE_PLAIN = "text/plain";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_JS = "application/x-javascript";
    public static final String CONTENT_TYPE_CSS = "text/css";
    public static final String SLING_RESOURCE_TYPE = "sling:resourceType";
    public static final String SLING_POST_SERVLET_CREATE_SUFFIX = "/";

    /** Means "don't care about Content-Type" in getContent(...) methods */
    public static final String CONTENT_TYPE_DONTCARE = "*";

    /** URLs stored here are deleted in tearDown */
    protected final List<String> urlsToDelete = new LinkedList<String>();


    /** Class that creates a test node under the given parentPath, and
     *  stores useful values for testing. Created for JspScriptingTest,
     *  older test classes do not use it, but it might simplify them.
     */
    protected class TestNode {
        public final String testText;
        public final String nodeUrl;
        public final String resourceType;
        public final String scriptPath;

        public TestNode(String parentPath, Map<String, String> properties) throws CmsException {
            if(properties == null) {
                properties = new HashMap<String, String>();
            }
            testText = "This is a test node " + System.currentTimeMillis();
            properties.put("text", testText);
            nodeUrl = cmsService.createNode(parentPath + SLING_POST_SERVLET_CREATE_SUFFIX, properties);
            resourceType = properties.get(SLING_RESOURCE_TYPE);
            scriptPath = "/apps/" + (resourceType == null ? "nt/unstructured" : resourceType);
            cmsService.mkdirs(scriptPath);
        }

        public void delete() throws CmsException {
            cmsService.delete(nodeUrl);
        }
    };

    protected static String removeEndingSlash(String str) {
        if(str != null && str.endsWith("/")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Before
    public void onSetUp() throws Exception {
        // setup HTTP client, with authentication (using default Jackrabbit credentials)
        httpClient = new HttpClient();
        httpClient.getParams().setAuthenticationPreemptive(true);
        Credentials defaultcreds = new UsernamePasswordCredentials("admin", "admin");
        httpClient.getState().setCredentials(new AuthScope("localhost", 8888, AuthScope.ANY_REALM), defaultcreds);
    }

    @After
    public void onTearDown() throws Exception {
        for(String url : urlsToDelete) {
            cmsService.delete(url);
        }
    }

    /** Verify that given URL returns expectedStatusCode
     * @throws IOException */
    protected void assertHttpStatus(String urlString, int expectedStatusCode, String assertMessage) throws IOException {
        final int status = httpClient.executeMethod(new GetMethod(urlString));
        if(assertMessage == null) {
            assertEquals(urlString,expectedStatusCode, status);
        } else {
            assertEquals(assertMessage, expectedStatusCode, status);
        }
    }

    /** Verify that given URL returns expectedStatusCode
     * @throws IOException */
    protected void assertHttpStatus(String urlString, int expectedStatusCode) throws IOException {
        assertHttpStatus(urlString, expectedStatusCode, null);
    }

    /** Execute a POST request and check status */
    protected void assertPostStatus(String url, int expectedStatusCode, List<NameValuePair> postParams, String assertMessage)
    throws IOException {
        final PostMethod post = new PostMethod(url);
        post.setFollowRedirects(false);

        if(postParams!=null) {
            final NameValuePair [] nvp = {};
            post.setRequestBody(postParams.toArray(nvp));
        }

        final int status = httpClient.executeMethod(post);
        if(assertMessage == null) {
            assertEquals(expectedStatusCode, status);
        } else {
            assertEquals(assertMessage, expectedStatusCode, status);
        }
    }

    /** retrieve the contents of given URL and assert its content type */
    protected String getContent(String url, String expectedContentType) throws IOException {
        return getContent(url, expectedContentType, null);
    }

    /** retrieve the contents of given URL and assert its content type
     * @param expectedContentType use CONTENT_TYPE_DONTCARE if must not be checked
     * @throws IOException
     * @throws HttpException */
    protected String getContent(String url, String expectedContentType, List<NameValuePair> params) throws IOException {
        final GetMethod get = new GetMethod(url);
        if(params != null) {
            final NameValuePair [] nvp = new NameValuePair[0];
            get.setQueryString(params.toArray(nvp));
        }
        final int status = httpClient.executeMethod(get);
        final String content = get.getResponseBodyAsString();
        assertEquals("Expected status 200 for " + url + " (content=" + content + ")",200,status);
        final Header h = get.getResponseHeader("Content-Type");
        if(expectedContentType == null) {
            if(h!=null) {
                fail("Expected null Content-Type, got " + h.getValue());
            }
        } else if(CONTENT_TYPE_DONTCARE.equals(expectedContentType)) {
            // no check
        } else if(h==null) {
            fail(
                    "Expected Content-Type that starts with '" + expectedContentType
                    +" but got no Content-Type header at " + url
            );
        } else {
            assertTrue(
                "Expected Content-Type that starts with '" + expectedContentType
                + "' for " + url + ", got '" + h.getValue() + "'",
                h.getValue().startsWith(expectedContentType)
            );
        }
        return content;
    }

    protected void assertJavascript(String expectedOutput, String jsonData, String code) throws IOException {
        assertJavascript(expectedOutput, jsonData, code, null);
    }

    /** Evaluate given code using given jsonData as the "data" object */
    protected void assertJavascript(String expectedOutput, String jsonData, String code, String testInfo) throws IOException {
        // build the code, something like
        //  data = <jsonData> ;
        //  <code>
        final String jsCode = "data=" + jsonData + ";\n" + code;
        final Context rhinoContext = Context.enter();
        final ScriptableObject scope = rhinoContext.initStandardObjects();

        // execute the script, out script variable maps to sw
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        ScriptableObject.putProperty(scope, "out", Context.javaToJS(pw, scope));
        final int lineNumber = 1;
        final Object securityDomain = null;
        rhinoContext.evaluateString(scope, jsCode, getClass().getSimpleName(),
                lineNumber, securityDomain);

        // check script output
        pw.flush();
        final String result = sw.toString().trim();
        if(!result.equals(expectedOutput)) {
            fail(
                    "Expected '" + expectedOutput
                    + "' but got '" + result
                    + "' for script='" + jsCode + "'"
                    + (testInfo==null ? "" : ", test info=" + testInfo)
            );
        }
    }

    // Spring IoC
    @Autowired
    protected CmsService cmsService;

    @Autowired
    protected HttpClient httpClient;
}


