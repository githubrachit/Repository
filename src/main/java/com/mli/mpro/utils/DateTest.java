package com.mli.mpro.utils;

import com.mli.mpro.productRestriction.util.AppConstants;

public class DateTest {
    public static void main(String[] args) {
        String date = Utility.dateFormatter("26-10-2021", AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN, AppConstants.DD_MM_YYYY_HH_MM_SS_HYPHEN_A);
        System.out.println("Date:"+date);
    }
}
