/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.managers;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.trucontactssync.app.AppController;
import com.trucontactssync.common.AppLog;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseManager {

    //database manager singleten instance.
    private static DatabaseManager sInstance;
    //sqlite helper object
    private static SQLiteOpenHelper sDatabaseHelper;
    //integer to check whether db is currently opened or clossed.
    private final AtomicInteger mOpenCounter = new AtomicInteger();
    //sqlite database object
    private SQLiteDatabase mDatabase;
    private final String mPassword = AppController.getInstance().getAndroidId();

    /*As private constructor is used so can not create object of this class directly.*/
    private DatabaseManager() {
    }

    /**
     * initialize manager instance.
     *
     * @param helper -SQLiteOpenHelper object
     */
    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (sInstance == null) {
            sInstance = new DatabaseManager();
            sDatabaseHelper = helper;
        }
    }

    /**
     * singleton instance for DatabaseManager class
     *
     * @return
     */
    public static synchronized DatabaseManager getInstance() throws IllegalStateException {
        if (sInstance == null) {
            AppLog.logString(DatabaseManager.class.getSimpleName()
                    + " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }


    /**
     * open db by checking teh atomic variable.
     *
     * @return
     */
    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            // mDatabase = sDatabaseHelper.getWritableDatabase(mPassword);
            mDatabase = sDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * close db by checking teh atomic variable.
     *
     * @return
     */
    public synchronized void closeDatabaseOnAppExit() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }
}
