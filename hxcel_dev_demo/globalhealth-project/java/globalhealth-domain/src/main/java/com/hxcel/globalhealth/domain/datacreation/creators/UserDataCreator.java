/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.datacreation.creators;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.UserManager;
import com.hxcel.globalhealth.domain.common.model.User;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.common.model.UserInfo;
import com.hxcel.globalhealth.domain.common.model.UserRole;
import com.hxcel.globalhealth.domain.common.model.enums.CountryCd;
import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.List;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * It expects a regular sql file to parse and create country objects for.
 * It expects a line like this:
 */
public class UserDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(UserDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;
    private Role role = null;

    public void create() throws DataCreatorException {

        try {
            if (file.exists()) {
                role = platformManager.getRoleByStatusCode("ROLE_ADMINISTRATOR");
                processCreation();
            } else {
                throw new DataCreatorException("CSV file could not be found");
            }
        } catch (DomainException e) {
            log.error(e.getMessage(), e);
        }

        log.info("Populated " + populated + " administrators in db");
        log.info("Omitted " + omitted + " administrators from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            processUsers();

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    /**
     * This could have been written nicer. Right now there's persistence code at the bottom
     * in a method that's called parse xml. lame!
     *
     * @throws IOException
     * @throws DataCreatorException
     */
    private void processUsers() throws IOException, DataCreatorException {

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> users = document.selectNodes("//users/user");

            User user = null;

            for (Element e : users) {
                List<Element> cells = e.elements();

                if (cells.size() == 5) {

                    String username = cells.get(0).getTextTrim();
                    String password = cells.get(1).getTextTrim();
                    String firstName = cells.get(2).getTextTrim();
                    String lastName = cells.get(3).getTextTrim();

                    user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setCountry(platformManager.getCountry(CountryCd.UNITED_STATES));

                    UserInfo ui = new UserInfo();
                    ui.setFirstName(firstName);
                    ui.setLastName(lastName);
                    user.setUserInfo(ui);

                    // now we save the user
                    User tmp = userManager.getUserByUsername(user.getUsername());

                    if (tmp == null) {
                        populated++;
                        // ready fr save all
                        user = userManager.saveNewUser(user);

                        // then we add user roles
                        List<Element> roles = cells.get(4).elements();
                        for (Element role : roles) {
                            Role r = platformManager.getRoleByStatusCode(role.getText());

                            userManager.saveOrUpdateUserRole(new UserRole(user, r));
                        }
                    } else {
                        log.info("Admin user already exists: " + user.getUsername());
                        omitted++;
                    }


                } else {
                    log.error("Not the right file. Expecting 2 tokens, found " + cells.size());
                }
            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            System.exit(1);
        } catch (DomainException e1) {
            log.error("Could not associates role with user. Role: " + role + " doesn't exist");
        }

    }

    // Spring IoC
    private Resource file;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PlatformManager platformManager;

    @Required
    public void setFile(Resource sqlFile) {
        this.file = sqlFile;
    }
}