package com.trucontactssync.listeners;


import com.trucontactssync.model.DataSync;

public interface OnDataSyncProgressListener {

    void onUploadPercentageCompleted(int current_record, int total_record, DataSync dataSync);

    void onDownloadPercentageCompleted(int current_record, int total_record, DataSync dataSync);

    void onTableSyncStartedCallback(DataSync dataSync);
}
