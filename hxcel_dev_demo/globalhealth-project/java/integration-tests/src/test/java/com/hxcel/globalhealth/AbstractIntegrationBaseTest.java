package com.hxcel.globalhealth;

import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.jasypt.util.password.PasswordEncryptor;
import com.hxcel.globalhealth.jetty.JettyServer;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.common.model.*;
import com.hxcel.globalhealth.domain.common.model.enums.*;
import com.hxcel.globalhealth.domain.common.dao.*;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.patient.model.Patient;
import com.hxcel.globalhealth.domain.patient.PatientManager;
import com.hxcel.globalhealth.domain.patient.dao.PatientDAO;
import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.DtoTestFactory;
import com.hxcel.globalhealth.domain.emr.dao.EmrDAO;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.socialnetwork.model.enums.EntityTypeCd;
import com.hxcel.globalhealth.domain.socialnetwork.RelationshipManager;
import com.hxcel.globalhealth.domain.platform.model.*;
import com.hxcel.globalhealth.domain.platform.model.enums.*;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.platform.dao.OrganizationUserDAO;
import com.hxcel.globalhealth.domain.platform.dao.OrganizationLicenseDAO;
import com.hxcel.globalhealth.domain.phr.model.PhrContact;
import com.hxcel.globalhealth.domain.phr.model.Practitioner;
import com.hxcel.globalhealth.domain.phr.model.enums.PhrTypeCd;
import com.hxcel.globalhealth.domain.phr.dao.PractitionerDAO;
import com.hxcel.globalhealth.domain.phr.dao.PhrContactDAO;
import com.hxcel.globalhealth.domain.phr.dao.PersonalProfileDAO;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import net.sf.dozer.util.mapping.MapperIF;

import javax.swing.*;

/**
 * User: Bjorn Harvold
 * Date: Nov 1, 2008
 * Time: 11:51:12 AM
 * Description:
 */
@ContextConfiguration(locations = {
        "/spring-admin-validator-beans.xml",
        "/spring-admin-property-configurer-bean.xml",
        "/spring-domain-beans-for-main-app.xml"
})
public class AbstractIntegrationBaseTest extends AbstractJUnit4SpringContextTests {
    private final static Logger log = LoggerFactory.getLogger(AbstractIntegrationBaseTest.class);
    private User u = null;
    private UserDto userDto = null;
    private Patient p = null;
    private JettyServer server = null;
    private List<Organization> orgs = null;
    private List<Application> apps = null;
    private List<Role> roles = null;
    private List<ApplicationRole> applicationRoles;
    private List<License> licenses;
    private List<ApplicationConfiguration> applicationConfigurations;
    private List<Regulation> regulations;
    protected static final String PASSWORD = "password";
    private List<Country> countries;
    private List<RegulationOverride> overrides;

    @Before
    public void setUp() {
        secureChannel();

        try {
            log.info("Starting " + this.getClass().getName() + " web service test");
            log.info("Creating test user for unit test");

            u = createUser("testUser", "test@user.com");
            p = retrievePatient(getUser().getId());
            userDto = createUserDto();

            createPlatformTestData();
        } catch (Exception e) {
            log.error("Problem setting up test data", e);
            System.exit(1);
        }
    }

    // shutdown server
    @After
    public void tearDown() throws Exception {
        if (server != null) {
            server.stop();
        }
        log.info("Ending " + this.getClass().getName() + " web service test");
    }

