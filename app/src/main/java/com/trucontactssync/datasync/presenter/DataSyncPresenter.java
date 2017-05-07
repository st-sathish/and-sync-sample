package com.trucontactssync.datasync.presenter;

import com.trucontactssync.model.DataSync;

import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public interface DataSyncPresenter {

    /**
     * Interactor between AsyncTask and UI layer
     * @param dataSyncList
     *      dataSyncList tables
     */
    void onDataSyncTables(List<DataSync> dataSyncList);

    /**
     * Clear/Destroy the view
     */
    void onDestroy();

    /**
     * inform view to update pull column
     * @param dataSync
     *      current table
     * @param recordNumber
     *      current record number
     * @param percentage
     *      no:of percentage done;
     */
    void onPullUpdateProgress(DataSync dataSync, String recordNumber, int percentage);

    /**
     * inform view to update push column
     * @param dataSync
     *      current table
     * @param recordNumber
     *      current record number
     * @param percentage
     *      no:of percentage done;
     */
    void onPushUpdateProgress(DataSync dataSync, String recordNumber, int percentage);
}
