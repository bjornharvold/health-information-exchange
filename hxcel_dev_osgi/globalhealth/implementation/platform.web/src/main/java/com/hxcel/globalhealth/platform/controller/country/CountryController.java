package com.hxcel.globalhealth.platform.controller.country;

import com.hxcel.globalhealth.platform.spec.dto.ICountryDto;
import com.hxcel.globalhealth.platform.spec.CountryService;
import com.hxcel.globalhealth.platform.spec.dto.IRegulationOverrideDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: bjorn
 * Date: Nov 7, 2008
 * Time: 11:23:32 AM
 */
@Controller
public class CountryController {
    private static final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final static Integer DEFAULT_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULTS = 22;
    private final static Integer DEFAULT_MAX_CHILD_RESULTS = 20;

    /* cannot for OSGi
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    */

    /**
     * Display a list of countries
     *
     * @param name
     * @param view
     * @param index
     * @param maxResults
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/country/search.admin")
    protected String showCountries(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "view", required = false) String view,
                                   @RequestParam(value = "index", required = false) Integer index,
                                   @RequestParam(value = "maxResults", required = false) Integer maxResults,
                                   ModelMap map) throws Exception {
        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_RESULTS;
        }

        List<ICountryDto> list = countryService.searchForCountries(name, index, maxResults);
        Integer count = countryService.searchForCountriesCount(name);
        map.addAttribute("countries", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "countries.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching country tiles view: '" + result + "'");
        }

        return result;
    }

    /**
     * Display a single country
     *
     * @param countryId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/country/view.admin")
    protected String showCountry(@RequestParam(value = "id", required = true) String countryId,
                                 @RequestParam(value = "view", required = false) String view,
                                 ModelMap map) throws Exception {
        ICountryDto country = countryService.getCountry(countryId);

        map.addAttribute("country", country);

        String result = StringUtils.isBlank(view) ? "country.content" : view;

        if (log.isTraceEnabled()) {
            log.trace("Dispatching country tiles view: '" + result + "'");
        }

        return result;
    }

    /**
     * Deletes a regulation override
     *
     * @param regulationOverrideId
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/country/regulation/override/delete.admin")
    protected String deleteRegulationOverride(
            @RequestParam(value = "countryId", required = true) String countryId,
            @RequestParam(value = "id", required = true) String regulationOverrideId,
            @RequestParam(value = "view", required = false) String view,
            ModelMap map) throws Exception {
        countryService.deleteRegulationOverride(regulationOverrideId);

        String result = StringUtils.isBlank(view) ? "country.regulation.overrides.data" : view;

        return showRegulationOverridesByCountry(countryId, null, result, null, null, map);
    }

    /**
     * Display country regulation overrides
     *
     * @param name
     * @param view
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/secure/country/regulation/search.admin")
    protected String showRegulationOverridesByCountry(
            @RequestParam(value = "id", required = false) String countryId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "view", required = false) String view,
            @RequestParam(value = "index", required = false) Integer index,
            @RequestParam(value = "maxResults", required = false) Integer maxResults,
            ModelMap map) throws Exception {

        if (index == null) {
            index = DEFAULT_INDEX;
        }
        if (maxResults == null) {
            maxResults = DEFAULT_MAX_CHILD_RESULTS;
        }

        List<IRegulationOverrideDto> list = countryService.searchForRegulationOverridesByCountry(countryId, name, index, maxResults);
        Integer count = countryService.searchForRegulationOverridesByCountryCount(countryId, name);
        map.addAttribute("overrides", list);
        map.addAttribute("name", name);
        map.addAttribute("maxResults", maxResults);
        map.addAttribute("index", index);
        map.addAttribute("count", count);

        String result = StringUtils.isBlank(view) ? "country.regulation.overrides.content" : view;

        return showCountry(countryId, result, map);
    }

    // Spring IoC
    private CountryService countryService = null;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
}
