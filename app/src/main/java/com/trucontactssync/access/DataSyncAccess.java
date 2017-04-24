/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trucontactssync.database.AbstractDb;
import com.trucontactssync.model.DataSync;

import java.util.ArrayList;
import java.util.List;

public class DataSyncAccess extends AbstractDb {

    //Table name
    public static final String TABLE_NAME = "datasync";

    //Column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TABLE_NAME = "tablename";
    public static final String COLUMN_USERCHECK = "usercheck";
    public static final String COLUMN_LAST_SYNCED = "lastsynced";
    public static final String COLUMN_DISPLAY_NAME = "displayname";
    public static final String COLUMN_COLUMN_LIST = "columnlist";
    public static final String COLUMN_KEY_NAMES = "keynames";
    public static final String COLUMN_SPL_FUNCTION = "splfunction";

    private DataSync data = null;

    public DataSyncAccess(SQLiteDatabase database) {
        super(database);
    }

    @Override
    protected String getPrimaryKey() {
        return COLUMN_ID;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{COLUMN_ID,
                COLUMN_TABLE_NAME,
                COLUMN_USERCHECK,
                COLUMN_LAST_SYNCED,
                COLUMN_DISPLAY_NAME,
                COLUMN_COLUMN_LIST,
                COLUMN_KEY_NAMES,
                COLUMN_SPL_FUNCTION};
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Object loadColumns(Cursor c) {
        List<DataSync> dataList = new ArrayList<DataSync>();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                data = new DataSync();
                data.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                data.setTablename(c.getString(c.getColumnIndex(COLUMN_TABLE_NAME)));
                data.setUsercheck(c.getInt(c.getColumnIndex(COLUMN_USERCHECK)));
                data.setLastsynced(c.getLong(c.getColumnIndex(COLUMN_LAST_SYNCED)));
                data.setDisplayname(c.getString(c.getColumnIndex(COLUMN_DISPLAY_NAME)));
                data.setColumnlist(c.getString(c.getColumnIndex(COLUMN_COLUMN_LIST)));
                data.setKeynames(c.getString(c.getColumnIndex(COLUMN_KEY_NAMES)));
                data.setSplfunction(c.getString(c.getColumnIndex(COLUMN_SPL_FUNCTION)));
                dataList.add(data);
            } while (c.moveToNext());
            c.close();
        }
        return dataList;
    }

    @Override
    protected ContentValues buildColumns(Object obj) {
        data = (DataSync) obj;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, data.getId());
        contentValues.put(COLUMN_TABLE_NAME, data.getTablename());
        contentValues.put(COLUMN_USERCHECK, data.getUsercheck());
        contentValues.put(COLUMN_LAST_SYNCED, data.getLastsynced());
        contentValues.put(COLUMN_DISPLAY_NAME, data.getDisplayname());
        contentValues.put(COLUMN_COLUMN_LIST, data.getColumnlist());
        contentValues.put(COLUMN_KEY_NAMES, data.getKeynames());
        contentValues.put(COLUMN_SPL_FUNCTION, data.getSplfunction());
        return contentValues;
    }
}
