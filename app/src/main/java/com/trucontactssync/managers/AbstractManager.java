package com.trucontactssync.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.common.ErrorResponse;
import com.trucontactssync.common.util.TruContactsUtility;
import com.trucontactssync.listeners.OnNextDataSync;
import com.trucontactssync.model.DataSync;
import com.trucontactssync.network.NetworkConstants;

import org.json.JSONObject;

public abstract class AbstractManager {

    /** Total records to sync */
    protected int totalRecords = 0;

    /** datasync model */
    protected DataSync mDataSync;

    /** app context */
    protected Context mContext;

    /** database */
    protected SQLiteDatabase database;


    /** send this property value to continue pull after reach last iteration of push */
    protected boolean oneByOnePush = false;

    /** total record downloaded or uploaded */
    protected int trProgressCompleted = 0;

    /** total record pushed */
    protected int mTotalRecordPushed = 0;

    /** Remaining record to push */
    private int mRemainingRecordToPush = 0;

    protected String objectId = null;

    protected static final int DEFAULT_UPLOAD_RECORD_TO_SYNC = 50;

    public AbstractManager() {
        database = DatabaseManager.getInstance().openDatabase();
    }

    /** Datasync callback */
    protected OnNextDataSync onNextDataSync;

    /**
     *
     * @param previousRecKey
     *      key previous record
     * @param lastSyncedKey
     *      key last synced
     * @param a_dataSync
     *      datasync object
     * @param paginationRecs
     *      pagination record to load
     * @return
     */
    protected DataSync buildDataSync(String previousRecKey, String lastSyncedKey, DataSync a_dataSync, int paginationRecs) {
        DataSync dataSync = new DataSync();
        dataSync.setPrevrecs(PreferenceManager.getInstance().getInt(previousRecKey, 0));
        //dataSync.setSessionid(TruContactsUtility.getSessionTokenFromSharedPreference(mContext));
        dataSync.setSessionid("6CF7FCCF82B6");
        dataSync.setLastsynced(PreferenceManager.getInstance().getLong(lastSyncedKey, 0));
        dataSync.setId(a_dataSync.getId());
        dataSync.setDisplayname(a_dataSync.getDisplayname());
        dataSync.setTablename(a_dataSync.getTablename());
        dataSync.setUsercheck(a_dataSync.getUsercheck());
        dataSync.setKeynames(a_dataSync.getKeynames());
        dataSync.setColumnlist(a_dataSync.getColumnlist());
        dataSync.setPaginationrecs(paginationRecs);
        dataSync.setOnebyonepush(mDataSync.getOnebyonepush());
        return dataSync;
    }

    /**
     *
     * @param previousRecKey
     *      previous record key
     * @param lastSyncedKey
     *      last synced key
     * @param paginationRecs
     *      pagination recs
     * @return
     */
    protected DataSync buildDataSync(String previousRecKey, String lastSyncedKey, int paginationRecs) {
        DataSync dataSync = new DataSync();
        dataSync.setPrevrecs(PreferenceManager.getInstance().getInt(previousRecKey, 0));
        //dataSync.setSessionid(TruContactsUtility.getSessionTokenFromSharedPreference(mContext));
        dataSync.setSessionid("6CF7FCCF82B6");
        dataSync.setLastsynced(PreferenceManager.getInstance().getLong(lastSyncedKey, 0));
        dataSync.setId(mDataSync.getId());
        dataSync.setDisplayname(mDataSync.getDisplayname());
        dataSync.setTablename(mDataSync.getTablename());
        dataSync.setUsercheck(mDataSync.getUsercheck());
        dataSync.setKeynames(mDataSync.getKeynames());
        dataSync.setColumnlist(mDataSync.getColumnlist());
        dataSync.setPaginationrecs(paginationRecs);
        dataSync.setOnebyonepush(mDataSync.getOnebyonepush());
        return dataSync;
    }

