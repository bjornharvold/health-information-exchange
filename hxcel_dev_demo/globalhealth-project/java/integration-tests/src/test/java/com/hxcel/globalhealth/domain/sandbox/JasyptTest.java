package com.hxcel.globalhealth.domain.sandbox;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: bjorn
 * Date: Aug 12, 2008
 * Time: 1:20:02 PM
 */

public class JasyptTest extends AbstractDependencyInjectionSpringContextTests {
    private static final Logger log = LoggerFactory.getLogger(JasyptTest.class);

    private final static String myEncryptionPassword = "TEST";
    private final static String myText = "This is a test string";
    private final static String dbuser = "inl1f3";
    private final static String decryptMe = "VdKmZHLCsaLuMFzyOMSEGcAbb9K4/AFbPcP/CPaNuWY=";

    public String[] getConfigLocations() {
        return new String[]{"/spring-utils-encryption-beans.xml"};
    }

    @Test
    public void testEncryptionPackageWorking() {

        log.info("First we test regular jasypt programatic encryptor");
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(myEncryptionPassword);
        String myEncryptedText = textEncryptor.encrypt(myText);

        String plainText = textEncryptor.decrypt(myEncryptedText);

        assertEquals("Input and output after encryption was not the same", myText, plainText);

        log.info("Then we test bean injected jasypt string encryptor");
        myEncryptedText = pbeStringEncryptor.encrypt(myText);

        plainText = pbeStringEncryptor.decrypt(myEncryptedText);

        assertEquals("Input and output after encryption was not the same", myText, plainText);

        try {
            plainText = pbeStringEncryptor.decrypt(decryptMe);
        } catch (Exception e) {
            log.error("The APP_ENCRYPTION_PASSWORD system variable you entered is not correct! This is what you entered. Please confer with the getting started docs. " + System.getenv("APP_ENCRYPTION_PASSWORD"));
        }

        assertEquals("Input and output after encryption was not the same", dbuser, plainText);
    }

    // Spring IoC
    @Autowired
    private PBEStringEncryptor pbeStringEncryptor = null;

    @Autowired
    private EnvironmentStringPBEConfig environmentStringPBEConfig = null;

    public void setPbeStringEncryptor(PBEStringEncryptor pbeStringEncryptor) {
        this.pbeStringEncryptor = pbeStringEncryptor;
    }

    public void setEnvironmentStringPBEConfig(EnvironmentStringPBEConfig environmentStringPBEConfig) {
        this.environmentStringPBEConfig = environmentStringPBEConfig;
    }
}
