package com.hxcel.globalhealth.service.cms;

import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Oct 4, 2008
 * Time: 12:42:44 PM
 */
public class UploadFileTest extends AbstractCmsBaseTest {
    private final static Logger log = LoggerFactory.getLogger(UploadFileTest.class);
    private static final Resource TEST_FILE = new ClassPathResource("cmstest.xml");
    private static final String MUST_CONTAIN = "http://www.hxcel.com";

    @Test
    public void testDistinctResource() throws IOException {
        final String url = "/UploadFileTest_1_" + System.currentTimeMillis();

        // create new node
        String urlOfNewNode = null;
        try {
            if (cmsService.isAvailable()) {
                urlOfNewNode = cmsService.createNode(url, null);

                if (TEST_FILE.exists()) {
                    cmsService.uploadFileNode(urlOfNewNode, TEST_FILE.getFile(), "./file", null);

                    // get and check URL of created file
                    String urlOfFileNode = urlOfNewNode + "/file";
                    final GetMethod get = new GetMethod(HOST + urlOfFileNode);
                    final int status = httpClient.executeMethod(get);
                    assertEquals(urlOfFileNode + " must be accessible after createNode", 200, status);

                    /*
                    We should check the data, but nt:resources are not handled yet
                    // compare data with local file (just length)
                    final byte[] data = get.getResponseBody();
                    assertEquals("size of file must be same", localFile.length(), data.length);
                    */
                    String data = get.getResponseBodyAsString();
                    assertTrue("checking for content", data.contains(MUST_CONTAIN));

                    // download structure
                    String json = getContent(HOST + urlOfFileNode + ".json", CONTENT_TYPE_JSON);
                    // just check for some strings
                    assertTrue("checking primary type", json.contains("\"jcr:primaryType\":\"nt:resource\""));
                    assertTrue("checking mime type", json.contains("\"jcr:mimeType\":\"text/xml\""));

                    urlsToDelete.add(url);
                } else {
                    fail("Test file doesn't exist");
                }
            } else {
                log.error("Won't fail the test because it's an integration test but CMS service is not available");

            }
        } catch (CmsException e) {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testDistinctResourceWithType() throws IOException {
        final String url = "/UploadFileTest_1_" + System.currentTimeMillis();

        // create new node
        String urlOfNewNode = null;
        try {
            if (cmsService.isAvailable()) {
                urlOfNewNode = cmsService.createNode(url, null);

                // upload local file
                if (TEST_FILE.exists()) {
                    cmsService.uploadFileNode(urlOfNewNode, TEST_FILE.getFile(), "./file", "nt:unstructured");

                    // get and check URL of created file
                    String urlOfFileNode = urlOfNewNode + "/file";
                    final GetMethod get = new GetMethod(HOST + urlOfFileNode);
                    final int status = httpClient.executeMethod(get);
                    assertEquals(urlOfFileNode + " must be accessible after createNode", 200, status);

                    /*
                    We should check the data, but nt:resources are not handled yet
                    // compare data with local file (just length)
                    final byte[] data = get.getResponseBody();
                    assertEquals("size of file must be same", localFile.length(), data.length);
                    */
                    String data = get.getResponseBodyAsString();
                    assertTrue("checking for content", data.contains(MUST_CONTAIN));

                    // download structure
                    String json = getContent(HOST + urlOfFileNode + ".100.json", CONTENT_TYPE_JSON);

                    // just check for some strings
                    assertTrue("checking primary type", json.contains("\"jcr:primaryType\":\"nt:unstructured\""));
                    assertTrue("checking mime type", json.contains("\"jcr:mimeType\":\"text/xml\""));

                    urlsToDelete.add(url);
                } else {
                    fail("Test file doesn't exist");
                }
            } else {
                log.error("Won't fail the test because it's an integration test but CMS service is not available");
            }
        } catch (CmsException e) {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testDistinctFile() throws IOException {
        String folderPath = "/UploadFileTest_1_" + System.currentTimeMillis();

        try {
            if (cmsService.isAvailable()) {
                cmsService.mkdirs(folderPath);
                final String url = folderPath;

                // upload local file

                if (TEST_FILE.exists()) {
                    cmsService.uploadFileNode(url, TEST_FILE.getFile(), "./file", null);

                    // get and check URL of created file
                    String urlOfFileNode = url + "/file";

                    /*
                    TODO: does not work, since no nt:file resource type handler present ???

                    final GetMethod get = new GetMethod(urlOfFileNode);
                    final int status = httpClient.executeMethod(get);
                    assertEquals(urlOfFileNode + " must be accessible after createNode",200,status);

                    // compare data with local file (just length)
                    final byte[] data = get.getResponseBody();
                    assertEquals("size of file must be same", localFile.length(), data.length);
                    */

                    String webdavUrl = folderPath + "/file";
                    final GetMethod get = new GetMethod(HOST + webdavUrl);
                    final int status = httpClient.executeMethod(get);
                    assertEquals(urlOfFileNode + " must be accessible after createNode", 200, status);

                    // compare data with local file (just length)
                    final byte[] data = get.getResponseBody();
                    assertEquals("size of file must be same", TEST_FILE.getFile().length(), data.length);

                    // download structure
                    String json = getContent(HOST + urlOfFileNode + ".json", CONTENT_TYPE_JSON);
                    // just check for some strings
                    assertTrue("checking primary type", json.contains("\"jcr:primaryType\":\"nt:file\""));

                    urlsToDelete.add(url);
                } else {
                    fail("Test file doesn't exist");
                }
            } else {
                log.error("Won't fail the test because it's an integration test but CMS service is not available");
            }
        } catch (CmsException e) {
            log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
        }
    }

    @Test
    public void testUploadAndDelete() {
        if (cmsService.isAvailable()) {
            try {
                try {

                    assertNotNull("Local test file " + TEST_FILE.getFilename() + " must be found", TEST_FILE.getInputStream());

                    String path = "/test/path";
                    final String fileName = "FileUploadTest." + System.currentTimeMillis() + ".txt";
                    // Upload a file via WebDAV, verify, delete and verify
                    assertHttpStatus(HOST + path + fileName, 404, "Resource " + path + " must not exist before test");
                    path = cmsService.upload(path, fileName, TEST_FILE.getInputStream());
                    assertNotNull("path is empty", path);
                    assertHttpStatus(HOST + path, 200, "Resource " + path + " must exist after upload");
                    cmsService.delete(path);
                    assertHttpStatus(HOST + path, 404, "Resource " + path + " must not exist anymore after deleting");
                } finally {
                    if (TEST_FILE.getInputStream() != null) {
                        TEST_FILE.getInputStream().close();
                    }
                }
            } catch (IOException e) {
                fail("problem with file: " + e);
            } catch (CmsException e) {
                log.error("Cms Service is not available. Cannot run this test if the service is not running. Consider commenting out this test if your are running it in a CI environment");
            }
        } else {
            log.error("Won't fail the test because it's an integration test but CMS service is not available");
        }
    }
}
