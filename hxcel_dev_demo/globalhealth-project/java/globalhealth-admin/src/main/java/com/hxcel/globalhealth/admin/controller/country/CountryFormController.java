package com.hxcel.globalhealth.admin.controller.country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;
import org.springmodules.web.propertyeditors.ReflectivePropertyEditor;

import com.hxcel.globalhealth.domain.platform.model.Organization;
import com.hxcel.globalhealth.domain.platform.PlatformManager;
import com.hxcel.globalhealth.domain.common.model.Country;
import com.hxcel.globalhealth.admin.controller.country.validator.CountryValidator;

import javax.annotation.Resource;

/**
 * <p>Title: CountryFormController</p>
 * <p>Description: Handles working with a single country</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/country/edit.admin")
@SessionAttributes(types = Country.class)
public class CountryFormController {
    private static final Logger log = LoggerFactory.getLogger(CountryFormController.class);
    private final PlatformManager platformManager;
    private String FORM = "country.edit";


    @Autowired
    public CountryFormController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        Country country = null;

        if (StringUtils.isBlank(id)) {
            country = new Country();
        } else {
            country = platformManager.getCountry(id);
        }

        model.addAttribute("country", country);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "country")Country country,
                                   BindingResult result, SessionStatus status) throws Exception {
        new CountryValidator().validate(country, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {

            if (StringUtils.isBlank(country.getId())) {
                view = "redirect:/secure/country/search.admin?view=countries.data";
            } else {
                view = "redirect:/secure/country/view.admin?view=country.data&id=" + country.getId();
            }

            platformManager.saveOrUpdateCountry(country);
            status.setComplete();

        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Organization.class, organizationPropertyEditor);
    }

    @Resource(name = "organizationPropertyEditor")
    private ReflectivePropertyEditor organizationPropertyEditor;
}