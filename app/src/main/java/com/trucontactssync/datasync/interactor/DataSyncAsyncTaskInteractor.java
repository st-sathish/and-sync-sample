package com.trucontactssync.datasync.interactor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.trucontactssync.datasync.presenter.DataSyncPresenter;
import com.trucontactssync.managers.DatabaseManager;
import com.trucontactssync.model.DataSync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncAsyncTaskInteractor extends AsyncTask<String, Void, List<DataSync>> {

    DataSyncPresenter dataSyncPresenter;
    public DataSyncAsyncTaskInteractor(DataSyncPresenter dataSyncPresenter) {
        this.dataSyncPresenter = dataSyncPresenter;
    }

    @Override
    protected List<DataSync> doInBackground(String... strings) {
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = null;
        List<DataSync> dataSyncList = null;
        try {
            cursor = database.rawQuery("select * from datasync", null);
            if (cursor != null) {
                dataSyncList = new ArrayList<DataSync>();
                while (cursor.moveToNext()) {
                    DataSync dataSync = new DataSync();
                    dataSync.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                    dataSync.setTablename(cursor.getString(cursor.getColumnIndex("tablename")));
                    dataSync.setUsercheck(Integer.parseInt(cursor.getString(cursor.getColumnIndex("usercheck"))));
                    dataSync.setLastsynced(Long.parseLong(cursor.getString(cursor.getColumnIndex("lastsynced"))));
                    dataSync.setDisplayname(cursor.getString(cursor.getColumnIndex("displayname")));
                    dataSync.setColumnlist(cursor.getString(cursor.getColumnIndex("columnlist")));
                    dataSync.setKeynames(cursor.getString(cursor.getColumnIndex("keynames")));
                    dataSync.setSplfunction(cursor.getString(cursor.getColumnIndex("splfunction")));

                    //sathish
                    //dataSync.setSessionid("69FCEC30C24A");
                    //mohan
                    dataSync.setSessionid("7E3E8D875BBF");
                    dataSyncList.add(dataSync);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != cursor) {
                cursor.close();
            }
        }
        return dataSyncList;
    }

    @Override
    protected void onPostExecute(List<DataSync> dataSyncs) {
        dataSyncPresenter.onDataSyncTables(dataSyncs);
    }
}
