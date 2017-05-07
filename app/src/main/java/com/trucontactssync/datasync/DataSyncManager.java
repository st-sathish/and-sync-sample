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
}
