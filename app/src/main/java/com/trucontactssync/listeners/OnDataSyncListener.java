package com.trucontactssync.listeners;


/**
 * on Sync listener to listen all tables status
 */
public interface OnDataSyncListener {

    /**
     * Callback when all tables synced
     */
    void onSyncCompletedCallback();
}
