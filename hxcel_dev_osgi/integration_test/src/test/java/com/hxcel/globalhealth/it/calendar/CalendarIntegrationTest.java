package com.hxcel.globalhealth.it.calendar;

import org.springframework.osgi.util.OsgiServiceReferenceUtils;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hxcel.globalhealth.calendar.spec.*;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 10, 2008
 * Time: 4:34:13 PM
 * Responsibility: Tests out the cms module functionality
 */
public class CalendarIntegrationTest extends AbstractCalendarIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(CalendarIntegrationTest.class);

    public void testServiceAvailability() {
        log.info("Testing Calendar OSGi Service availability");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, SERVICE_CALENDAR, null);

        assertNotNull("ServiceReference for " + SERVICE_CALENDAR + " is null", sr);

        CalendarService service = (CalendarService) bundleContext.getService(sr);

        log.info("CalendarService bundle is available! " + service.toString());

        Boolean available = service.isAvailable();

        log.info("Is CalendarService also available for testing: " + available);

        log.info("Testing Calendar OSGi Service availability COMPLETE");
    }

    public void testGetUsers() {
        log.info("Testing Calendar OSGi Service getUsers");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, SERVICE_CALENDAR, null);

        assertNotNull("ServiceReference for " + SERVICE_CALENDAR + " is null", sr);

        CalendarService service = (CalendarService) bundleContext.getService(sr);

        log.info("CalendarService bundle is available! " + service.toString());

        Boolean available = service.isAvailable();

        log.info("Is CalendarService also available for testing: " + available);

        if (available) {
            log.info("Service is available. Let's try to grab the users");

            try {
                List<CalendarUser> list = service.getCmpService().getUsers(null, CalendarSortType.username, CalendarSortOrder.ascending, 0, 10);

                log.info("User list might be null but as long as the call works it's ok");

                if (list != null) {
                    log.info("Retrieved " + list.size() + " calendar users");
                }
            } catch (CalendarException e) {
                fail("Could not retrieve users: " + e.getMessage());
            }
        }

        log.info("Testing Calendar OSGi Service getUsers COMPLETE");
    }

    public void testGetUser() {
        log.info("Testing Calendar OSGi Service getUser");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, SERVICE_CALENDAR, null);

        assertNotNull("ServiceReference for " + SERVICE_CALENDAR + " is null", sr);

        CalendarService service = (CalendarService) bundleContext.getService(sr);

        log.info("CalendarService bundle is available! " + service.toString());

        Boolean available = service.isAvailable();

        log.info("Is CalendarService also available for testing: " + available);

        if (available) {
            log.info("Service is available. Let's try to grab user 'bjorn'");

            try {
                CalendarUser user = service.getCmpService().getUser("bjorn");

                log.info("Result might be null but as long as the call works it's ok");

                if (user != null) {
                    log.info("Retrieved user 'bjorn': " + user.toString());
                }
            } catch (CalendarException e) {
                fail("Could not retrieve user 'bjorn': " + e.getMessage());
            }
        }

        log.info("Testing Calendar OSGi Service getUser COMPLETE");
    }

    public void testGetUsersCount() {
        log.info("Testing Calendar OSGi Service getUserCount");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, SERVICE_CALENDAR, null);

        assertNotNull("ServiceReference for " + SERVICE_CALENDAR + " is null", sr);

        CalendarService service = (CalendarService) bundleContext.getService(sr);

        log.info("CalendarService bundle is available! " + service.toString());

        Boolean available = service.isAvailable();

        log.info("Is CalendarService also available for testing: " + available);

        if (available) {
            log.info("Service is available. Let's try to grab user 'bjorn'");

            try {
                Integer count = service.getCmpService().getUserCount();

                assertNotNull("Count is null", count);

                log.info("There are " + count + " registered calendar users");
            } catch (CalendarException e) {
                fail("Could not retrieve user 'bjorn': " + e.getMessage());
            }
        }

        log.info("Testing Calendar OSGi Service getUserCount COMPLETE");
    }

    public void testHandleUserFunctionality() {
        log.info("Testing Calendar OSGi Service createUser / updateUser / deleteUser / getUserServiceDocument");

        ServiceReference sr = OsgiServiceReferenceUtils.getServiceReference(bundleContext, SERVICE_CALENDAR, null);

        assertNotNull("ServiceReference for " + SERVICE_CALENDAR + " is null", sr);

        CalendarService service = (CalendarService) bundleContext.getService(sr);

        log.info("CalendarService bundle is available! " + service.toString());

        Boolean available = service.isAvailable();

        log.info("Is CalendarService also available for testing: " + available);

        if (available) {
            log.info("Service is available. Let's try to grab user 'bjorn'");

            try {
                String username = "testuser_" + System.currentTimeMillis();
                String firstName = "First_" + System.currentTimeMillis();
                String lastName = "Last_" + System.currentTimeMillis();
                String password = Long.toString(System.currentTimeMillis());
                String email = "testuser@test_" + System.currentTimeMillis() + ".com";

                log.info("Creating user...");
                service.getCmpService().createUser(username, firstName, lastName, password, email);
                log.info("Creating user... COMPLETE");

                log.info("Updating user...");
                service.getCmpService().updateUser(username, null, firstName+"_new", null, null, null);
                log.info("Updating user... COMPLETE");

                log.info("Retrieving user service document...");
                UserServiceDocument usd = service.getCmpService().getUserServiceDocument(username);
                assertNotNull("User Service Document is null", usd);
                log.info("Retrieving user service document... COMPLETE");

                log.info("Deleting user...");
                service.getCmpService().deleteUser(username);
                log.info("Deleting user... COMPLETE");

            } catch (CalendarException e) {
                fail("Could not create test user" + e.getMessage());
            }
        }

        log.info("Testing Calendar OSGi Service createUser / updateUser / deleteUser / getUserServiceDocument COMPLETE");
    }
}