package com.hxcel.globalhealth.domain.common;


import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hxcel.globalhealth.AbstractIntegrationBaseTest;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.dto.UserDto;
import com.hxcel.globalhealth.domain.utils.hibernate.PersistenceException;
import com.hxcel.globalhealth.domain.DomainException;

/**
 * User: Bjorn Harvold
 * Date: Oct 20, 2008
 * Time: 3:35:11 PM
 * Description:
 */
public class UserManagerTest extends AbstractIntegrationBaseTest {
    private static final Logger log = LoggerFactory.getLogger(UserManagerTest.class);

    @Test
    public void testSaveNewUser() {
        assertTrue(true);
    }

    @Test
    public void testUserDAO() {

        // test if user got created
        assertTrue(getUser().getId() != null);

        // do a password check
        assertTrue(passwordEncryptor.checkPassword(PASSWORD, getUser().getPassword()));

        // get user we just inserted
        User u2 = getUserByUsername();
        assertTrue(u2 != null);

        // see if it is the same user
        assertTrue(getUser().equals(u2));

    }

    @Test
    public void getUserCount() {
        Integer result = null;

        try {
            result = userManager.getUserCount("bjorn");
            assertTrue("User count should be at least 1", result > 0);
        } catch (DomainException e) {
            log.error("Error:\n", e);
        }

    }

    private User getUserByUsername() {
        User user = null;

        try {
            log.info("Retrieving user by username and password");

            user = userManager.getUserByUsername("testUser");

            log.info("User retrieved successfully by username and password!");

        } catch (DomainException ex) {
            log.error("Error:\n", ex);
        }

        return passwordEncryptor.checkPassword(PASSWORD, user.getPassword()) ? user : null;
    }

}