    /**
     * Return DataSync pojo as bundle
     * @return
     */
    public Bundle getDataSyncBundle(DataSync dataSync) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PREV_RECS, dataSync.getPrevrecs());
        bundle.putString(Constants.SESSION_ID, dataSync.getSessionid());
        bundle.putLong(Constants.LAST_SYNCED, dataSync.getLastsynced());
        bundle.putInt(Constants.ID, dataSync.getId());
        bundle.putString(Constants.DISPLAY_NAME, dataSync.getDisplayname());
        bundle.putString(Constants.TABLE_NAME, dataSync.getTablename());
        bundle.putInt(Constants.USER_CHECK, dataSync.getUsercheck());
        bundle.putInt(Constants.PAGINATION_RECS, dataSync.getPaginationrecs());
        bundle.putString(Constants.KEY_NAMES, dataSync.getKeynames());
        bundle.putString(Constants.COLUMN_LIST, dataSync.getColumnlist());
        //assume the request sending total records in a first push
        if(oneByOnePush && (totalRecords <= DEFAULT_UPLOAD_RECORD_TO_SYNC)) {
            oneByOnePush = false;
        }
        bundle.putString(Constants.KEY_ONE_BY_ONE_PUSH, String.valueOf(oneByOnePush));
        return bundle;
    }

    public void setNextDataSyncListener(OnNextDataSync listener) {
        this.onNextDataSync = listener;
    }

    public void callbackHandler(Bundle bundle, String lastSyncedKey, String prevRecKey, int paginationRecordCount) {
        String status = bundle.getString(Constants.STATUS);
        String errorCode = bundle.getString(NetworkConstants.ERROR_CODE);
        String errorMessage = bundle.getString(NetworkConstants.RESPONSE_EXCEPTION);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(errorCode);
        errorResponse.setStatus(status);
        errorResponse.setErrorMessage(errorMessage);

        String response = bundle.getString(NetworkConstants.RESPONSE_DATA);
        AppLog.logString("errorCode = " + errorCode);
        AppLog.logString("errorMessage = " + errorMessage);
        AppLog.logString("status = " + status);
        if (errorCode != null && errorCode.equals(NetworkConstants.RESPONSE_NETWORK_EXCEPTION)) {
            errorCallback(errorResponse);
        } else {
            if (status != null) {
                if (status.equalsIgnoreCase(Constants.STATUS_SUCCESS)) {
                    if(oneByOnePush) {
                        mRemainingRecordToPush = (mRemainingRecordToPush == 0) ? (totalRecords - DEFAULT_UPLOAD_RECORD_TO_SYNC) :
                                (mRemainingRecordToPush - DEFAULT_UPLOAD_RECORD_TO_SYNC);
                        mTotalRecordPushed += (totalRecords - mRemainingRecordToPush);
                        AppLog.logString("Total record pushed so far: "+mTotalRecordPushed);
                        AppLog.logString("Remaining Record to push: "+mRemainingRecordToPush);
                        //push/upload record progress listener
                        //onNextDataSync.onUploadPercentageCompleted(mTotalRecordPushed, totalRecords, null);

                        //assume this is a last push
                        if((totalRecords - mTotalRecordPushed) < DEFAULT_UPLOAD_RECORD_TO_SYNC) {
                            oneByOnePush = false;
                        }
                        repeatService();
                    } else {
                        if(totalRecords <= DEFAULT_UPLOAD_RECORD_TO_SYNC) {
                            //push/upload record progress listener
                            //onNextDataSync.onUploadPercentageCompleted(totalRecords, totalRecords, null);
                        }
                        //handle pull/download db record
                        successCallback(response);
                        PreferenceManager.getInstance().putLong(lastSyncedKey, (Long)(System.currentTimeMillis() / 1000));
                        int prevRec = PreferenceManager.getInstance().getInt(prevRecKey, 0);
                        if(totalRecords > (prevRec + paginationRecordCount)) {
                            PreferenceManager.getInstance().putInt(prevRecKey, (prevRec + paginationRecordCount));
                            repeatService();
                        } else {
                            /*if (onNextDataSync != null) {
                                onNextDataSync.onNextSync();
                            }*/
                        }
                    }

                } else {
                    errorMessage = bundle.getString(Constants.TAG_JSON_ERROR);
                    /*if (onNextDataSync != null) {
                        onNextDataSync.onNextSync();
                    }*/
                }
            } else {

            }
        }
    }

    protected abstract void repeatService();

    /**
     * Handle error
     */
    protected abstract void errorCallback(ErrorResponse errorResponse);

    /**
     * Handle response data
     * @param responseData
     *      response data
     */
    protected abstract void successCallback(String responseData);

    /**
     * Set total records for pagination
     * @param responseJsonObject
     *      filter total records from response
     */
    protected void setPaginationTotalRecords(JSONObject responseJsonObject) {
        try {
            if(responseJsonObject.has(Constants.NO_OF_RECS)) {
                String noOfRecs = responseJsonObject.getString(Constants.NO_OF_RECS);
                this.totalRecords = Integer.parseInt(noOfRecs);
            }
        } catch (Exception e) {
            AppLog.logString("Caught with exception :: "+e.getMessage());
        }
    }
}
