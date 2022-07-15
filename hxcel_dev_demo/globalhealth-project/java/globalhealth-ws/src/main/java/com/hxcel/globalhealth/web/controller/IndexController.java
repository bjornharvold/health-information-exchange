package com.hxcel.globalhealth.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: IndexController</p>
 * <p>Description: Handles forwarding to Flex front end application. Everything will be handled from there.</p>
 *
 * @author Bjorn Harvold
 */
public class IndexController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);


    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView();
    }
}
