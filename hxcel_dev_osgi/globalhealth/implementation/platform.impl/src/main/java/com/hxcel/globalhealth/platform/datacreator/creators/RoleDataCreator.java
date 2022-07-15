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
import java.util.ArrayList;

import com.hxcel.globalhealth.platform.datacreator.DataCreator;
import com.hxcel.globalhealth.platform.datacreator.DataCreatorException;
import com.hxcel.globalhealth.platform.spec.dto.IRoleDto;
import com.hxcel.globalhealth.platform.spec.UserService;
import com.hxcel.globalhealth.platform.spec.PlatformException;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:19:22 AM
 * Inserts required roles into the system
 */
public class RoleDataCreator extends AbstractDataCreator implements DataCreator {
    private final static Logger log = LoggerFactory.getLogger(RoleDataCreator.class);
    private static int populated = 0;
    private static int omitted = 0;


    public void create() throws DataCreatorException {

        if (file.exists()) {
            processCreation();
        } else {
            throw new DataCreatorException("CSV file could not be found");
        }

        log.info("Populated " + populated + " roles in db");
        log.info("Omitted " + omitted + " roles from db. Already exists.");
    }

    private void processCreation() throws DataCreatorException {
        try {

            persist(parseXMLFile());

        } catch (IOException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    private List<IRoleDto> parseXMLFile() throws IOException {
        List<IRoleDto> result = new ArrayList<IRoleDto>();

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> roles = document.selectNodes("//roles/role");

            IRoleDto role = null;

            for (Element e : roles) {
                List<Element> cells = e.elements();

                if (cells.size() == 3) {

                    String name = cells.get(0).getTextTrim();
                    String description = cells.get(1).getTextTrim();
                    String statusCode = cells.get(2).getTextTrim();

                    role = userService.createRole();
                    role.setName(name);
                    role.setDescription(description);
                    role.setStatusCode(statusCode);

                    result.add(role);
                } else {
                    log.error("Not the right file. Expecting 2 tokens, found " + cells.size());
                }
            }
        } catch (DocumentException e) {
            log.error("Couldn't parse XML document. Exiting.");
            System.exit(1);
        }

        return result;
    }

    /**
     * Saves the admin users to the db before the application becomes active
     *
     * @param roles
     * @throws DataCreatorException
     *
     */
    private void persist(List<IRoleDto> roles) throws DataCreatorException {
        List<IRoleDto> dbList = new ArrayList<IRoleDto>();
        try {

            for (IRoleDto role : roles) {
                IRoleDto tmp = userService.getRoleByStatusCode(role.getStatusCode());

                if (tmp == null) {
                    dbList.add(role);
                    populated++;
                } else {
                    log.info("Role already exists with status code: " + role.getStatusCode());
                    omitted++;
                }
            }

            // ready fr save all
            if (dbList.size() > 0) {
                for (IRoleDto role : dbList) {
                    userService.saveOrUpdateRole(role);
                }
            }
        } catch (PlatformException e) {
            throw new DataCreatorException(e.getMessage(), e);
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