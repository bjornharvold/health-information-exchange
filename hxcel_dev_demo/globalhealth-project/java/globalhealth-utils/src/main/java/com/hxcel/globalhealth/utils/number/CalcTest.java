/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */
package com.hxcel.globalhealth.utils.number;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Title: </p>
 * <p>Description: </p>


 *
 * @author Bjorn Harvold

 */

public class CalcTest {
    public CalcTest() {
    }

    public static void main(String[] args) {
        CalcTest calcTest1 = new CalcTest();
        calcTest1.calc();
//    calcTest1.dateTest();
    }

    public void calc() {
        BigDecimal di = new BigDecimal("9999999999999999999");
        System.out.println("2505600*1000=" + di.longValue());
        double t = 1.0 / 2.0;
        System.out.println("t: " + t);

        Double d = new Double("50");
        System.out.println("d: " + d.toString());

        System.out.println("now: " + new Date().getTime());

        SimpleDateFormat sdf = new SimpleDateFormat();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        StringBuffer result = new StringBuffer();

        result.append("'");
        result.append(df.format(new Date()));
        result.append("'");

        System.out.println(result.toString());

        System.out.println("-1 * -3 = " + (-1 * -3));
    }

    public void dateTest() {
        Calendar now = GregorianCalendar.getInstance();

        while (true) {
            System.out.println("now: " + now.getTime().toString());


        }
    }
}
