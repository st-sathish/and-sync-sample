/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDb {

    protected final SQLiteDatabase appDatabase;

    protected AbstractDb(SQLiteDatabase database) {
        this.appDatabase = database;
    }

    // method to set primary key
    protected abstract String getPrimaryKey();

    // method to set columns
    protected abstract String[] getColumns();

    // method to set table name
    protected abstract String getTableName();

    // method to set values
    protected abstract Object loadColumns(Cursor c);

    // method to build content values
    protected abstract ContentValues buildColumns(Object obj);

    /**
     * method to delete items
     *
     * @param whereCondition
     * @param whereArgs
     * @return
     */
    public int delete(final String whereCondition, final String[] whereArgs) {
        return delete(getTableName(), whereCondition, whereArgs);
    }

    /**
     * method to delete items
     *
     * @param tableName
     * @param whereCondition
     * @param whereArgs
     * @return
     */
    private int delete(final String tableName, final String whereCondition,
                       final String[] whereArgs) {
        return appDatabase.delete(tableName, whereCondition, whereArgs);
    }

    /**
     * method to insert items
     *
     * @param tableName
     * @param cv
     * @return
     */
    private long insert(final String tableName, final ContentValues cv) {
        return this.appDatabase.insert(tableName, null, cv);
    }

    /**
     * method to insert items
     *
     * @param obj
     * @return
     */
    public long insert(final Object obj) {
        if (obj != null) {
            return insert(getTableName(), buildColumns(obj));
        }
        return 0;
    }

    /**
     * method to select items
     *
     * @param tableName
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    private Cursor select(final String tableName, final String[] columns,
                          final String selection, final String[] selectionArgs,
                          final String groupBy, final String having, final String orderBy,
                          final String limit) {
        return appDatabase.query(tableName, columns, selection, selectionArgs,
                groupBy, having, orderBy, limit);
    }

    /**
     * method to select items
     *
     * @param selection
     * @param selectionArray
     * @param orderBy
     * @return
     */
    public Object select(final String selection, final String[] selectionArray,
                         final String orderBy) {
        final Cursor c = select(getTableName(), getColumns(), selection,
                selectionArray, null, null, orderBy, null);

        return loadColumns(c);
    }

    public Object select(final String selection, final String order) {

        final Cursor c = select(getTableName(), getColumns(), selection, null,
                null, null, order, null);
        return loadColumns(c);
    }

    public Object select(final String selection, final String[] selectionArray,
                         final String orderBy, String limit) {
        final Cursor c = select(getTableName(), getColumns(), selection,
                selectionArray, null, null, orderBy, limit);

        return loadColumns(c);
    }

    public Object selectAll(final String orderBy) {
        final Cursor c = select(getTableName(), getColumns(), null, null, null,
                null, orderBy, null);

        return loadColumns(c);
    }

    private int update(final String tableName, final ContentValues cv,
                       final String whereCondition, final String[] whereArgs) {
        return appDatabase.update(tableName, cv, whereCondition, whereArgs);
    }

    public void update(final Object obj, final String whereCondition,
                       final String[] whereArgs) {
        update(getTableName(), buildColumns(obj), whereCondition, whereArgs);
    }

    public Cursor select(String[] column, String selection, String orderBy) {

        return select(true, getTableName(), column, selection,
                null, null, null, orderBy, null);
    }

    public Object executeQuery(String query) {
        final Cursor c =  appDatabase.rawQuery(query, null);
        return loadColumns(c);
    }

    public void executeDelete(String query) {
        appDatabase.execSQL(query);
    }

    /**
     * method to return cursor after binding data
     *
     * @param distinct
     * @param tableName
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    private Cursor select(final boolean distinct, final String tableName, final String[] columns,
                          final String selection, final String[] selectionArgs,
                          final String groupBy, final String having, final String orderBy,
                          final String limit) {
        return appDatabase.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

    }

}
