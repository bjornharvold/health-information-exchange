package com.hxcel.globalhealth.platform.controller.country;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.controller.country.validator.RegulationOverrideValidator;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationOverrideDto;
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
import java.util.List;

/**
 * <p>Title: CountryRegulationOverrideFormController</p>
 * <p>Description: Handles working with a a regulation override</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/country/regulation/override/edit.admin")
@SessionAttributes("regulationOverrideForm")
public class RegulationOverrideFormController {
    private static final Logger log = LoggerFactory.getLogger(RegulationOverrideFormController.class);
    private final static String FORM = "regulation.override.edit";

    /* cannot for OSGi
    @Autowired
    public RegulationOverrideFormController(CountryService countryService) {
        this.countryService = countryService;
    }
    */

    @ModelAttribute(value = "regulations")
    protected List<IRegulationDto> populateRegulations() throws Exception {
        List<IRegulationDto> result = null;

        result = countryService.searchForRegulations(null, null, null);

        return result;
    }

    @ModelAttribute(value = "country")
    protected ICountryDto populateCountry(@RequestParam(value = "countryId", required = true) String countryId) throws Exception {
        ICountryDto result = null;

        result = countryService.getCountry(countryId);

        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IRegulationOverrideDto regulationOverride = null;

        if (StringUtils.isBlank(id)) {
            regulationOverride = countryService.createRegulationOverride();
        } else {
            regulationOverride = countryService.getRegulationOverride(id);
        }

        model.addAttribute("regulationOverrideForm", regulationOverride);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@RequestParam(value = "countryId", required = true) String countryId,
                                   @ModelAttribute(value = "regulationOverrideForm") IRegulationOverrideDto regulationOverride,
                                   BindingResult result, SessionStatus status) throws Exception {
        String view = null;

        try {
            new RegulationOverrideValidator().validate(regulationOverride, result);

            if (result.hasErrors()) {
                view = FORM;
            } else {

                regulationOverride = countryService.saveOrUpdateRegulationOverride(regulationOverride);
                status.setComplete();
                view = "redirect:/secure/country/regulation/search.admin?id=" + countryId + "&view=country.regulation.overrides.data";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return view;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IRegulationDto.class, regulationPropertyEditor);
        binder.registerCustomEditor(ICountryDto.class, countryPropertyEditor);
    }

    // Spring IoC
    private CountryService countryService = null;
    private PropertyEditor regulationPropertyEditor;
    private PropertyEditor countryPropertyEditor;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setRegulationPropertyEditor(PropertyEditor regulationPropertyEditor) {
        this.regulationPropertyEditor = regulationPropertyEditor;
    }

    public void setCountryPropertyEditor(PropertyEditor countryPropertyEditor) {
        this.countryPropertyEditor = countryPropertyEditor;
    }
}