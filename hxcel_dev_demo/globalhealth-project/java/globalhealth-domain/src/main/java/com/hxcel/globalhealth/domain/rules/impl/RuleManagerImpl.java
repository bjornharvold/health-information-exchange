/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.domain.rules.impl;

import com.hxcel.globalhealth.domain.rules.Forward;
import com.hxcel.globalhealth.domain.rules.Rule;
import com.hxcel.globalhealth.domain.rules.RuleException;
import com.hxcel.globalhealth.domain.rules.RuleManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * User: Bjorn Harvold
 * Date: Nov 25, 2005
 * Time: 10:39:08 AM

 * <p/>
 * Description: This is the implementation of the rule manager.
 * @Deprecated
 */
public class RuleManagerImpl implements RuleManager, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(RuleManagerImpl.class);
    private Map<String, Rule> ruleMap = null;
    private final static String _SCOPE_SITE = "site";
    private final static String _SCOPE_ACTION = "action";
    private List<String> _excludePatterns = null;


    /**
     * This method will get called by the filter. The first request will init the rules xml file.
     * This had to be done because during startup there is still no WebApplicationContext and we
     * need that to be able to refer to any of the beans we're referencing. If there's another way to do
     * this at startup, by all means change this.
     */
    public void init() {
        try {
            if (rules != null) {
                log.info("============== Parsing business rules this one time. Can this occur during startup instead? ==============");
                for (Resource resource : rules) {
                    // init each uri
                    parse(resource.getInputStream());
                }
                log.info("============== Parsing business rules complete==============");
            }
        } catch (IOException ex) {
            log.error("Couldn't retrieve input stream from resource");
            // we don't want the application to run without proper rules set up
            System.exit(1);
        }
        catch (RuleException e) {
            log.error(e.getMessage());
            // we don't want the application to run without proper rules set up
            System.exit(1);
        }
    }

    /**
     * Go through all rules looking for scope = site and execute rule
     *
     * @return
     *
     */
    public Forward executeSiteRules(Map<String, Object> map) throws RuleException {

        Collection<Rule> col;
        Forward result = null;
        String path = (String) map.get("requestUri");

        // first see if the page is an excluded rules pattern
        if (!isExcludedPath(path)) {
            if (ruleMap != null) {
                col = ruleMap.values();

                for (Rule rule : col) {
                    if (rule.getScope().equals(_SCOPE_SITE)) {
                        if (log.isTraceEnabled()) {
                            log.trace("===================================");
                            log.trace("Processing site rule: " + rule.getName());
                        }
                        // execute dependencies first
                        List<Rule> dependencies = rule.getDependencies();

                        if (dependencies != null) {
                            if (log.isTraceEnabled()) {
                                log.trace("Dependencies exists. Need to validate those first.");
                            }
                            for (Rule depRule : dependencies) {
                                if (log.isTraceEnabled()) {
                                    log.trace("Processing dependecy rule: " + depRule.getName());
                                }
                                // this rule might have been run before in this request so we
                                // don't have to run it again
                                if (!depRule.executedAndPassed()) {
                                    result = depRule.validate(map);

                                    if (result != null) {
                                        if (log.isTraceEnabled()) {
                                            log.trace("Dependency rule: " + depRule.getName() + " didn't pass. Returning forward");
                                        }
                                        return result;
                                    }
                                    if (log.isTraceEnabled()) {
                                        log.trace("Dependency passed validation successfully!");
                                    }
                                } else {
                                    if (log.isTraceEnabled()) {
                                        log.trace("Dependency has already passed through validation successfully!");
                                    }
                                }
                            }
                        }

                        // ok now execute the real rule
                        result = rule.validate(map);

                        if (result != null) {
                            if (log.isTraceEnabled()) {
                                log.trace("Rule " + rule.getName() + " didn't pass. Returning forward");
                                log.trace("===================================");
                            }
                            return result;
                        } else {
                            if (log.isTraceEnabled()) {
                                log.trace("Rule " + rule.getName() + " passed validation successfully!");
                                log.trace("===================================");
                            }
                        }
                    }
                }
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("No site rules are available");
                }
            }
        }

        return result;
    }

    /**
     * Go through all controller rules and see if they map to path
     *
     * @return
     * @throws RuleException
     */
    public Forward executeActionRules(Map<String, Object> map) throws RuleException {
        Forward result = null;
        Collection<Rule> col;
        String path = (String) map.get("requestUri");

        // first see if the page is an excluded rules pattern
        if (!isExcludedPath(path)) {
            if (ruleMap != null) {
                col = ruleMap.values();

                for (Rule rule : col) {
                    // so if it's a site rule and it hasn't passed yet. Run it!
                    if (rule.getScope().equals(_SCOPE_ACTION) && !rule.executedAndPassed()) {
                        // let's see if the mapping fits
                        if (matches(path, rule.getPatterns())) {
                            if (log.isTraceEnabled()) {
                                log.trace("===================================");
                                log.trace("Processing controller rule: " + rule.getName());
                            }
                            // execute dependencies first
                            List<Rule> dependencies = rule.getDependencies();

                            if (dependencies != null) {
                                if (log.isTraceEnabled()) {
                                    log.trace("Dependencies exists. Need to validate those first.");
                                }
                                for (Rule depRule : dependencies) {
                                    if (log.isTraceEnabled()) {
                                        log.trace("Processing dependecy rule: " + depRule.getName());
                                    }
                                    // this rule might have been run before in this request so we
                                    // don't have to run it again
                                    if (!depRule.executedAndPassed()) {
                                        result = depRule.validate(map);

                                        if (result != null) {
                                            if (log.isTraceEnabled()) {
                                                log.trace("Dependency rule: " + depRule.getName() + " didn't pass. Returning forward");
                                            }
                                            return result;
                                        }
                                        if (log.isTraceEnabled()) {
                                            log.trace("Dependency passed validation successfully!");
                                        }
                                    } else {
                                        if (log.isTraceEnabled()) {
                                            log.trace("Dependency has already passed through validation successfully!");
                                        }
                                    }
                                }
                            }

                            // ok now execute the real rule
                            result = rule.validate(map);

                            if (result != null) {
                                if (log.isTraceEnabled()) {
                                    log.trace("Rule " + rule.getName() + " didn't pass. Returning forward");
                                    log.trace("===================================");
                                }
                                return result;
                            } else {
                                if (log.isTraceEnabled()) {
                                    log.trace("Rule " + rule.getName() + " passed validation successfully!");
                                    log.trace("===================================");
                                }
                            }
                        }
                    }
                }
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("No controller rules are available");
                }
            }
        }
        return result;
    }

    /**
     * Checks the list of excluded patterns defnied in the rules.xml file
     * to see if the current path should be validated at all
     *
     * @param path
     * @return boolean
     */
    private boolean isExcludedPath(String path) {
        boolean result = false;

        if (_excludePatterns != null) {
            for (String pattern : _excludePatterns) {
                if (path.matches(pattern)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Path matches excluded pattern: \"" + pattern + "\". Action rules will not be executed.");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Create Rule objects to put in the map on this class
     *
     * @param is InputStream
     * @throws RuleException
     */
    private void parse(InputStream is) throws RuleException {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            document.normalize();

            List<Element> excludePatterns = document.selectNodes("//rules/global-exclude-patterns/pattern");
            for (Element element : excludePatterns) {
                if (_excludePatterns == null) {
                    _excludePatterns = new ArrayList<String>();
                }
                _excludePatterns.add(element.getStringValue());
            }

            // get all rules in xml file
            List<Node> list = document.selectNodes("//rules/rule");

            for (Node node : list) {
                createRule(node);
            }
            // set dependencies
            for (Node node : list) {
                createDependencies(node);
            }

            if (log.isTraceEnabled()) {
                printRules();
            }
        }
        catch (DocumentException de) {
            log.error("Couldn't find file: " + de.getMessage());
            throw new RuleException("IO Exception. Could not find file?", de);
        }
    }

    /**
     * Creates a Rule based on the information in the xml file
     *
     * @param node
     */
    private void createRule(Node node) throws RuleException {
        Rule rule;

        try {
            if (node != null) {
                rule = (Rule) ctx.getBean(node.valueOf("@ref-bean"));
                rule.setName(node.valueOf("@name"));
                rule.setScope(node.valueOf("@scope"));
                rule.setDescription(node.valueOf("description"));
                rule.setPatterns(createMappings(node));
                rule.setForwards(createForwards(node));

                if (ruleMap == null) {
                    ruleMap = new TreeMap<String, Rule>();
                }

                // and add to map if not already present
                // we're checking for duplicate key
                if (ruleMap.containsKey(rule.getName())) {
                    log.info("WARNING: duplicate key!!! Key: " + rule.getName());
                } else {
                    ruleMap.put(rule.getName(), rule);
                }
            }
        } catch (ClassCastException ccx) {
            String s = "Spring bean is not referencing an implementation of Rule";
            log.error(s);
            throw new RuleException(s, ccx);
        }
    }

    /**
     * Creates a list of all the pattern mappings to run this controller rule on
     * @param node
     * @return Map
     */
    private List<String> createMappings(Node node) {
        List<Element> list;
        List<String> result = null;

        list = node.selectNodes("patterns/pattern");

        for (Element e : list) {
            if (result == null) {
                result = new ArrayList<String>();
            }
            result.add(e.getStringValue());
        }

        return result;
    }

    /**
     * Created all Forward object for this rule
     *
     * @param node
     * @return List
     */
    private Map<String, Forward> createForwards(Node node) {
        Map<String, Forward> forwards = null;
        List<Node> list;

        if (node != null) {
            forwards = new TreeMap<String, Forward>();
            list = node.selectNodes("forwards/forward");

            for (Node node1 : list) {
                Forward f = new Forward();
                f.setName(node1.valueOf("@name"));
                f.setPath(node1.valueOf("@path"));
                f.setRedirect(Boolean.parseBoolean(node1.valueOf("@redirect")));

                forwards.put(f.getName(), f);
            }
        }

        return forwards;
    }

    /**
     * Creates all dependency objects for this rule. This rule will run after all rules
     * have been loaded so we can get their referring object.
     *
     * @param node
     * @return List
     */
    private List<Rule> createDependencies(Node node) throws RuleException {
        List<Rule> dependencies = null;
        List<Node> list;

        if (node != null) {
            dependencies = new ArrayList<Rule>();
            list = node.selectNodes("dependencies/dependency");

            if (!list.isEmpty()) {
                for (Node node1 : list) {
                    if (ruleMap.containsKey(node1.valueOf("@ref-rule"))) {
                        // get referring rule
                        dependencies.add(ruleMap.get(node1.valueOf("@ref-rule")));
                    } else {
                        String error = "Dependency refers to a rule that doesn't exist: " + node1.valueOf("@ref-rule");
                        log.error(error);
                        throw new RuleException(error);
                    }
                }

                // associate dependencies with the rule
                if (ruleMap.containsKey(node.valueOf("@name"))) {
                    ruleMap.get(node.valueOf("@name")).setDependencies(dependencies);
                }
            }
        }

        return dependencies;
    }

    /**
     * Prints out the rules that were parsed
     */
    private void printRules() {
        if (ruleMap != null) {
            for (Rule rule : ruleMap.values()) {
                log.trace("============== BEGIN " + rule.getName() + " ==============");
                printRule(rule);
                if (rule.getDependencies() != null) {
                    for (Rule depRule : rule.getDependencies()) {
                        log.trace("---> Dependency rule: ");
                        printRule(depRule);
                    }
                }
                if (rule.getForwards() != null) {
                    for (Forward forward : rule.getForwards().values()) {
                        log.trace("Forward name: " + forward.getName() + ", path: " + forward.getPath());
                    }
                }
                log.trace("============== END " + rule.getName() + " ==============");
            }
        }
    }

    /**
     * Matches up all the patterns to see if they match the current path
     * @param path
     * @param patterns
     * @return boolean
     */
    private boolean matches(String path, List<String> patterns) {

        if (StringUtils.isNotBlank(path) && patterns != null) {
            for (String pattern : patterns) {
                if (path.matches(pattern)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Prints out a specific rule.
     *
     * @param rule
     */
    private void printRule(Rule rule) {
        log.trace("Name: " + rule.getName());
        log.trace("Description: " + rule.getDescription());
        log.trace("Mapping: " + rule.getPatterns());
        log.trace("Scope: " + rule.getScope());
    }

    // Spring IoC
    private Resource[] rules = null;
    private ApplicationContext ctx = null;

    public void setRules(Resource[] rules) {
        this.rules = rules;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}