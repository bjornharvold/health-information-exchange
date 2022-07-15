/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.utils.string;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Description:
 *
 * @author bjorn
 */
public class StringParser {
    private final static Logger log = LoggerFactory.getLogger(StringParser.class);

    /**
     * StringParser ctor object
     */
    public StringParser() {
    }

    /**
     * Method description:
     *
     * @param keywordString Description of Parameter
     * @return Description of the Returned Value
     */
    public static String[] parseThisString(String keywordString) {
        return parseThisString(keywordString, " ");
    }


    /**
     * Method description:
     *
     * @param keywordString Description of Parameter
     * @return Description of the Returned Value
     */
    public static List parseThisStringToList(String keywordString) {
        StringTokenizer st = new StringTokenizer(keywordString, " ,-:;=+");
        List<String> result = new ArrayList<String>();

        while (st.hasMoreElements()) {
            result.add(st.nextToken().trim());
        }

        return result;
    }


    /**
     * Method description:
     *
     * @param stringToParse Description of Parameter
     * @param delimiter     Description of Parameter
     * @return Description of the Returned Value
     */
    public static String[] parseThisString(String stringToParse, String delimiter) {
        StringTokenizer st = new StringTokenizer(stringToParse, delimiter);
        String[] result = null;

        if (st != null) {
            result = new String[st.countTokens()];

            int i = 0;

            while (st.hasMoreElements()) {
                result[i] = st.nextToken().trim();
                i++;
            }
        }

        return result;
    }

    /**
     * Method description:
     *
     * @param stringToParse Description of Parameter
     * @param delimiter     Description of Parameter
     * @return Description of the Returned Value
     * @throws NumberFormatException
     */
    public static Integer[] parseThisStringToIntegerArray(String stringToParse, String delimiter) throws NumberFormatException {
        StringTokenizer st = new StringTokenizer(stringToParse, delimiter);
        Integer[] result = null;

        if (st != null) {
            result = new Integer[st.countTokens()];

            int i = 0;

            while (st.hasMoreElements()) {
                result[i] = new Integer(st.nextToken().trim());
                i++;
            }
        }

        return result;
    }


    /**
     * Method description: Parses a String array into one string with a chosen delimiter
     *
     * @param pricingOptions Description of Parameter
     * @param delimiter      Description of Parameter
     * @return Description of the Returned Value
     */
    public static String parseThisArray(String[] pricingOptions, String delimiter) {
        StringBuffer result = null;
        String theResult = null;
        if (pricingOptions != null) {
            result = new StringBuffer();
            for (int i = 0; i < pricingOptions.length; i++) {
                result.append(pricingOptions[i]);
                result.append(delimiter);
            }
            // remove the last comma
            result.deleteCharAt(result.length() - 1);
            theResult = result.toString();
        }

        return theResult;
    }

    public static String slashToDot(String slashString) {
        String result = slashString.replace('/', '.');
        return result;
    }

    /**
     * The incoming path looks like this: /home/bjorn/test.pdf The result will look like this
     *
     * @param pathAndFilename String
     * @return String
     */
    public static String getFileNameOfPath(String pathAndFilename) {
        String result = null;
//    com.Ostermiller.util.StringTokenizer st = new com.Ostermiller.util.StringTokenizer(pathAndFilename, "/");

        // linux uses forward slash - windows uses backslash - must unify
        pathAndFilename = pathAndFilename.replaceAll("\\\\", "/");

        result = pathAndFilename.substring(pathAndFilename.lastIndexOf("/") + 1, pathAndFilename.length());
        // remove spaces
        result = result.replace(' ', '_');

        return result;
    }

    /**
     * This will return the extension for a file path e.g. /opt/tomcat/images/image.jpg ==> jpg e.g. image.gif ==> gif
     *
     * @param pathAndFilename String
     * @return String
     */
    public static String getFileNameExtension(String pathAndFilename) {
        String result = null;

        if (pathAndFilename != null && pathAndFilename.length() > 0) {
            result = pathAndFilename.substring(pathAndFilename.lastIndexOf(".") + 1);
        }

        return result;
    }

    public static Object returnNullTest() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(java.util.UUID.randomUUID().toString());
    }

    /**
     * Converts string into its binary representation
     *
     * @param s String
     * @return String
     */
    public static String toBinaryString(String s) {
        byte[] a = null;
        StringBuffer sb = null;
        BitSet bs = null;

        if (s != null && s.length() > 0) {
            a = s.getBytes();
            sb = new StringBuffer();
            bs = StringParser.fromByteArray(a);

            for (int i = 0; i < bs.length(); i++) {
                if (bs.get(i)) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
        }

        return sb.toString();
    }

    // Returns a bitset containing the values in bytes.
    // The byte-ordering of bytes must be big-endian which means the most significant bit is in element 0.
    private static BitSet fromByteArray(byte[] bytes) {
        BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }

    // Returns a byte array of at least length 1.
    // The most significant bit in the result is guaranteed not to be a 1
    // (since BitSet does not support sign extension).
    // The byte-ordering of the result is big-endian which means the most significant bit is in element 0.
    // The bit at index 0 of the bit set is assumed to be the least significant bit.
    private static byte[] toByteArray(BitSet bits) {
        byte[] bytes = new byte[bits.length() / 8 + 1];
        for (int i = 0; i < bits.length(); i++) {
            if (bits.get(i)) {
                bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
            }
        }
        return bytes;
    }


    /**
     * This assumes all values in array are integers
     *
     * @param value String[]
     * @return Integer[]
     */
    public static Integer[] convertToIntegerArray(String[] value) {
        Integer[] result = null;

        if (value != null && value.length > 0) {
            result = new Integer[value.length];
            for (int i = 0; i < value.length; i++) {
                result[i] = new Integer(value[i]);
            }
        }

        return result;
    }

    /**
     * Converts an Integer array to a strung array
     *
     * @param value String[]
     * @return Integer[]
     */
    public static String[] convertToStringArray(Integer[] value) {
        String[] result = null;

        if (value != null && value.length > 0) {
            result = new String[value.length];
            for (int i = 0; i < value.length; i++) {
                result[i] = new String(value[i].toString());
            }
        }

        return result;
    }


    /**
     * We are expecting a property file with key value pairs e.g. key=value. We want to make an object graph of the data
     *
     * @param resourceBundle
     * @return
     */
    public List<KeyValuePair> parseInputStreamToKeyValuePairs(InputStream resourceBundle) {
        List<KeyValuePair> result = null;

        try {
            if (resourceBundle != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceBundle));
                result = new ArrayList<KeyValuePair>();
                String line = null;

                while ((line = br.readLine()) != null) {
                    // ignore comment lines
                    if (line.trim().length() > 2) {
                        if (!line.startsWith("#")) {
                            String key = line.substring(0, line.indexOf("="));
                            String value = line.substring(line.indexOf("=") + 1);

                            result.add(new KeyValuePair(key, value));
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                resourceBundle.close();
            } catch (IOException e) {
                log.error("Could not close input stream!", e);
            }
        }

        return result;
    }
}
