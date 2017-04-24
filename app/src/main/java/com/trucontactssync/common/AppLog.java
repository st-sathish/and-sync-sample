/*
 * Copyright (c) Ravikiran Srinivasan
 */

/**
 * Application log class
 */
package com.trucontactssync.common;


import android.util.Log;

public class AppLog {
    // Application Tag
    private static final String APP_TAG = "TruContacts";

    // Log a String
    public static void logString(String message) {
        // If log is enabled, the print the log
        if (Constants.IS_LOG_ENABLED) {
            Log.i(APP_TAG, " " + message);
        }

    }
}