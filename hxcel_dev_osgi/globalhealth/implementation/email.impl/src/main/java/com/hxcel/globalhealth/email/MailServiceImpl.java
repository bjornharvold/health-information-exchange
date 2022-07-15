package com.hxcel.globalhealth.email;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;

import com.hxcel.globalhealth.email.spec.MailService;
import com.hxcel.globalhealth.email.spec.MailServiceException;

/**
 * User: Bjorn Harvold Date: Apr 23, 2007 Time: 5:04:33 PM
 */
public class MailServiceImpl implements MailService {

	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);


	/**
	 * Creates a MailMessage for you that you can then send Required keys in the
	 * map are: key: to key: subject (is a message bundle key that will be
	 * translated)
	 * 
	 * @param template
	 * @param params
	 * @throws MailServiceException
	 */
	public void sendPlainEmail(String template, Map<String, Object> params)
			throws MailServiceException {

		// Only proceed if mail enabled property is set to true
        if (log.isTraceEnabled()) {
            log.trace("Mail server is enabled? Answer: " + mailEnabled);
        }
		if (mailEnabled) {

			validate(template, params);

			// retrieve template
			try {
				prepareMailMessage(template, params);

				mailSender.send(mailMessage);

			} catch (VelocityException ex) {
				log.error("Problem retrieving template for email: " + ex.getMessage(), ex);
				throw new MailServiceException("error.velocity: " + ex.getMessage(), ex);
			}
		}
	}
	
	/**
	 * setup the mail message in preparation for sending
	 * 
	 * @param template
	 * @param params
	 */
	private void prepareMailMessage(String template, Map<String, Object> params) {
		mailMessage.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, params));
		mailMessage.setTo((String) params.get("to"));
		mailMessage.setSubject((String) params.get("subject"));
		
		if (log.isDebugEnabled()) {
			log.debug("Initial email looks like this: ");
			log.debug(mailMessage.toString());
		}
	}

	/**
	 * Sends a MIME email based on a velocity template and image assets with
	 * (key: identifier, value: image name)
	 * 
	 * @param template
	 * @param params
	 * @param imageAssets
	 * @throws MailServiceException
	 */
	public void sendMIMEEmail(String template, Map<String, Object> params,
			Map<String, String> imageAssets, Map<String, String> attachments, Map<String, String> headers)
			throws MailServiceException {

		// Only proceed if mail enabled property is set to true
		if (mailEnabled) {

			validate(template, params);

			try {
                mimeMessagePreparator.setVelocityEngine(velocityEngine);
                mimeMessagePreparator.setTemplate(template);
                mimeMessagePreparator.setParams(params);
                mimeMessagePreparator.setImageAssets(imageAssets);
                mimeMessagePreparator.setAttachments(attachments);
                mimeMessagePreparator.setHeaders(headers);

                mailSender.send(mimeMessagePreparator);
			} catch (VelocityException ex) {
				log.error("Problem retrieving template for email: " + ex.getMessage(), ex);
				throw new MailServiceException("error.notification.velocity: "+ ex.getMessage(), ex);
			}
		}
	}

    public Boolean isAvailable() {
        return mailEnabled;
    }

    private void validate(String template, Map<String, Object> params)
			throws MailServiceException {
		if (StringUtils.isBlank(template)) {
			log.error("Missing template. Template cannot be null");
			throw new MailServiceException("error.missing.argument.exception",
					"Missing template. Template cannot be null");
		}
		if (params == null) {
			log
					.error("Missing params. Minimum fields needed are \"to\" and \"subject\"");
			throw new MailServiceException("error.missing.argument.exception",
					"Missing params. Minimum fields needed are \"to\" and \"subject\"");
		} else if ((!params.containsKey("to") && mailMessage.getTo() == null)
				|| (!params.containsKey("subject") && StringUtils
						.isBlank(mailMessage.getSubject()))) {
			log
					.error("Missing values in map. Missing params. Minimum fields needed are \"to\" and \"subject\"");
			throw new MailServiceException(
					"error.missing.argument.exception",
					"Missing values in map. Missing params. Minimum fields needed are \"to\" and \"subject\"");
		}
	}

	// Spring IoC
	private JavaMailSender mailSender = null;
	private SimpleMailMessage mailMessage = null;
	private VelocityEngine velocityEngine = null;
    private MyMimeMessagePreparator mimeMessagePreparator;
    private boolean mailEnabled = false;

    @Required
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Required
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	@Required
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Required
	public void setMailEnabled(boolean mailEnabled) {
		this.mailEnabled = mailEnabled;
	}

    @Required
    public void setMimeMessagePreparator(MyMimeMessagePreparator mimeMessagePreparator) {
        this.mimeMessagePreparator = mimeMessagePreparator;
    }

    public MyMimeMessagePreparator getMimeMessagePreparator() {
        return mimeMessagePreparator;
    }
}
