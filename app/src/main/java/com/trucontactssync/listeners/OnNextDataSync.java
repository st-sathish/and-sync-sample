/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.listeners;


import com.trucontactssync.model.DataSync;

public interface OnNextDataSync {
    void onNextDataSync(int dataToBeSynched);
    void onNextSync();

    /**
     * Callback on upload percentage progress
     * @param current_record
     *      current record
     * @param dataSync
     *      datasync object
     */
    void onUploadPercentageCompleted(int current_record, int total_record, DataSync dataSync);

    /**
     * Callback on download percentage completed
     * @param current_record
     *      current record
     * @param dataSync
     *      datasync
     */
    void onDownloadPercentageCompleted(int current_record, int total_record, DataSync dataSync);
}
