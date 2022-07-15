package com.hxcel.globalhealth.cms.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Stack;

/**
 * User: Bjorn Harvold
 * Date: Dec 1, 2006
 * Time: 8:49:17 PM
 * This class:
 * - parses the exported xml files from Magnolia
 * - reads in the resource bundles that carry the properties
 * - if the property node does not match anything in the resource
 * bundles it will print an error and exit
 * - when all property nodes have been matched they will all be reinserted into the
 * jackrabbit repo
 * - the node will be the property name, the node will have a value that contains the text
 */
public class Magnolia2SlingConverter {
    private final static Logger log = LoggerFactory.getLogger(Magnolia2SlingConverter.class);
    private static ClassPathXmlApplicationContext ctx = null;
    private boolean missingKey = false;
    protected static final DocumentBuilderFactory BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private Stack<String> stack = new Stack<String>();
    private int depth = 0;
    private boolean popIt = false;

    /*
    public static void main(String[] args) {
        Magnolia2Jackrabbit mj = new Magnolia2Jackrabbit();

        mj.migrate();
    }

    private void migrate() {
        ctx = new ClassPathXmlApplicationContext("com/hxcel/globalhealth/cms/migration/spring-migration-beans.xml");

        parseHelpProperties();

        parseMarketingProperties();

        validateDtos();

        if (missingKey) {
            log.error("Errors were encountered. Not persisting any records. See log for details. Writing property file containing missing keys");
        } else {
            addContentToJackrabbit();
        }
    }

    private void addContentToJackrabbit() {

    }

    private void parseMarketingProperties() {
        Resource xml = new ClassPathResource("com/hxcel/globalhealth/cms/migration/website.marketing.xml");

        try {
            if (xml.exists()) {
                Document document = BUILDER_FACTORY.newDocumentBuilder().parse(xml.getInputStream());

                // grab the root entity
                Node node = document.getFirstChild();

                parse(node);
            }
        } catch (ParserConfigurationException e) {
            log.error("Could not parse website.marketing.xml");
        } catch (IOException e) {
            log.error("Could not find file");
        } catch (SAXException e) {
            log.error("SAX Exception", e);
        }

    }

    private void parseHelpProperties() {
        Resource xml = new ClassPathResource("com/hxcel/globalhealth/cms/migration/website.help.xml");

        try {
            if (xml.exists()) {
                Document document = BUILDER_FACTORY.newDocumentBuilder().parse(xml.getInputStream());

                // grab the root entity
                Node node = document.getFirstChild();

                parse(node);
            }
        } catch (ParserConfigurationException e) {
            log.error("Could not parse website.help.xml");
        } catch (IOException e) {
            log.error("Could not find file");
        } catch (SAXException e) {
            log.error("SAX Exception", e);
        }
    }

    private void validateDtos() {
        MessageSource messageSource = getMessageSource();
        String resourceBundleKey = null;
        BufferedWriter fw = getWriter();

        for (HelpContentDto dto : dtos) {
            String resourceKey = dto.getResourceKey();

            try {
                resourceBundleKey = messageSource.getMessage(resourceKey, null, null);
            } catch (NoSuchMessageException e) {
                log.warn(e.getMessage());
                missingKey = true;

                try {
                    fw.write(resourceKey);
                    fw.write("=");
                    fw.newLine();
                } catch (IOException e1) {
                    log.error("IOException", e1);
                }
            }
        }

        try {
            fw.close();
        } catch (IOException e) {
            log.error("Could not close writer", e);
        }

    }

    private BufferedWriter getWriter() {
        File newFile = new File("missingKeys.properties");
        BufferedWriter result = null;

        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

            result = new BufferedWriter(new FileWriter(newFile));

        } catch (IOException e) {
            log.error("Could not write to file", e);
        }

        return result;
    }

    private void parse(Node node) {
        if (node.hasChildNodes()) {
            // grab all the child nodes
            NodeList children = node.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node childNode = children.item(i);

                if (childNode.hasAttributes() && childNode.getNodeName().equals("sv:node")) {
                    NamedNodeMap attributes = childNode.getAttributes();

                    // see if we want to ignore this directory
                    String name = attributes.getNamedItem("sv:name").getNodeValue();

                    if (!name.equals("MetaData") && !name.equals(".activationInfo")) {
                        // ok this means we're working with a real node
                        if (name.equals("0")) {
                            // this means we should get the content property from that node
                            // and create a new dto - now we have all we need
                            HelpContentDto dto = new HelpContentDto();
                            dto.setResourceKey(makeResourceKey());
                            dto.setLocale(Locale.US);

                            if (childNode.hasChildNodes()) {
                                // looping through properties to find content
                                NodeList properties = childNode.getChildNodes();

                                for (int j = 0; j < properties.getLength(); j++) {
                                    Node propChildNode = properties.item(j);

                                    if (propChildNode.hasAttributes()) {
                                        NamedNodeMap propAttributes = propChildNode.getAttributes();

                                        // see if we want to ignore this directory
                                        String propAttributeName = propAttributes.getNamedItem("sv:name").getNodeValue();

                                        if (propAttributeName.equals("content")) {
                                            String content = propChildNode.getFirstChild().getNextSibling().getTextContent();
                                            dto.setContent(content.replaceAll("\n", ""));
                                        }
                                    }
                                }
                            }

                            log.info(dto);
                            dtos.add(dto);
                        } else {
                            // we need to recurse and just append the key - EXCEPT for the 2 conditions below
                            if (!name.equals("en") && !name.equals("contentParagraph")) {
                                stack.push(name);
                                depth++;
                            }

                            parse(childNode);

                            if (!name.equals("en") && !name.equals("contentParagraph") && depth > 0) {
                                stack.pop();
                            }
                        }
                    }
                }
            }
        }
    }

    private String makeResourceKey() {
        StringBuffer result = null;

        if (stack != null && !stack.isEmpty()) {
            for (int i = 0; i < stack.size(); i++) {
                String key = stack.get(i);

                if (result == null) {
                    result = new StringBuffer();
                }

                result.append(key);

                if ((i + 1) < stack.size()) {
                    result.append(".");
                }
            }
        }

        return result.toString();
    }
*/
}
