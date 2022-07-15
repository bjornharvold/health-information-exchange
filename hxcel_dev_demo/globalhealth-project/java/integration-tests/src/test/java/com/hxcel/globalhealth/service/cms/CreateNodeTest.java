package com.hxcel.globalhealth.service.cms;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * User: bjorn
 * Date: Oct 1, 2008
 * Time: 4:25:46 PM
 */
public class CreateNodeTest extends AbstractCmsBaseTest {
    private final static Logger log = LoggerFactory.getLogger(CreateNodeTest.class);

    @Test
    public void testCreateNode() throws IOException {
        if (cmsService.isAvailable()) {
            final String url = "/CreateNodeTest_1_" + System.currentTimeMillis();

            // add some properties to the node
            final Map<String, String> props = new HashMap<String, String>();
            props.put("name1", "value1");
            props.put("name2", "value2");

            // POST and get URL of created node
            String urlOfNewNode = null;
            try {
                urlOfNewNode = cmsService.createNode(url, props);
            } catch (CmsException ioe) {
                fail("createNode failed: " + ioe);
            }

            // get and check URL of created node
            final GetMethod get = new GetMethod(HOST + urlOfNewNode);
            final int status = httpClient.executeMethod(get);
            assertEquals(HOST + urlOfNewNode + " must be accessible after createNode", 200, status);
            final String responseBodyStr = get.getResponseBodyAsString();
            assertTrue(responseBodyStr.contains("value1"));
            assertTrue(responseBodyStr.contains("value2"));

            // test default txt and html renderings
            getContent(HOST + urlOfNewNode, CONTENT_TYPE_PLAIN);
            getContent(HOST + urlOfNewNode + ".txt", CONTENT_TYPE_PLAIN);
            getContent(HOST + urlOfNewNode + ".html", CONTENT_TYPE_HTML);
            getContent(HOST + urlOfNewNode + ".json", CONTENT_TYPE_JSON);
            // getContent(urlOfNewNode + ".xml", CONTENT_TYPE_XML);

            // And extensions for which we have no renderer fail
            assertHttpStatus(HOST + urlOfNewNode + ".pdf", 500);
            assertHttpStatus(HOST + urlOfNewNode + ".someWeirdExtension", 500);

            urlsToDelete.add(url);
        } else {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testCreateNodeMultipart() throws IOException {
        if (cmsService.isAvailable()) {
            final String url = "/CreateNodeTest_2_" + System.currentTimeMillis();

            // add some properties to the node
            final Map<String, String> props = new HashMap<String, String>();
            props.put("name1", "value1B");
            props.put("name2", "value2B");

            // POST and get URL of created node
            String urlOfNewNode = null;
            try {
                urlOfNewNode = cmsService.createNode(url, props, null, true);
            } catch (CmsException ioe) {
                fail("createNode failed: " + ioe);
            }

            // check node contents (not all renderings - those are tested above)
            final GetMethod get = new GetMethod(HOST + urlOfNewNode);
            final int status = httpClient.executeMethod(get);
            assertEquals(HOST + urlOfNewNode + " must be accessible after createNode", 200, status);
            final String responseBodyStr = get.getResponseBodyAsString();
            assertTrue(responseBodyStr.contains("value1B"));
            assertTrue(responseBodyStr.contains("value2B"));

            urlsToDelete.add(url);
        } else {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testCreateNodeWithNodeType() throws IOException {
        if (cmsService.isAvailable()) {
            final String url = "/CreateNodeTest_3_" + System.currentTimeMillis();

            // add node type param
            final Map<String, String> props = new HashMap<String, String>();
            props.put("jcr:primaryType", "nt:folder");

            // POST and get URL of created node
            String urlOfNewNode = null;
            try {
                urlOfNewNode = cmsService.createNode(url, props);
            } catch (CmsException ioe) {
                fail("createNode failed: " + ioe);
            }

            String content = getContent(HOST + urlOfNewNode + ".json", CONTENT_TYPE_JSON);
            assertJavascript("nt:folder", content, "out.println(data['jcr:primaryType'])");

            urlsToDelete.add(url);
        } else {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testDeepCreateNodeWithNodeType() throws IOException {
        if (cmsService.isAvailable()) {
            final String url = "/CreateNodeTest_5_" + System.currentTimeMillis();

            // add node type param
            final Map<String, String> props = new HashMap<String, String>();
            props.put("jcr:primaryType", "nt:folder");
            props.put("foo/jcr:primaryType", "nt:folder");
            props.put("foo/bar/jcr:primaryType", "nt:folder");

            // POST and get URL of created node
            String urlOfNewNode = null;
            try {
                urlOfNewNode = cmsService.createNode(url, props);
            } catch (CmsException ioe) {
                fail("createNode failed: " + ioe);
            }

            String content = getContent(HOST + urlOfNewNode + ".3.json", CONTENT_TYPE_JSON);
            assertJavascript("nt:folder", content, "out.println(data['jcr:primaryType'])");
            assertJavascript("nt:folder", content, "out.println(data.foo['jcr:primaryType'])");
            assertJavascript("nt:folder", content, "out.println(data.foo.bar['jcr:primaryType'])");
            urlsToDelete.add(url);
        } else {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testCreateEmptyNode() throws IOException {
        if (cmsService.isAvailable()) {
            final String url = "/CreateNodeTest_6_" + System.currentTimeMillis();

            // add node type param
            final Map<String, String> props = new HashMap<String, String>();

            // POST and get URL of created node
            String urlOfNewNode = null;
            try {
                urlOfNewNode = cmsService.createNode(url, props);
            } catch (CmsException ioe) {
                fail("createNode failed: " + ioe);
            }

            // get and check URL of created node
            final GetMethod get = new GetMethod(HOST + urlOfNewNode);
            final int status = httpClient.executeMethod(get);
            assertEquals(HOST + urlOfNewNode + " must be accessible after createNode", 200, status);
            urlsToDelete.add(url);
        } else {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }
}
