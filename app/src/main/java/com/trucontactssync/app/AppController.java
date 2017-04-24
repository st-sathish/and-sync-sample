/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.database.DatabaseHelper;
import com.trucontactssync.managers.DatabaseManager;


public class AppController extends Application {

    //tag for volley request que
    private static final String TAG = AppController.class.getSimpleName();
    private static final String TAG_PREFERENCE = "TruContacts";
    //singleton class variable for shared preference.
    private static SharedPreferences mSharedPreferences;
    //application class variable
    private static AppController mInstance;
    //variable to track application visibility
    private static boolean activityVisible;

    /**
     * Application class instance
     */
    public AppController() {
        mInstance = this;
    }

    /**
     * Get singleton instance.
     *
     * @return
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppLog.logString("Application onCreate");
        /* application instance */
        mInstance = this;

        /* shared preference initialization */
        mSharedPreferences = getApplicationContext().getSharedPreferences(TAG_PREFERENCE, Context.MODE_PRIVATE);

        /* initializing database instances */
        DatabaseManager.initializeInstance(new DatabaseHelper(getApplicationContext()));

        DatabaseManager.getInstance().openDatabase();
    }

    /**
     * get android device id
     *
     * @return
     */
    public String getAndroidId() {
        return Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public boolean isConnectedToNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * get shared preference object
     *
     * @return
     */
    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    /**
     * check whether activity is visible or not. This will be used for tracking application in activity.
     *
     * @return
     */
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    /**
     * method to check whether activity is resumed.
     */
    public static void isActivityResumed() {
        activityVisible = true;
    }

    /**
     * method to check activity is stopped
     */
    public static void isActivityStopped() {
        activityVisible = false;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        DatabaseManager.getInstance().closeDatabaseOnAppExit();
    }


}