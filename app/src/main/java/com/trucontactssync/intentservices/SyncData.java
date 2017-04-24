/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.intentservices;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.trucontactssync.access.DataSyncAccess;
import com.trucontactssync.access.country.CountryAccess;
import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.listeners.OnDataSyncListener;
import com.trucontactssync.listeners.OnDataSyncProgressListener;
import com.trucontactssync.listeners.OnNextDataSync;
import com.trucontactssync.managers.DatabaseManager;
import com.trucontactssync.managers.GetCountryManager;
import com.trucontactssync.model.DataSync;

import java.util.ArrayList;

public class SyncData implements OnNextDataSync {

    private final Context mContext;

    private ArrayList<DataSync> mDataSyncArrayList = new ArrayList<DataSync>();

    private int mCurrentIndex = 0;

    private OnDataSyncListener onDataSyncListener = null;

    private OnDataSyncProgressListener onSyncProgressListener = null;

    /**
     * Constructor
     *
     * @param context
     */
    public SyncData(Context context) {
        mContext = context;
        AppLog.logString("*** Offline Sync Started ***");
        //commented due to unnecessary calling each time, called where ever creating new instance
        //onNextDataSync(Constants.SYNC_COUNTRY);
    }

    /**
     * Sync data either in the UI or Background
     * @param context
     *      application context
     *  @param onSyncProgressListener
     *      show percentage value
     *  @param onDataSyncListener
     *      callback sync completed
     */
    public SyncData(Context context, OnDataSyncProgressListener onSyncProgressListener, OnDataSyncListener onDataSyncListener) {
        mContext = context;
        AppLog.logString("*** Offline Sync Started ***");
        this.onSyncProgressListener = onSyncProgressListener;
        this.onDataSyncListener = onDataSyncListener;
    }

    /**
     * Sync data either in the UI or Background
     * @param context
     *      application context
     *  @param onDataSyncListener
     *      callback sync completed
     */
    public SyncData(Context context, OnDataSyncListener onDataSyncListener) {
        mContext = context;
        AppLog.logString("*** Offline Sync Started ***");
        this.onDataSyncListener = onDataSyncListener;
    }

    /* Sync Country Data*/
    private void syncCountryManagerData(DataSync datasync) {
        GetCountryManager getCountryManager = new GetCountryManager(mContext, datasync);
        getCountryManager.setNextDataSyncListener(this);
        getCountryManager.callCountryService();
    }

    @Override
    public void onNextDataSync(int dataToBeSynced) {

        switch (dataToBeSynced) {
            case Constants.SYNC_COUNTRY:
                //syncCountryManagerData();
                break;
//            case Constants.SYNC_STATE:
//                //syncStateManagerData();
//                break;
//            case Constants.SYNC_ADDRESS_TYPE:
//                //syncAddressTypeManagerData();
//                break;
//            case Constants.SYNC_VCARD_HEADER:
//                //syncVCardHeaderManagerData();
//                break;
//            case Constants.SYNC_VCARD_URL:
//                //syncVCardUrlManagerData();
//                break;
//            case Constants.SYNC_VCARD_SOCIAL_PROFILE:
//                //syncVCardSocialManagerData();
//                break;
//            case Constants.SYNC_VCARD_PHONE:
//                //syncVCardPhoneManagerData();
//                break;
//            case Constants.SYNC_VCARD_EMAIL:
//                //syncVCardMailManagerData();
//                break;
//            case Constants.SYNC_VCARD_ADDRESS:
//                //syncVCardAddressManagerData();
//                break;
//            case Constants.SYNC_INVITE_SENT:
//                //syncInviteSentManagerData();
//                break;
//            case Constants.SYNC_INVITE_RECEIVED:
//                //syncInviteReceivedManagerData();
//                break;
//            case Constants.SYNC_USER_ALERT_CHECK:
//                //syncUserAlertCheckManagerData();
//                break;
//            case Constants.SYNC_VCARD_CHANGE_LOG:
//                //syncVCardChangeLog();
//                break;
//            case Constants.SYNC_VCARD_IMAGE:
//                //syncVCardImage();
//                break;
//            case Constants.SYNC_INVITE_WITHDRAWN:
//                //syncInviteWithDrawn();
//                break;
//            case Constants.SYNC_FEEDBACK:
//                //syncFeedBack();
//                break;
//            case Constants.COPY_LOCAL_CONTACTS:
//                copyLocalContacts();
//                break;
////            case Constants.SYNC_LOCAL_CONTACTS:
////                syncLocalContactsDownload();
////                break;
////            case Constants.UPLOAD_LOCAL_CONTACTS:
////                syncLocalContactsUpload();
////                break;
//            case Constants.COMPLETE_SYNC:
//                //redirect();
//                break;
            default:
                break;
        }
    }

    @Override
    public void onNextSync() {
        this.mCurrentIndex += 1;
        syncTable();
    }

    public void syncTable() {
        if(mCurrentIndex < mDataSyncArrayList.size()) {
            DataSync dataSync = mDataSyncArrayList.get(mCurrentIndex);
            onTableSyncStarted(dataSync);
            switch (dataSync.getTablename()) {
                case CountryAccess.TABLE_NAME:
                    syncCountryManagerData(dataSync);
                    break;
                default:
                    onNextSync();
                    break;
            }
        } else {
            AppLog.logString("Offline data sync completed");
            if(this.onDataSyncListener != null) {
                onDataSyncListener.onSyncCompletedCallback();
            }
        }
    }

    @Override
    public void onUploadPercentageCompleted(int current_record, int total_record, DataSync dataSync) {
        if(this.onSyncProgressListener != null) {
            onSyncProgressListener.onUploadPercentageCompleted(current_record, total_record, dataSync);
        }
    }

    @Override
    public void onDownloadPercentageCompleted(int current_record, int total_record, DataSync dataSync) {
        if(this.onSyncProgressListener != null) {
            onSyncProgressListener.onDownloadPercentageCompleted(current_record, total_record, dataSync);
        }
    }

    private void onTableSyncStarted(DataSync dataSync) {
        if(this.onSyncProgressListener != null) {
            onSyncProgressListener.onTableSyncStartedCallback(dataSync);
        }
    }

    /**
     * No:of tables to sync
     * @param tables
     */
    public void execute(String[] tables) {
        if(null != tables) {
            ArrayList<DataSync> dataList = getDataSyncList();
            for(String table : tables) {
                for(DataSync dataSync : dataList) {
                    if(table.equals(dataSync.getTablename())) {
                        mDataSyncArrayList.add(dataSync);
                        break;
                    }
                }
            }
            syncTable();
        }
    }

    /**
     * Execute/sync all tables
     */
    public void execute() {
        mDataSyncArrayList = getDataSyncList();
        syncTable();
    }

    private ArrayList<DataSync> getDataSyncList() {
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        DataSyncAccess dataSyncAccess = new DataSyncAccess(database);
        ArrayList<DataSync> dataList = null;
        try {
            dataList = (ArrayList<DataSync>) dataSyncAccess.selectAll(null);
        } catch (ArrayIndexOutOfBoundsException e) {
            AppLog.logString(e.getMessage());
        }
        return dataList;
    }
}