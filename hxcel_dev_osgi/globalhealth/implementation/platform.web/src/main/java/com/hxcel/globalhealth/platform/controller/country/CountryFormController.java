package com.hxcel.globalhealth.platform.controller.country;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.controller.country.validator.CountryValidator;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.dto.IOrganizationDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.beans.PropertyEditor;

/**
 * <p>Title: CountryFormController</p>
 * <p>Description: Handles working with a single country</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/country/edit.admin")
@SessionAttributes("countryForm")
public class CountryFormController {
    private static final Logger log = LoggerFactory.getLogger(CountryFormController.class);
    private final static String FORM = "country.edit";

    /* cannot for OSGi
    @Autowired
    public CountryFormController(CountryService countryService) {
        this.countryService = countryService;
    }
    */

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false)String id, ModelMap model) throws Exception {
        ICountryDto country = null;

        if (StringUtils.isBlank(id)) {
            country = countryService.createCountry();
        } else {
            country = countryService.getCountry(id);
        }

        model.addAttribute("countryForm", country);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "countryForm")ICountryDto country,
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

            countryService.saveOrUpdateCountry(country);
            status.setComplete();

        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IOrganizationDto.class, organizationPropertyEditor);
    }


    // Spring IoC
    private CountryService countryService = null;
    private PropertyEditor organizationPropertyEditor;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setOrganizationPropertyEditor(PropertyEditor organizationPropertyEditor) {
        this.organizationPropertyEditor = organizationPropertyEditor;
    }
}