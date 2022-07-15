package com.hxcel.globalhealth.service.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Apr 23, 2007
 * Time: 5:16:40 PM
 */
public class MailServiceTest extends AbstractEmailServiceTest {
    private static final Logger log = LoggerFactory.getLogger(MailServiceTest.class);
    private static final String EMAIL_TO_TEST = "bjorn@server.com";
    private static final String SUBJECT = "Test subject";
    private static final String MESSAGE = "Test message!";
    private GreenMail mailServer = null;

    public void onSetUp() {
        mailServer = new GreenMail(ServerSetupTest.ALL);
        mailServer.start();
    }

    public void onTearDown() {
        if (mailServer != null) {
            mailServer.stop();
        }
    }

    public void testPlainSendMail() {
        try {
            log.info("Sending plain email");
            Map<String, Object> params = new HashMap<String, Object>();

            params.put("message", GreenMailUtil.random());
            params.put("subject", GreenMailUtil.random());
            params.put("to", EMAIL_TO_TEST);            
            mailService.sendPlainEmail("/velocity/template/plainEmail.vm", params);
            
            assertTrue(mailServer.waitForIncomingEmail(5000, 1)); 
            //Retrieve using GreenMail API
            Message[] messages = mailServer.getReceivedMessages();
            assertEquals(1, messages.length);
            assertEquals(params.get("subject"), messages[0].getSubject());
            assertEquals(params.get("message"), GreenMailUtil.getBody(messages[0]).trim());
        } catch (MailServiceException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        } catch (MessagingException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        } catch (InterruptedException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        }
    }

    public void testMimeSendMail() {
        try {
            log.info("Sending MIME email");
            Map<String, Object> params = new HashMap<String, Object>();
            
            params.put("message", GreenMailUtil.random());
            params.put("subject", GreenMailUtil.random());
            params.put("to", EMAIL_TO_TEST);

            Map<String, String> imageAssets = new HashMap<String, String>();
            imageAssets.put("identifier1", "test.xml");

            Map<String, String> attachmentAssets = new HashMap<String, String>();
            attachmentAssets.put("test.xml", "test.xml");

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("sender", "Intela, Inc");

            // In production, should use normal FileSystemRessource. 
            // But in test, set with classloader ressource to keep test
            // atomic.
            mailService.sendMIMEEmail("/velocity/template/plainEmail.vm", params, imageAssets, attachmentAssets, headers);

            assertTrue(mailServer.waitForIncomingEmail(5000, 1));

            //Retrieve using GreenMail API
            Message[] messages = mailServer.getReceivedMessages();
            assertEquals(1, messages.length);
            assertEquals(params.get("subject"), messages[0].getSubject());

            // commenting out - returns whole message with attachment code. not just text
            // assertEquals(params.get("message"), GreenMailUtil.getBody(messages[0]).trim());

            //if your send content is a 2 part multipart...
            assertTrue(messages[0].getContent() instanceof MimeMultipart);
            MimeMultipart mp = (MimeMultipart) messages[0].getContent();
            assertEquals(3, mp.getCount());
            String bp1S = GreenMailUtil.getBody(mp.getBodyPart(0));
            String bp2S = GreenMailUtil.getBody(mp.getBodyPart(1));
            String bp3S = GreenMailUtil.getBody(mp.getBodyPart(2));
            assertNotNull(bp1S);
            assertNotNull(bp2S);
            assertNotNull(bp3S);

        } catch (MailServiceException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        } catch (MessagingException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        } catch (InterruptedException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        } catch (IOException e) {
            log.error("Error: " + e.getMessage() + "\n", e);
            assertTrue(false);
        }
    }

    // Spring IoC
    private MailService mailService = null;

    @Required
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
