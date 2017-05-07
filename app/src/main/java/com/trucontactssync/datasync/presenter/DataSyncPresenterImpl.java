package com.trucontactssync.datasync.presenter;

import com.trucontactssync.datasync.DataSyncManager;
import com.trucontactssync.datasync.DataSyncManagerImpl;
import com.trucontactssync.datasync.DataSyncView;
import com.trucontactssync.datasync.interactor.DataSyncAsyncTaskInteractor;
import com.trucontactssync.model.DataSync;

import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncPresenterImpl implements DataSyncPresenter {

    DataSyncView dataSyncView;

    public DataSyncPresenterImpl(DataSyncView dataSyncView) {
        this.dataSyncView = dataSyncView;
        DataSyncAsyncTaskInteractor dataSyncAsyncTaskInteractor = new DataSyncAsyncTaskInteractor(this);
        dataSyncAsyncTaskInteractor.execute();
    }

    @Override
    public void onPullUpdateProgress(DataSync dataSync, String recordNumber, int percentage) {
        if(dataSyncView != null) {
            dataSyncView.updatePullColumn(dataSync, recordNumber, percentage);
        }
    }

    @Override
    public void onPushUpdateProgress(DataSync dataSync, String recordNumber, int percentage) {
        if(dataSyncView != null) {
            dataSyncView.updatePushColumn(dataSync, recordNumber, percentage);
        }
    }

    @Override
    public void onDataSyncTables(List<DataSync> dataSyncList) {
        if(dataSyncView != null) {
            //callback to view
            dataSyncView.updateUI(dataSyncList);
        }
        //start dataSync Manager
        DataSyncManager dataSyncManager = new DataSyncManagerImpl(dataSyncList, this);
    }

    @Override
    public void onDestroy() {
        this.dataSyncView = null;
    }
}
