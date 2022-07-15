package com.hxcel.globalhealth.platform.controller.regulation;

import com.hxcel.globalhealth.platform.controller.regulation.validator.RegulationValidator;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationDto;
import com.hxcel.globalhealth.platform.spec.model.enums.RegulationTypeCd;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * <p>Title: RegulationFormController</p>
 * <p>Description: Handles working with a single regulation</p>
 *
 * @author Bjorn Harvold
 */
@Controller
@RequestMapping(value = "/secure/regulation/edit.admin")
@SessionAttributes("regulationForm")
public class RegulationFormController {
    private static final Logger log = LoggerFactory.getLogger(RegulationFormController.class);
    private String FORM = "regulation.edit";

    /* cannot for OSGi
    @Autowired
    public RegulationFormController(CountryService countryService) {
        this.countryService = countryService;
    }
    */

    @ModelAttribute(value = "types")
    protected RegulationTypeCd[] populateRegulationType() {
        return RegulationTypeCd.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String setupForm(@RequestParam(value = "id", required = false) String id, ModelMap model) throws Exception {
        IRegulationDto regulation = null;

        if (StringUtils.isBlank(id)) {
            regulation = countryService.createRegulation();
        } else {
            regulation = countryService.getRegulation(id);
        }

        model.addAttribute("regulationForm", regulation);

        return FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute(value = "regulationForm") IRegulationDto regulation,
                                   BindingResult result, SessionStatus status) throws Exception {
        new RegulationValidator().validate(regulation, result);
        String view = null;

        if (result.hasErrors()) {
            view = FORM;
        } else {

            view = "redirect:/secure/regulation/search.admin?view=regulations.data";

            countryService.saveOrUpdateRegulation(regulation);
            status.setComplete();

        }

        return view;
    }

    // Spring IoC
    private CountryService countryService = null;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
}