package com.trucontactssync.datasync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trucontactssync.R;
import com.trucontactssync.datasync.presenter.DataSyncPresenter;
import com.trucontactssync.datasync.presenter.DataSyncPresenterImpl;
import com.trucontactssync.model.DataSync;

import java.util.List;

import static com.trucontactssync.R.id.syncDbParentLayout;

public class DataSyncActivity extends AppCompatActivity implements DataSyncView {

    ProgressBar mProgressBar = null;
    LinearLayout mSyncDbParentLayout = null;
    DataSyncPresenter mDataSyncPresenter = null;
    int getProgressBar ,value;
    int progress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_data_sync);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSyncDbParentLayout = (LinearLayout) findViewById(syncDbParentLayout);
        mDataSyncPresenter = new DataSyncPresenterImpl(this);
    }

    @Override
    public void updateUI(List<DataSync> dataSyncs) {
        View syncDbListItemView;
        TextView tvSyncDbName;
        LayoutInflater layoutInflater = getLayoutInflater();
        for(int i= 0; i < dataSyncs.size();i++){
            syncDbListItemView = layoutInflater.inflate(R.layout.sync_db_list_item, mSyncDbParentLayout, false);
            syncDbListItemView.findViewById(R.id.sync_parent_layout).setTag(dataSyncs.get(i).getId());
            tvSyncDbName = (TextView)syncDbListItemView.findViewById(R.id.tvSyncDbName);
            tvSyncDbName.setText(dataSyncs.get(i).getDisplayname());
            mSyncDbParentLayout.addView(syncDbListItemView);
        }
    }

    @Override
    public void updatePushColumn(DataSync dataSync, String recordCount, int percentage) {
        updatePushPullColumn(dataSync, recordCount, percentage, R.id.syncDbPush);
    }

    @Override
    public void updatePullColumn(DataSync dataSync, String recordCount, int percentage) {
        updatePushPullColumn(dataSync, recordCount, percentage, R.id.syncDbPull);
    }

    private void updatePushPullColumn(DataSync dataSync,String recordCount, int percentage, int pullOrPushId) {
        View view = currentDataSyncTable(dataSync.getId());
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.loading);
        TextView textView = (TextView)view.findViewById(pullOrPushId);
        if(recordCount .equals("0")){
            progressBar.setProgress(progress);
            textView.setText(recordCount);
        }
        else{
            getProgressBar = progressBar.getProgress();
            value = getProgressBar + percentage;
            progressBar.setProgress(value);
            textView.setText(recordCount);
        }
    }

    public View currentDataSyncTable(Integer id) {
        View view = null;
        for(int i =0; i < mSyncDbParentLayout.getChildCount(); i++) {
            view = mSyncDbParentLayout.getChildAt(i);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.sync_parent_layout);
            if(linearLayout.getTag() != null && linearLayout.getTag().equals(id)) {
                break;
            }
        }
        return view;
    }

    @Override
    protected void onDestroy() {
        mDataSyncPresenter.onDestroy();
        super.onDestroy();
    }
}
