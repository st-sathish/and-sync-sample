package com.trucontactssync.intentservices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.listeners.OnDataSyncListener;
import com.trucontactssync.listeners.OnDataSyncProgressListener;
import com.trucontactssync.model.DataSync;

/**
 * Created by CS39 on 3/30/2017.
 */

public class SyncIntentService extends IntentService implements OnDataSyncProgressListener, OnDataSyncListener {

    Bundle bundle = null;

    public SyncIntentService() {
        super(SyncIntentService.class.getName());
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        bundle = intent.getExtras();
        //SyncData syncData = new SyncData(getApplicationContext(), this, this);
        //syncData.execute();
        onDownloadPercentageCompleted(1, 1, null);
    }

    public void publishUI(int progressDone) {
        if (bundle != null) {
            Messenger messenger = (Messenger) bundle.get("messenger");
            if(messenger != null) {
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putInt("progress_done", progressDone);
                msg.setData(b); //put the data here
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    AppLog.logString("error error");
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onSyncCompletedCallback() {

    }

    @Override
    public void onUploadPercentageCompleted(int current_record, int total_record, DataSync dataSync) {
        int percentageDone = current_record / total_record;
        AppLog.logString("Upload Progress percentage: "+percentageDone);
        publishUI(percentageDone);
    }

    @Override
    public void onDownloadPercentageCompleted(int current_record, int total_record, DataSync dataSync) {
        int percentageDone = current_record / total_record;
        AppLog.logString("Progress percentage: "+percentageDone);
        for(int i =0;i < 40000;i++) {
            publishUI(i);
            AppLog.logString("arg " + i);
        }
    }

    @Override
    public void onTableSyncStartedCallback(DataSync dataSync) {

    }
}
