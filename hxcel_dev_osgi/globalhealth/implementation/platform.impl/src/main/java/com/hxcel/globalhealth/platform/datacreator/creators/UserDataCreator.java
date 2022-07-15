/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.platform.datacreator.creators;

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

import com.hxcel.globalhealth.platform.datacreator.DataCreator;
import com.hxcel.globalhealth.platform.datacreator.DataCreatorException;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.PlatformException;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.spec.dto.IUserDto;
import com.hxcel.globalhealth.common.spec.model.enums.CountryCd;

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
    private IRoleDto role = null;

    public void create() throws DataCreatorException {

        try {
            if (file.exists()) {
                role = userService.getRoleByStatusCode("ROLE_ADMINISTRATOR");
                processCreation();
            } else {
                throw new DataCreatorException("CSV file could not be found");
            }
        } catch (PlatformException e) {
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

            IUserDto user = null;

            for (Element e : users) {
                List<Element> cells = e.elements();

                if (cells.size() == 5) {

                    String username = cells.get(0).getTextTrim();
                    String password = cells.get(1).getTextTrim();
                    String firstName = cells.get(2).getTextTrim();
                    String lastName = cells.get(3).getTextTrim();

                    user = userService.createUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setCountry(CountryCd.UNITED_STATES);

                    user.getUserInfo().setFirstName(firstName);
                    user.getUserInfo().setLastName(lastName);

                    // now we save the user
                    IUserDto tmp = userService.getUserByUsername(user.getUsername());

                    if (tmp == null) {
                        populated++;
                        // ready fr save all
                        user = userService.saveNewUser(user);

                        // then we add user roles
                        List<Element> roles = cells.get(4).elements();
                        for (Element role : roles) {
                            IRoleDto r = userService.getRoleByStatusCode(role.getText());

                            userService.addUserRole(user.getId(), r.getId());
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
        } catch (PlatformException e1) {
            log.error("Could not associates role with user. Role: " + role + " doesn't exist");
        }

    }

    // Spring IoC
    private Resource file;

    @Autowired
    private UserService userService;

    @Required
    public void setFile(Resource sqlFile) {
        this.file = sqlFile;
    }
}