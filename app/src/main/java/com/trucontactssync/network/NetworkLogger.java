/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.network;

import android.util.Log;

import com.trucontactssync.common.Constants;

/**
 * Created by DuraiTamil on 10/2/2015.
 */
public class NetworkLogger {

    /**
     * Method to display the debug logs
     *
     * @param message
     */
    public static void debugLog(String message) {
        if (Constants.IS_LOG_ENABLED) {
            Log.d(NetworkConstants.APPLICATION_TAG, message);
        }
//		writeToFile(message);
    }

    /**
     * Method to display the error logs
     *
     * @param message
     */
    public static void errorLog(String message) {
        if (Constants.IS_LOG_ENABLED) {
            Log.e(NetworkConstants.APPLICATION_TAG, message);
        }
    }

    /**
     * Method to display the info logs
     *
     * @param message
     */
    public static void infoLog(String message) {

        if (Constants.IS_LOG_ENABLED) {
            Log.i(NetworkConstants.APPLICATION_TAG, message);
        }
    }

    /**
     * Method to display the verbose logs
     *
     * @param message
     */
    public static void verboseLog(String message) {
        Log.v(NetworkConstants.APPLICATION_TAG, message);
    }

}
