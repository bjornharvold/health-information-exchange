/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.datacreation.creators;

import com.hxcel.globalhealth.domain.DomainException;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.model.Role;
import com.hxcel.globalhealth.domain.datacreation.DataCreator;
import com.hxcel.globalhealth.domain.datacreation.DataCreatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private List<Role> parseXMLFile() throws IOException {
        List<Role> result = new ArrayList<Role>();

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file.getInputStream());
            document.normalize();

            List<Element> roles = document.selectNodes("//roles/role");

            Role role = null;

            for (Element e : roles) {
                List<Element> cells = e.elements();

                if (cells.size() == 3) {

                    String name = cells.get(0).getTextTrim();
                    String description = cells.get(1).getTextTrim();
                    String statusCode = cells.get(2).getTextTrim();

                    role = new Role();
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
     * @throws com.hxcel.globalhealth.domain.datacreation.DataCreatorException
     *
     */
    private void persist(List<Role> roles) throws DataCreatorException {
        List<Role> dbList = new ArrayList<Role>();
        try {

            for (Role role : roles) {
                Role tmp = platformManager.getRoleByStatusCode(role.getStatusCode());

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
                for (Role role : dbList) {
                    platformManager.saveOrUpdateRole(role);
                }
            }
        } catch (DomainException e) {
            throw new DataCreatorException(e.getMessage(), e);
        }
    }

    // Spring IoC
    private Resource file;

    @Autowired
    private PlatformManager platformManager;

    @Required
    public void setFile(Resource sqlFile) {
        this.file = sqlFile;
    }
}