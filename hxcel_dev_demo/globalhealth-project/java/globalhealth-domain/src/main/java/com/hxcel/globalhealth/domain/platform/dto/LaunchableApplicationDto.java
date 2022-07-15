package com.hxcel.globalhealth.domain.platform.dto;

import com.hxcel.globalhealth.domain.platform.model.Application;
import com.hxcel.globalhealth.domain.platform.model.OrganizationLicense;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfiguration;
import com.hxcel.globalhealth.domain.platform.model.ApplicationConfigurationOverride;
import com.hxcel.globalhealth.utils.string.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Bjorn Harvold
 * Date: Oct 30, 2008
 * Time: 11:45:46 AM
 * Description: We use it to wrap an application or organizationlicense entity and expose their values
 */
public class LaunchableApplicationDto {
    private final static Logger log = LoggerFactory.getLogger(LaunchableApplicationDto.class);

    private Application application;
    private OrganizationLicense organizationLicense;
    private List<ApplicationConfiguration> configurations;
    private List<ApplicationConfigurationOverride> overrides;

    /**
     * Use this for when the application is owned by the organization
     *
     * @param app
     * @param configurations
     */
    public LaunchableApplicationDto(Application app, List<ApplicationConfiguration> configurations) {
        this.application = app;
        this.configurations = configurations;
    }

    /**
     * Use this when the application is licensed to an organization
     *
     * @param license
     * @param configurations
     * @param overrides
     */
    public LaunchableApplicationDto(OrganizationLicense license, List<ApplicationConfiguration> configurations, List<ApplicationConfigurationOverride> overrides) {
        this.organizationLicense = license;
        this.configurations = configurations;
        this.overrides = overrides;
    }

    public Application getApplication() {
        if (application != null) {
            return application;
        } else if (organizationLicense != null) {
            return organizationLicense.getLicense().getApplication();
        }

        log.error("No application entity is present! Cannot continue.");

        return null;
    }

    public List<KeyValuePair> getConfigurations() {
        List<KeyValuePair> result = null;

        if (application != null && configurations != null) {
            result = new ArrayList<KeyValuePair>();

            for (ApplicationConfiguration configuration : configurations) {
                KeyValuePair kv = new KeyValuePair(configuration.getKey(), configuration.getValue());

                result.add(kv);
            }
        } else if (organizationLicense != null && (configurations != null || overrides != null)) {
            result = new ArrayList<KeyValuePair>();

            for (ApplicationConfiguration configuration : configurations) {
                KeyValuePair kv = new KeyValuePair(configuration.getKey(), configuration.getValue());

                // here we override default values
                for (ApplicationConfigurationOverride override : overrides) {
                    if (StringUtils.equals(configuration.getId(), override.getOriginal().getId()) && configuration.getExportable()) {
                        kv.setValue(override.getValue());
                    }
                }
                result.add(kv);
            }
        }

        return result;
    }

    public String getConfigurationForJSON() {
        List<KeyValuePair> list = getConfigurations();
        StringBuilder sb = new StringBuilder();

        for (KeyValuePair pair : list) {
            sb.append(pair.getKey());
            sb.append(": \"");
            sb.append(pair.getValue());
            sb.append("\", ");
        }

        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length() - 1);
        }

        return sb.toString();
    }
}
