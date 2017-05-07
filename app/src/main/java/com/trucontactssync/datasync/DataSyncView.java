package com.trucontactssync.datasync;

import com.trucontactssync.model.DataSync;

import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public interface DataSyncView {

    /**
     * Update UI view to display data sync tables
     * @param dataSyncs
     *      dataSyncs tables
     */
    void updateUI(List<DataSync> dataSyncs);

    /**
     * Update dataSync UI push column
     * @param dataSync
     *      pushed dataSync table
     * @param count
     *      no:of record pushed
     * @param percentage
     *      no:of percentage completed
     */
    void updatePushColumn(DataSync dataSync, String count, int percentage);

    /**
     * Update dataSync UI pull column
     * @param dataSync
     *      pulled dataSync table
     * @param count
     *      no:of record pulled
     * @param percentage
     *      no:of percentage pull completed
     */
    void updatePullColumn(DataSync dataSync, String count, int percentage);
}
