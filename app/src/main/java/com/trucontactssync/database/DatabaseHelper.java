/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static String DB_NAME = "trucontacts.sqlite";
    private static String DB_PATH = "/data/data/com.trucontactssync/databases/";
//    private static String DB_PATH = Environment.getExternalStorageDirectory()+"/Movies/";
    // Database Version
    private static final int DATABASE_VERSION = Constants.DB_VERSION;

    private SQLiteDatabase myDataBase;
    private Context myContext;
    public String errorMsg;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        createDataBase(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    public void createDataBase(Context context) {
        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;
        if (!dbExist) {
            db_Read = this.getReadableDatabase();
            db_Read.close();
            copyDataBase();
        }
    }

    public boolean checkDataBase() {
        File dbFile = myContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    void copyDataBase() {
        try {
            String outFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;

            InputStream myInput = myContext.getAssets().open("databases/" +DB_NAME);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();

            myOutput.flush();
            myOutput.close();
        } catch (IOException ioe) {
            AppLog.logString("Exception " + ioe);
        }
    }

    void createNewDataBase() {
        try {
            String MY_DATABASE_NAME = DB_NAME; // DB_PATH +
            @SuppressWarnings("unused")
            SQLiteDatabase myDB = null;
            myDB = this.myContext.openOrCreateDatabase(MY_DATABASE_NAME, 0,
                    null);
            openDataBase();
            executeUpdate("CREATE TABLE user(id INT(11), name varchar(50))");
        } catch (Exception e) {
            // TODO
        }
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public Cursor executeQuery(String sql) throws SQLException {
        Cursor c = null;
        try {
            this.errorMsg = "";
            c = myDataBase.rawQuery(sql, null);
        } catch (Exception e) {
            this.errorMsg = "" + e.toString();
        }
        return c;
    }

    public boolean executeUpdate(String sql) throws SQLException {
        boolean qryExecuted = false;
        try {
            this.errorMsg = "";
            myDataBase.execSQL(sql);
            qryExecuted = true;
        } catch (Exception e) {
            this.errorMsg = "" + e.toString();
            qryExecuted = false;
        }
        return qryExecuted;
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }
}

