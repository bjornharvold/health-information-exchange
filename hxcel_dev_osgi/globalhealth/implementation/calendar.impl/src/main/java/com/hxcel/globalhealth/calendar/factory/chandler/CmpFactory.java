package com.hxcel.globalhealth.calendar.factory.chandler;

import com.hxcel.globalhealth.calendar.spec.*;
import com.hxcel.globalhealth.calendar.dto.ChandlerUser;
import com.hxcel.globalhealth.calendar.dto.ChandlerUserServiceDocument;
import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom4j.Dom4jXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * User: Bjorn Harvold
 * Date: Dec 28, 2008
 * Time: 7:18:18 PM
 * Responsibility:
 */
public final class CmpFactory {
    private final static Logger log = LoggerFactory.getLogger(CmpFactory.class);
    private final static String NAMESPACE = "http://osafoundation.org/cosmo/CMP";
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * Creates the url to successfully retrieve a list of calendar users
     *
     * @param name
     * @param sortBy
     * @param orderBy
     * @param index
     * @param maxResults
     * @return
     */
    public String createGetUsersRequest(String name, CalendarSortType sortBy, CalendarSortOrder orderBy, Integer index, Integer maxResults) {
        StringBuilder sb = new StringBuilder("/cmp/users?");

        if (index != null && maxResults != null) {
            sb.append("ps=");
            sb.append(maxResults);
            sb.append("&pn=");
            sb.append(index);
        }

        if (sortBy != null) {
            sb.append("&st=");
            sb.append(sortBy.name());
        }

        if (orderBy != null) {
            sb.append("&so=");
            sb.append(orderBy.name());
        }

        if (StringUtils.isNotBlank(name)) {
            sb.append("&q=");
            sb.append(name);
        }

        return sb.toString();
    }

    /**
     * Creates the url to retrieve a single user by username
     *
     * @param username
     * @return
     */
    public String createGetUserRequest(String username) {
        StringBuilder sb = new StringBuilder("/cmp/user/");
        sb.append(username);

        return sb.toString();
    }

    /**
     * Creates the url for retrieving the user count
     *
     * @return
     */
    public String createGetUserCountRequest() {
        return "/cmp/users/count";
    }

    /**
     * Parses the calendar users and returns a list
     *
     * @param response
     * @return
     */
    public List<CalendarUser> handleGetUsersResponse(String response) throws CalendarException {
        List<CalendarUser> result = null;

        if (StringUtils.isNotBlank(response)) {

            try {
                SAXReader reader = new SAXReader();
                Document root = reader.read(new StringReader(response));
                Map map = new HashMap();
                map.put("cmp", NAMESPACE);

                XPath xpath = new Dom4jXPath("//cmp:users/cmp:user");
                xpath.setNamespaceContext(new SimpleNamespaceContext(map));

                List<Node> users = xpath.selectNodes(root);

                if (users != null) {
                    result = new ArrayList<CalendarUser>();

                    for (Node user : users) {
                        /*
                        if (log.isTraceEnabled()) {
                            log.trace(user.asXML());
                        }
                        */
                        result.add(createUserFromNode(user));
                    }
                }

            } catch (DocumentException e) {
                throw new CalendarException(e.getMessage(), e);
            } catch (JaxenException e) {
                throw new CalendarException(e.getMessage(), e);
            }

        }

        return result;
    }

    /**
     * Parses the user retrieved from the calendar server
     *
     * @param response
     * @return
     * @throws CalendarException
     */
    public CalendarUser handleGetUserResponse(String response) throws CalendarException {
        CalendarUser result = null;

        if (StringUtils.isNotBlank(response)) {

            try {
                SAXReader reader = new SAXReader();
                Document root = reader.read(new StringReader(response));
                Map map = new HashMap();
                map.put("cmp", NAMESPACE);

                XPath xpath = new Dom4jXPath("//cmp:user");
                xpath.setNamespaceContext(new SimpleNamespaceContext(map));

                Node user = (Node) xpath.selectSingleNode(root);

                result = createUserFromNode(user);

            } catch (DocumentException e) {
                throw new CalendarException(e.getMessage(), e);
            } catch (JaxenException e) {
                throw new CalendarException(e.getMessage(), e);
            }

        }

        return result;
    }

    private CalendarUser createUserFromNode(Node user) throws CalendarException {
        ChandlerUser u = null;

        try {
            if (user != null) {
                u = new ChandlerUser();
                Element e = (Element) user;

                u.setUsername(e.elementText("username"));
                u.setAdministrator(Boolean.parseBoolean(e.elementText("administrator")));
                u.setCreated(df.parse(e.elementText("created")));
                u.setModified(df.parse(e.elementText("modified")));
                u.setEmail(e.elementText("email"));
                u.setFirstName(e.elementText("firstName"));
                u.setLastName(e.elementText("lastName"));
                u.setUrl(new URL(e.elementText("url")));

                if (e.element("homedirUrl") != null) {
                    u.setHomedirUrl(e.elementText("homedirUrl"));
                }
            }
        } catch (ParseException e) {
            throw new CalendarException(e.getMessage(), e);
        } catch (MalformedURLException e) {
            throw new CalendarException(e.getMessage(), e);
        }

        return u;
    }

