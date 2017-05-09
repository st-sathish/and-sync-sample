package com.trucontactssync.datasync;

/**
 * Created by CS39 on 5/7/2017.
 */

import com.trucontactssync.model.DataSync;

/**
  * Datasync Manager to manage both async task pull & push
 */
public interface DataSyncManager {

    void publishPushProgress(DataSync dataSync, String recordNumber, int percentage);

    /**
     * public pull progress to the presenter
     * @param recordNumber
     *      current record count
     * @param percentage
     *      percentage done
     */
    void publishPullProgress(DataSync dataSync, String recordNumber, int percentage);

    /**
     * Remote Server method name to trigger
     */
    String API_ACTION_NAME = "getsyncdata";

    /**
     * Callback function after all the data pushed from local to remote
     */
    void onDataSyncPushCompleted();

    /**
     * DataSync pull callback
     */
    void onDataSyncPullCompleted();
}
