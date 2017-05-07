package com.trucontactssync.datasync;

import android.os.AsyncTask;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncPushAsyncTask extends AsyncTask<Object, Integer, Void> {

    DataSyncManager dataSyncManager;

    public DataSyncPushAsyncTask(DataSyncManager dataSyncManager) {
        this.dataSyncManager = dataSyncManager;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected Void doInBackground(Object... objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