    public void secureChannel() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", "PASSWORD",
                new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMINISTRATOR")});
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void createPlatformTestData() {
        try {
            assertNotNull("PlatformManager is null", platformManager);
            // create some test content

            if (orgs == null) {
                orgs = new ArrayList<Organization>();
            }
            if (apps == null) {
                apps = new ArrayList<Application>();
            }
            if (roles == null) {
                roles = new ArrayList<Role>();
            }
            if (countries == null) {
                countries = new ArrayList<Country>();
            }
            if (regulations == null) {
                regulations = new ArrayList<Regulation>();
            }
            if (overrides == null) {
                overrides = new ArrayList<RegulationOverride>();
            }
            if (applicationRoles == null) {
                applicationRoles = new ArrayList<ApplicationRole>();
            }
            if (licenses == null) {
                licenses = new ArrayList<License>();
            }
            if (applicationConfigurations == null) {
                applicationConfigurations = new ArrayList<ApplicationConfiguration>();
            }

            for (int j = 0; j < 2; j++) {
                Country country = new Country();
                country.setStatusCode("Name_" + System.currentTimeMillis());
                country.setCountryCode(RandomStringUtils.randomAlphabetic(6));
                country.setCurrencyCode(RandomStringUtils.randomAlphabetic(3));

                country = platformManager.saveOrUpdateCountry(country);

                countries.add(country);
            }

            for (int j = 0; j < 2; j++) {
                Role role = new Role();
                role.setName("Name_" + System.currentTimeMillis());
                role.setDescription("Test");
                role.setStatusCode("ROLE_" + RandomStringUtils.randomAlphabetic(10));

                role = platformManager.saveOrUpdateRole(role);

                roles.add(role);
            }

            for (int j = 0; j < 2; j++) {
                Regulation regulation = new Regulation();
                regulation.setName("Name_" + System.currentTimeMillis());
                regulation.setKey("Key_" + System.currentTimeMillis());
                regulation.setDescription("Test");
                regulation.setValue("Value_" + RandomStringUtils.randomAlphabetic(10));
                regulation.setType(RegulationTypeCd.STRING);
                regulation = platformManager.saveOrUpdateRegulation(regulation);

                // create one override for every regulation
                RegulationOverride override = new RegulationOverride();
                override.setOriginal(platformManager.getRegulation(regulation.getId()));
                override.setValue("Override_" + System.currentTimeMillis());

                try {
                    override.setCountry(countryDAO.getCountry(countries.get(j).getId()));
                } catch (PersistenceException e) {
                    log.error("Cannot retrieve country.");
                }

                override = platformManager.saveOrUpdateRegulationOverride(override);
                overrides.add(override);

                regulations.add(regulation);
            }

            for (int j = 0; j < 2; j++) {
                Organization org = new Organization();
                org.setName("Name_" + System.currentTimeMillis());
                org.setOrganizationStatus(OrganizationStatusCd.ACTIVE);
                org.setOrganizationType(OrganizationTypeCd.TEST);
                try {
                    org.setCountry(countryDAO.getCountry(CountryCd.UNITED_STATES));
                } catch (PersistenceException e) {
                    log.error("Cannot retrieve country.");
                }
                org = platformManager.saveOrUpdateOrganization(org);

                orgs.add(org);

                // add one user to the organization
                OrganizationUser ou = new OrganizationUser();
                ou.setOrganization(platformManager.getOrganization(org.getId()));
                ou.setUser(userManager.getUser(u.getId()));
                ou.setStatus(OrganizationUserStatusCd.ACTIVE);

                ou = platformManager.saveOrUpdateOrganizationUser(ou);

                // add some roles to member
                List<String> roleIds = new ArrayList<String>();
                roleIds.add(roles.get(0).getId());
                roleIds.add(roles.get(1).getId());

                platformManager.saveOrUpdateOrganizationUserRoles(ou.getId(), roleIds);

                // add applications to organization
                for (int m = 0; m < 2; m++) {
                    Application app = new Application();
                    app.setApplicationStatus(ApplicationStatusCd.TEST);
                    app.setApplicationType(ApplicationTypeCd.ADMINISTRATION);
                    app.setAppVersion("1.0");
                    app.setName("Test_" + System.currentTimeMillis());
                    app.setSwfUrl("http://localhost/test.swf");
                    app.setOwner(platformManager.getOrganization(org.getId()));
                    app.setPlatform(false);

                    app = platformManager.saveOrUpdateApplication(app);
                    apps.add(app);

                    for (int i = 0; i < 2; i++) {
                        License license = new License();
                        license.setApplication(app);
                        license.setDescription(RandomStringUtils.randomAlphabetic(10));
                        license.setExpirationDate(new Date());
                        license.setLicenseType(LicenseTypeCd.ANNUAL_SUBSCRIPTION);
                        license.setName(RandomStringUtils.randomAlphabetic(10));
                        license.setPrice((float) 10);
                        license.setStatus(LicenseStatusCd.ACTIVE);
                        license = platformManager.addApplicationLicense(app.getId(), license);

                        licenses.add(license);
                    }

                    for (int i = 0; i < 2; i++) {
                        ApplicationConfiguration ac = new ApplicationConfiguration();
                        ac.setApplication(app);
                        ac.setExportable(true);
                        ac.setKey(RandomStringUtils.randomAlphabetic(10));
                        ac.setValue(RandomStringUtils.randomAlphabetic(10));

                        ac = platformManager.addApplicationConfiguration(app.getId(), ac);
                        applicationConfigurations.add(ac);

                        OrganizationLicense ol = platformManager.saveOrUpdateOrganizationLicense(org.getId(), licenses.get(i).getId());

                        for (int k = 0; k < 2; k++) {
                            ApplicationConfigurationOverride override = new ApplicationConfigurationOverride(platformManager.getOrganizationLicense(ol.getId()));
                            override.setOriginal(platformManager.getApplicationConfiguration(ac.getId()));
                            override.setValue(RandomStringUtils.randomAlphabetic(10));

                            platformManager.saveOrUpdateApplicationConfigurationOverride(override);
                        }

                    }

                    for (int i = 0; i < 2; i++) {
                        ApplicationRole ar = platformManager.addApplicationRole(app.getId(), roles.get(i).getId());

                        applicationRoles.add(ar);
                    }

                    for (int i = 0; i < 2; i++) {

                        ResourceBundleEntry rbe = new ResourceBundleEntry();
                        rbe.setApplication(platformManager.getApplication(app.getId()));
                        rbe.setKey("Key_" + System.currentTimeMillis());
                        rbe.setValue("Value_" + System.currentTimeMillis());
                        try {
                            rbe.setCountry(countryDAO.getCountry(CountryCd.UNITED_STATES));
                        } catch (PersistenceException e) {
                            log.error("Cannot retrieve country.");
                        }
                        rbe = platformManager.saveOrUpdateResourceBundle(rbe);
                    }
                }

            }

        } catch (DomainException e) {
            log.error(e.getMessage(), e);
            fail("Couldn't create dummy data: " + e.getMessage());
        }
    }

