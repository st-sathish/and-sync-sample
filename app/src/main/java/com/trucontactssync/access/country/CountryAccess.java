/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.access.country;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.trucontactssync.database.AbstractDb;
import com.trucontactssync.model.Country;

import java.util.ArrayList;
import java.util.List;


public class CountryAccess extends AbstractDb implements FixedColumnNames {

    //Table name
    public static final String TABLE_NAME = "countries";

    //Column names
    //public static final String COLUMN_OBJECT_ID = "objectId";
    //public static final String COLUMN_UPDATED_AT = "updatedAt";
    //public static final String COLUMN_ROW_DATA = "rowdata";
    //public static final String COLUMN_PARSE_OBJ_ID = "parseobjid";
    //public static final String COLUMN_COUNTRY_NAME = "countryname";

    private Country data = null;

    public CountryAccess(SQLiteDatabase database) {
        super(database);
    }

    @Override
    protected String getPrimaryKey() {
        return COLUMN_OBJECT_ID;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{COLUMN_OBJECT_ID,
                COLUMN_UPDATED_AT,
                COLUMN_IS_DELETED,
                COLUMN_ROW_DATA};
        //,COLUMN_PARSE_OBJ_ID,COLUMN_COUNTRY_NAME};
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Object loadColumns(Cursor c) {
        List<Country> dataList = new ArrayList<Country>();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                data = new Country();
                data.setObjectid(c.getString(c.getColumnIndex(COLUMN_OBJECT_ID)));
                data.setUpdatedAt(c.getString(c.getColumnIndex(COLUMN_UPDATED_AT)));
                data.setRowdata(c.getString(c.getColumnIndex(COLUMN_ROW_DATA)));
                data.setDeleted(c.getInt(c.getColumnIndex(COLUMN_IS_DELETED)));
                //data.setParseobjid(c.getString(c.getColumnIndex(COLUMN_PARSE_OBJ_ID)));
                //data.setCountryname(c.getString(c.getColumnIndex(COLUMN_COUNTRY_NAME)));
                dataList.add(data);
            } while (c.moveToNext());
            c.close();
        }
        return dataList;
    }

    @Override
    protected ContentValues buildColumns(Object obj) {
        data = (Country) obj;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OBJECT_ID, data.getObjectid());
        contentValues.put(COLUMN_UPDATED_AT, data.getUpdatedAt());
        contentValues.put(COLUMN_ROW_DATA, data.getRowdata());
        contentValues.put(COLUMN_IS_DELETED, data.isDeleted());
        //contentValues.put(COLUMN_PARSE_OBJ_ID, data.getParseobjid());
        //contentValues.put(COLUMN_COUNTRY_NAME, data.getCountryname());
        return contentValues;
    }
}
