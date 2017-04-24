/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trucontactssync.access.country.FixedColumnNames;
import com.trucontactssync.common.AppLog;
import com.trucontactssync.database.AbstractDb;
import com.trucontactssync.model.VCard;

import java.util.ArrayList;
import java.util.List;

public class VCardAccess extends AbstractDb implements FixedColumnNames {

    //Table name
    public static final String TABLE_NAME = "vcardheader";

    //Column names
    //public static final String COLUMN_OBJECT_ID = "objectId";
    //public static final String COLUMN_UPDATED_AT = "updatedAt";
    //public static final String COLUMN_ROW_DATA = "rowdata";
    //public static final String COLUMN_PARSE_OBJ_ID = "parseobjid";
    public static final String COLUMN_USER_ID = "userid";
    //public static final String COLUMN_ISDELETED = "isdeleted";
    public static final String IMAGE_DATA = "imagedata";
    public static final String AS_ROWDATA_RECEIVED = "rowdatareceived";
    public static final String COLUMN_CARD_TYPE = "cardtype";
    public static final String TMP_FIELD_NO_OF_CARD = "noOfCard";

    private VCard data = null;

    public VCardAccess(SQLiteDatabase database) {
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
                COLUMN_ROW_DATA,
                //COLUMN_PARSE_OBJ_ID,
                COLUMN_USER_ID,
                COLUMN_CARD_TYPE,
                COLUMN_IS_DELETED};
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Object loadColumns(Cursor c) {
        List<VCard> dataList = new ArrayList<VCard>();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                data = new VCard();
                AppLog.logString("Column Index:"+c.getColumnIndex(COLUMN_OBJECT_ID));
                data.setObjectid(c.getString(c.getColumnIndex(COLUMN_OBJECT_ID)));
                if(c.getColumnIndex(COLUMN_UPDATED_AT)!=-1){
                    data.setUpdatedAt(c.getLong(c.getColumnIndex(COLUMN_UPDATED_AT)));
                }
                data.setRowdata(c.getString(c.getColumnIndex(COLUMN_ROW_DATA)));
//                if(c.getColumnIndex(COLUMN_PARSE_OBJ_ID)!=-1){
//                    data.setParseobjid(c.getString(c.getColumnIndex(COLUMN_PARSE_OBJ_ID)));
//                }
                if(c.getColumnIndex(COLUMN_USER_ID)!=-1){
                    data.setUserid(c.getString(c.getColumnIndex(COLUMN_USER_ID)));
                }
                if(c.getColumnIndex(COLUMN_IS_DELETED)!=-1){
                    data.setIsdeleted(c.getInt(c.getColumnIndex(COLUMN_IS_DELETED)));
                }
                if(c.getColumnIndex(IMAGE_DATA)!=-1){
                    data.setImagedata(c.getBlob(c.getColumnIndex(IMAGE_DATA)));
                }
                if(c.getColumnIndex(AS_ROWDATA_RECEIVED)!=-1){
                    data.setAsrowdatareceived(c.getString(c.getColumnIndex(AS_ROWDATA_RECEIVED)));
                }
                if(c.getColumnIndex(COLUMN_CARD_TYPE)!=-1){
                    data.setCardtype(c.getString(c.getColumnIndex(COLUMN_CARD_TYPE)));
                }
                //custom runtime column which is not present in the VCardHeader table
                //to show up/down arrow for contact list
                if(c.getColumnIndex(TMP_FIELD_NO_OF_CARD) != -1) {
                    data.setNoOfVcard(Integer.parseInt(c.getString(c.getColumnIndex(TMP_FIELD_NO_OF_CARD))));
                }
                dataList.add(data);
            } while (c.moveToNext());
            c.close();
        }
        return dataList;
    }

    @Override
    protected ContentValues buildColumns(Object obj) {
        data = (VCard) obj;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_OBJECT_ID, data.getObjectid());
        contentValues.put(COLUMN_UPDATED_AT, data.getUpdatedAt());
        contentValues.put(COLUMN_ROW_DATA, data.getRowdata());
        //contentValues.put(COLUMN_PARSE_OBJ_ID, data.getParseobjid());
        contentValues.put(COLUMN_USER_ID, data.getUserid());
        contentValues.put(COLUMN_IS_DELETED, data.getIsdeleted());
        contentValues.put(COLUMN_CARD_TYPE, data.getCardtype());
        return contentValues;
    }
}