    public List<Organization> getOrganizations() {
        return orgs;
    }

    public List<Application> getApplications() {
        return apps;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<ApplicationRole> getApplicationRoles() {
        return applicationRoles;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public List<ApplicationConfiguration> getApplicationConfigurations() {
        return applicationConfigurations;
    }

    public List<Regulation> getRegulations() {
        return regulations;
    }

    public List<RegulationOverride> getOverrides() {
        return overrides;
    }

    public User getUser() {
        return u;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public Patient getPatient() {
        return p;
    }

    /**
     * User and Patient entities are loosly coupled
     *
     * @param id
     * @return
     */
    private Patient retrievePatient(String id) {
        Patient result = null;

        try {
            result = patientManager.getPrimaryPatientByUserId(getUser().getId());
        } catch (DomainException ex) {
            log.error("Error: " + ex.getMessage(), ex);
            fail();
        }

        return result;
    }

    /**
     * Is used by many tests so though I'd put it here instead of UserDAOTest
     *
     * @return User
     */
    public User createUser(String username, String email) {
        User u = null;

        try {
            u = userManager.getUserByUsername(username);

            if (u == null) {
                log.info("Creating new user");

                // first create user
                u = new User();
                u.setUsername(username);
                u.setCountry(countryDAO.getCountry(CountryCd.UNITED_STATES));
                u.setPassword(PASSWORD);

                UserInfo ui = new UserInfo();
                ui.setFirstName("Bjorn");
                ui.setLastName("Harvold");

                Email e = new Email();
                e.setEmail(email);
                ui.addEmail(e);
                u.setUserInfo(ui);

                u = userManager.saveNewUser(u);
            }

            assertNotNull("User is null", u);

            log.info("User created successfully!");

        } catch (PersistenceException ex) {
            log.error("Error: " + ex.getMessage(), ex);
            fail();
        } catch (DomainException ex) {
            log.error("Error: " + ex.getMessage(), ex);
            fail();
        }

        return u;
    }

    private UserDto createUserDto() {
        UserDto dto = null;

        try {
            log.info("Checking to see if our test user is already in the database.... testUser...... where are you....");

            dto = userManager.getUserDtoByUsername("testUser4WS");
            if (dto == null) {
                log.info("Could not find our test user. Creating new user");
                dto = DtoTestFactory.createUserDto();
                dto = userManager.saveNewUserDto(dto);
            } else {
                log.info("Found user. No need to create new one");
            }

            assertNotNull("New userdto cannot be null", dto);

        } catch (DomainException ex) {
            log.error("Error: " + ex.getMessage(), ex);
            fail();
        }

        return dto;
    }

    public UserInfo createUserInfo(String email) {
        UserInfo userInfo = null;

        try {
            userInfo = new UserInfo();
            userInfo.setFirstName("Bjorn");
            userInfo.setLastName("Harvold");
            userInfo.setMiddleName("Erik");

            // now we save email
            Email e = new Email();
            e.setEmail(email);
            e.setPrimary(true);
            // defaulting to home email
            e.setEmailTypeCd(EmailTypeCd.HOME);
            List<Email> emails = new ArrayList<Email>();
            emails.add(e);
            userInfo.setEmails(emails);

            userInfoDAO.save(userInfo);

        } catch (PersistenceException ex) {
            log.error("Error:\n", ex);
            assertTrue(false);

        }

        return userInfo;
    }

    public PhrContact createPhrContact(Patient c) {
        PhrContact contact = new PhrContact();
        try {
            contact.setContact(createContact());
            contact.setPhr(c.getPhr());
            contact.setRecordType(RecordTypeCd.PHR);
            contact.setRecordCreatorId(c.getId());
            contact.setRecordCreatorType(EntityTypeCd.PATIENT);
            contact.setPhrType(PhrTypeCd.CONTACT);
            contact.setRecordStatus(RecordStatusCd.ACTIVE);
            contact.setEmergency(false);
            phrContactDAO.save(contact);
            phrContactDAO.flush();
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            assertTrue(false);
        }
        return contact;
    }

    public Contact createContact() throws PersistenceException {
        Contact contact = new Contact();

        contact.setContactType(ContactTypeCd.AUNT);

        contact.setLocations(createLocations());
        contact.setUserInfo(createUserInfo("bjorn@harvold.com"));

        contactDAO.save(contact);

        return contact;
    }

    protected List<Location> createLocations() throws PersistenceException {
        List<Location> locations = new ArrayList<Location>();
        locations.add(createLocation());

        return locations;
    }

    public void create10Users() {
        for (int i = 0; i < 10; i++) {
            log.info("Creating new user " + i);

            String username = "testUser" + i;
            String email = "bjorn" + i + "@harvold.com";

            createUser(username, email);

            log.info("User " + i + " created successfully!");
        }
    }

    public void create2Users() {
        for (int i = 0; i < 2; i++) {
            log.info("Creating new user " + i);
            String username = "testUser" + i;
            String email = "bjorn" + i + "@harvold.com";
            User u = createUser(username, email);

            log.info("User " + i + " created successfully!");
        }
    }

    public Location createLocation() throws PersistenceException {
        Location location = new Location();

        location.setAddress1("Address 1");
        location.setAddress2("Address 2");
        location.setCity("City");
        location.setCountryCd(countryDAO.getCountry(CountryCd.UNITED_STATES));
        location.setPrimary(Boolean.TRUE);

        locationDAO.save(location);

        return location;
    }

    public Practitioner createPractitioner(Patient c) throws PersistenceException {
        Practitioner practitioner = new Practitioner();

        practitioner.setRecordType(RecordTypeCd.PHR);
        practitioner.setPhr(c.getPhr());
        practitioner.setTitle("Mr Doctor");
        practitioner.setPhrType(PhrTypeCd.PRACTITIONER);
        practitioner.setContact(createContact());
        practitioner.setRecordStatus(RecordStatusCd.ACTIVE);
        practitioner.setRecordCreatorId(c.getId());
        practitioner.setRecordCreatorType(EntityTypeCd.PATIENT);
        practitioner.setEmergency(true);
        UserInfo userInfo = createUserInfo("bjorn@harvold.com");

        log.info("Saving Practitioner");
        practitionerDAO.save(practitioner);

        return practitioner;
    }

    public Organization createOrganization() throws DomainException {
        Organization org = new Organization();
        org.setName(RandomStringUtils.randomAlphabetic(20));
        org.setOrganizationStatus(OrganizationStatusCd.ACTIVE);
        org.setOrganizationType(OrganizationTypeCd.TEST);

        return platformManager.saveOrUpdateOrganization(org);

    }

    public Application createApplication() throws DomainException {
        Application app = new Application();
        app.setName(RandomStringUtils.randomAlphabetic(20));
        app.setApplicationStatus(ApplicationStatusCd.PUBLISHED);
        app.setApplicationType(ApplicationTypeCd.GLOBAL);
        app.setAppVersion("1.0");
        app.setDescription("Long description that says a bunch");
        app.setOwner(createOrganization());
        app.setSwfUrl("url");
        app.setPlatform(false);
        // for convenience we'll create a default license here as well
        License license = new License();
        license.setExpirationDate(new Date());
        license.setLicenseType(LicenseTypeCd.FREE);
        license.setPrice((float) 0.00);
        license.setApplication(app);
        license.setName("name");
        license.setDescription("desc");
        license.setStatus(LicenseStatusCd.ACTIVE);

        app = platformManager.saveOrUpdateApplication(app);

        platformManager.addApplicationLicense(app.getId(), license);

        return app;
    }


    // Spring IoC
    @Autowired
    public CountryDAO countryDAO = null;

    @Autowired
    public UserManager userManager = null;

    @Autowired
    public PatientManager patientManager = null;

    @Autowired
    public PatientDAO patientDAO = null;

    @Autowired
    public EmailDAO emailDAO = null;

    @Autowired
    public EmrDAO emrDAO = null;

    @Autowired
    public UserInfoDAO userInfoDAO = null;

    @Autowired
    public ContactDAO contactDAO = null;

    @Autowired
    public LocationDAO locationDAO = null;

    @Autowired
    public PractitionerDAO practitionerDAO = null;

    @Autowired
    public PhrContactDAO phrContactDAO;

    @Autowired
    protected PasswordEncryptor passwordEncryptor;

    @Autowired
    protected PersonalProfileDAO personalProfileDAO = null;

    @Autowired
    protected MapperIF mapperIF;

    @Autowired
    protected OrganizationUserDAO organizationUserDAO;

    @Autowired
    protected OrganizationLicenseDAO organizationLicenseDAO;

    @Autowired
    public RelationshipManager relationshipManager;

    @Autowired
    protected PlatformManager platformManager;
}
