package com.trucontactssync.datasync;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.datasync.presenter.DataSyncPresenter;
import com.trucontactssync.model.DataSync;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncManagerImpl implements DataSyncManager {

    List<DataSync> dataSyncs;
    DataSyncPresenter dataSyncPresenter;

    public DataSyncManagerImpl(List<DataSync> dataSyncs, DataSyncPresenter dataSyncPresenter) {
        this.dataSyncs = dataSyncs;
        this.dataSyncPresenter = dataSyncPresenter;
        DataSync dataSync = dataSyncs.get(0);
        if(dataSync.getUsercheck() == 0) {
            AppLog.logString("Processing table :: "+dataSync.getDisplayname());
            //do only pull
            pullDataSync(dataSync);
        } else {
            //pushDataSync(dataSync);
        }
    }

    private void pushDataSync(DataSync dataSync) {
        DataSyncPushAsyncTask dataSyncPushAsyncTask = new DataSyncPushAsyncTask(this);
        dataSyncPushAsyncTask.execute(dataSync);
    }

    private void pullDataSync(DataSync dataSync) {
        DataSyncPullAsyncTask dataSyncPullAsyncTask = new DataSyncPullAsyncTask(this);
        dataSyncPullAsyncTask.execute(dataSync);
    }

    @Override
    public void publishPushProgress(DataSync dataSync, String recordNumber, int percentage) {

    }

    @Override
    public void publishPullProgress(DataSync dataSync, String recordNumber, int percentage) {
        dataSyncPresenter.onPullUpdateProgress(dataSync, recordNumber, percentage);
    }
}
