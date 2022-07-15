/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.utils.string;

import java.util.List;
import java.util.ArrayList;

public class KeywordStemmer {

    public KeywordStemmer() {
        //log.debug("Instantiated KeywordStemmer()");
    }

    public void stemKeywords(String[] keywords) {
        PorterStemmer ps = new PorterStemmer();
        if (keywords != null || keywords.length > 0) {
            // replace old keywords with stemmed ones
            for (int i = 0; i < keywords.length; i++) {
                // before stemming check to see if its a number
                // if it is a number then don't stem because ew
                // can't.
                // BIG CAUTION: if file picture id naming convention
                // changes from all numbers to something else
                // this will need to be changed!!!!
                try {
                    Long.valueOf(keywords[i]);
                }
                catch (NumberFormatException nfe) {
                    // this means it's not a long go ahead and stem it
                    keywords[i] = ps.stem(keywords[i]);
                }

            }
        }
    }

    public void stemKeywords(List<String> keywords) {
        if (keywords != null) {
            String[] tempArray = new String[keywords.size()];
            tempArray = keywords.toArray(tempArray);
            this.stemKeywords(tempArray);

            for (String keyword : tempArray) {
                if (!keywords.contains(keyword)) {
                    keywords.add(keyword);
                }
            }
        }
    }

    public static void main(String args[]) {
//    String[] keywords = new String[7];
//    keywords[0] = "help";
//    keywords[1]="bicycle";
//    keywords[2]="running";
//    keywords[3]="runner";
//    keywords[4]="sailboat";
//    keywords[5]="sails";
//    keywords[6]="helping";
        List<String> v = new ArrayList<String>();
        v.add("matte");
        v.add("ball");
        v.add("urter");
        v.add("motorsag");
        v.add("running");
        v.add("climbers");
        v.add("running");
        v.add("climbs");
        v.add("climbed");
        v.add("running");
        KeywordStemmer ks = new KeywordStemmer();

        ks.stemKeywords(v);

        for (String keyword : v) {
            System.out.println("Keyword + = " + keyword);
        }
    }
}
