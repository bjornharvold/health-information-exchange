package com.hxcel.globalhealth.utils.locale;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

/**
 * User: Bjorn Harvold
 * Date: Jan 8, 2007
 * Time: 8:49:13 PM
 */
public class LocaleUtils {
    /**
     * Returns a locale based on a country and a language
     *
     * @param language
     * @param country
     * @return
     */
    public static Locale getLocale(String language, String country) {
        Locale result = null;

        if (language != null && language.length() == 2 && country != null && country.length() == 2) {
            result = new Locale(language, country);
        }
        if (language != null && language.length() == 2) {
            result = new Locale(language);
        }

        return result;
    }

    /**
     * Expecting file name to look like this: global_en_US.properties, where
     * the required piece is en_US.
     *
     * @param filename
     * @return
     */
    public static Locale getLocaleByFilename(String filename) {
        Locale result = null;

        if (StringUtils.isNotBlank(filename)) {
            // let's find the last underscore first - that way we'll
            // be pretty flexible about everything else
            int lastUnderscore = StringUtils.lastIndexOf(filename, '_');

            // ok now we know where the language is and where the country code is
            String language = StringUtils.substring(filename, lastUnderscore-2, lastUnderscore);
            String country = StringUtils.substring(filename, lastUnderscore+1, lastUnderscore+3);

            result = new Locale(language, country);
        }

        return result;
    }

    public static void main(String[] agrs) {
        Locale l = LocaleUtils.getLocaleByFilename("global_en_US.properties");

        System.out.println("Locale: " + l);
    }
}