    /**
     * @param username
     * @param firstName
     * @param lastName
     * @param password
     * @param email
     * @return
     * @throws CalendarException
     */
    public String createCreateUserBody(String username, String firstName, String lastName, String password, String email) throws CalendarException {
        QName rootName = DocumentFactory.getInstance().createQName("user", "", NAMESPACE);
        Element e = DocumentFactory.getInstance().createElement(rootName);
        Document root = DocumentFactory.getInstance().createDocument(e);

        if (StringUtils.isNotBlank(username)) {
            Element usernameE = e.addElement("username")
                    .addText(username);
        }
        if (StringUtils.isNotBlank(firstName)) {
            Element firstNameE = e.addElement("firstName")
                    .addText(firstName);
        }
        if (StringUtils.isNotBlank(lastName)) {
            Element lastNameE = e.addElement("lastName")
                    .addText(lastName);
        }
        if (StringUtils.isNotBlank(password)) {
            Element passwordE = e.addElement("password")
                    .addText(password);
        }
        if (StringUtils.isNotBlank(email)) {
            Element emailE = e.addElement("email")
                    .addText(email);
        }

        if (log.isTraceEnabled()) {
            log.trace(root.asXML());
        }

        return root.asXML();
    }

    /**
     * Creates the url necessary to retrieve the user service document from the chandler server
     *
     * @param username
     * @return
     */
    public String createGetUserServiceDocumentRequest(String username) {
        StringBuilder sb = new StringBuilder("/cmp/user/");
        sb.append(username);
        sb.append("/service");

        return sb.toString();
    }

    /**
     * Parses the USD xml and creates an object model of it
     *
     * @param response
     * @return
     */
    public UserServiceDocument handleGetUserServiceDocumentResponse(String response) throws CalendarException {
        ChandlerUserServiceDocument result = null;

        if (StringUtils.isNotBlank(response)) {
            result = new ChandlerUserServiceDocument();

            try {
                SAXReader reader = new SAXReader();
                Document root = reader.read(new StringReader(response));

                if (log.isTraceEnabled()) {
                    log.trace("User Service Document: \n" + root.asXML());
                }

                Map map = new HashMap();
                map.put("cmp", NAMESPACE+"/userService");

                XPath xpath = new Dom4jXPath("//cmp:service");
                xpath.setNamespaceContext(new SimpleNamespaceContext(map));

                Element service = (Element) xpath.selectSingleNode(root);

                result.setUsername(service.elementText("username"));

                Iterator iter = service.elementIterator("link");

                while (iter.hasNext()) {
                    Element link = (Element) iter.next();

                    if (StringUtils.equals(link.attributeValue("title"), "cmp")) {
                        result.setCmp(link.attributeValue("href"));
                        result.setCmpContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "dav")) {
                        result.setDav(link.attributeValue("href"));
                        result.setDavContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "davPrincipal")) {
                        result.setDavPrincipal(link.attributeValue("href"));
                        result.setDavPrincipalContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "davCalendarHome")) {
                        result.setDavCalendarHome(link.attributeValue("href"));
                        result.setDavCalendarHomeContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "atom")) {
                        result.setAtom(link.attributeValue("href"));
                        result.setAtomContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "mc")) {
                        result.setMc(link.attributeValue("href"));
                        result.setMcContentType(link.attributeValue("type"));
                    }
                }

                iter = service.elementIterator("base");

                while (iter.hasNext()) {
                    Element link = (Element) iter.next();

                    if (StringUtils.equals(link.attributeValue("title"), "atom")) {
                        result.setAtomBase(link.attributeValue("href"));
                        result.setAtomBaseContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "mc")) {
                        result.setMcBase(link.attributeValue("href"));
                        result.setMcBaseContentType(link.attributeValue("type"));
                        result.setMcContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "pim")) {
                        result.setPimBase(link.attributeValue("href"));
                        result.setPimBaseContentType(link.attributeValue("type"));
                    } else if (StringUtils.equals(link.attributeValue("title"), "webcal")) {
                        result.setWebcalBase(link.attributeValue("href"));
                        result.setWebcalBaseContentType(link.attributeValue("type"));
                    }
                }

            } catch (DocumentException e) {
                throw new CalendarException(e.getMessage(), e);
            } catch (JaxenException e) {
                throw new CalendarException(e.getMessage(), e);
            }
        }

        return result;
    }
}
