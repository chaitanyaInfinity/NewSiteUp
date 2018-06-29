package com.example.infinitylabs.dynamictrackerapp.utilities;

/**
 * Created by nileshjarad on 28/12/16.
 */

public class StringUtilities {
    public static boolean isEmpty(String strToCheck) {
        if (strToCheck == null || strToCheck.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String strToCheck) {
        if (strToCheck == null || strToCheck.isEmpty()) {
            return false;
        }
        return true;
    }
}

