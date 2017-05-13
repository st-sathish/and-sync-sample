package com.trucontactssync.datasync;

import com.trucontactssync.datasync.presenter.DataSyncPresenter;
import com.trucontactssync.model.DataSync;

import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncManagerImpl implements DataSyncManager {

    List<DataSync> dataSyncs;
    DataSyncPresenter dataSyncPresenter;
    DataSync dataSync;
    private int dataSyncIndex = 0;
    private DataSyncPushAsyncTask dataSyncPushAsyncTask = null;
    private DataSyncPullAsyncTask dataSyncPullAsyncTask = null;

    public DataSyncManagerImpl(List<DataSync> dataSyncs, DataSyncPresenter dataSyncPresenter) {
        this.dataSyncs = dataSyncs;
        this.dataSyncPresenter = dataSyncPresenter;
        dataSync = dataSyncs.get(dataSyncIndex);
        goNext();
    }

    private void pushDataSync(DataSync dataSync) {
        //if(dataSyncPushAsyncTask == null)
            dataSyncPushAsyncTask = new DataSyncPushAsyncTask(this);
        dataSyncPushAsyncTask.execute(dataSync);
    }

    private void pullDataSync(DataSync dataSync) {
        //if(dataSyncPullAsyncTask == null)
            dataSyncPullAsyncTask = new DataSyncPullAsyncTask(this);
        dataSyncPullAsyncTask.execute(dataSync);
    }

    /**
     * Go DataSync one by one
     */
    private void goNext() {
//        if(dataSync.getUsercheck() == 0) {
//            AppLog.logString("Processing table :: "+dataSync.getDisplayname());
//            //dataSyncPresenter.onPushUpdateProgress(dataSync, "0", 50);
//            //do only pull
//            pullDataSync(dataSync);
//        } else {
            pushDataSync(dataSync);
//        }
    }

    @Override
    public void onDataSyncPushCompleted() {
        pullDataSync(dataSync);
    }

    @Override
    public void onDataSyncPullCompleted() {
        dataSyncIndex += 1;
        if(dataSyncIndex < dataSyncs.size()) {
            this.dataSync = dataSyncs.get(dataSyncIndex);
            //goNext();
        } else {
            //callback function to inform presenter that all the push & pull has been completed
        }
    }

    @Override
    public void publishPushProgress(DataSync dataSync, String recordNumber, int percentage) {
        dataSyncPresenter.onPushUpdateProgress(dataSync, recordNumber, percentage);
    }

    @Override
    public void publishPullProgress(DataSync dataSync, String recordNumber, int percentage) {
        dataSyncPresenter.onPullUpdateProgress(dataSync, recordNumber, percentage);
    }
}
