package com.hxcel.globalhealth.platform.utils;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.util.jar.Attributes;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

/**
 * User: Bjorn Harvold
 * Date: May 9, 2009
 * Time: 11:07:22 PM
 * Responsibility:
 */
public class BundleHeaderParser {
    // private final static Logger log = LoggerFactory.getLogger(BundleHeaderParser.class);

    /**
     * Parses the osgi export package string and makes it into a model
     * e.g. com.hxcel.globalhealth.email;version="1.0";uses="com.x.y",
     *
     * @param exportPackage
     * @return
     */
    public List<ExportImportPackage> parseExportImportPackage(String exportPackage) {
        String newExportPkg = null;
        List<ExportImportPackage> result = new ArrayList<ExportImportPackage>();

        // first we want to change all ", to "\n which will give every export package a new line
        if (StringUtils.isNotBlank(exportPackage)) {
            newExportPkg = exportPackage.replaceAll("\",", "\"\n");


            // now we can start parsing every line into an object model
            BufferedReader br = new BufferedReader(new StringReader(newExportPkg));
            String line = null;

            try {
                while ((line = br.readLine()) != null) {
                    result.add(parseLine(line));
                }
            } catch (IOException e) {
                // log.error(e.getMessage(), e);
            }
        }
        
        return result;
    }

    private ExportImportPackage parseLine(String line) {
        ExportImportPackage result = new ExportImportPackage();

        // now we parse the single line
        // the first token is the class
        // the second and third token is version and uses
        StringTokenizer st = new StringTokenizer(line, ";");

        if (st.hasMoreTokens()) {
            result.setPackage(st.nextToken());
        }

        // the next one is either version of uses
        while (st.hasMoreTokens()) {
            String next = st.nextToken();

            int begVersion = next.indexOf("version=");
            int begUses = next.indexOf("uses:=");
            int end = next.lastIndexOf("\"");

            if (begVersion > -1) {
                next = next.substring(begVersion + 9, end);
                result.setVersion(next);
            } else if (begUses > -1) {
                next = next.substring(begUses + 7, end);
                result.setUses(parseUses(next));
            }
        }

        return result;
    }

    /**
     * This parses the uses string and returns a list of uses
     *
     * @param uses
     * @return
     */
    private List<String> parseUses(String uses) {
        List<String> result = new ArrayList<String>();

        if (StringUtils.isNotBlank(uses)) {
            StringTokenizer st = new StringTokenizer(uses, ",");

            while (st.hasMoreTokens()) {
                result.add(st.nextToken());
            }
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            JarFile jar = new JarFile("/Users/crash/svn/hxcel_dev_osgi/globalhealth/implementation/platform.impl/target/platform.impl-1.0.0-SNAPSHOT.jar");

            Manifest manifest = jar.getManifest();

            Attributes map = manifest.getMainAttributes();

            BundleHeaderParser bhp = new BundleHeaderParser();

            List<ExportImportPackage> list = bhp.parseExportImportPackage(map.getValue("Export-Package"));
            List<ExportImportPackage> list2 = bhp.parseExportImportPackage(map.getValue("Import-Package"));

            System.out.println("test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
