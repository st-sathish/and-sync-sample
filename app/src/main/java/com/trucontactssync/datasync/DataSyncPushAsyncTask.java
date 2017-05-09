package com.trucontactssync.datasync;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.datasync.utils.DataSyncUtils;
import com.trucontactssync.http.RestPost;
import com.trucontactssync.managers.DatabaseManager;
import com.trucontactssync.model.DataSync;
import com.trucontactssync.utils.MathUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncPushAsyncTask extends AsyncTask<Object, Integer, Void> {

    DataSyncManager dataSyncManager;
    SQLiteDatabase db;
    DataSync dataSync = null;
    int totalRecordToPush = 0;
    int offset = 1;
    int pushLimit = 50;
    int prevrecs = 0;

    public DataSyncPushAsyncTask(DataSyncManager dataSyncManager) {
        this.dataSyncManager = dataSyncManager;
        db = DatabaseManager.getInstance().openDatabase();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        this.dataSyncManager.publishPullProgress(dataSync, String.valueOf(values[0]), values[1]);
    }

    @Override
    protected Void doInBackground(Object... objects) {
        this.dataSync = (DataSync) objects[0];
        try {
            //do push and pull
            totalRecordToPush = getTotalRecordPriorPush();
            if(totalRecordToPush > 0) {
                pushMetadata(totalRecordToPush);
            } else {
                publishProgress(0, 50);
            }
        } catch (Exception e) {
            AppLog.logString("Caught with Exception ::" +e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray cursorRequestBody(int pushLimit, int offset) throws JSONException{
        Cursor cursor = null;
        JSONArray requestArray = null;
        try {
            cursor = getCursor("*", pushLimit, offset);
            if(null != cursor && cursor.getCount() > 0) {
                requestArray = new JSONArray();
                String[] columnNames = cursor.getColumnNames();
                while (cursor.moveToNext()) {
                    JSONObject requestJSONObject = new JSONObject();
                    for (int j = 0; j < columnNames.length; j++) {
                        String columnName = cursor.getColumnName(j);
                        String columnValue = cursor.getString(cursor.getColumnIndex(columnName));
                        requestJSONObject.put(columnName, columnValue);
                        requestArray.put(requestJSONObject);
                    }
                }
            }
        } catch (JSONException e) {
            throw e;
        }
        finally {
            if(null != cursor) {
                cursor.close();
            }
        }
        return requestArray;
    }

    public void pushMetadata(int totalRecordToPush) {
        try {
            if(totalRecordToPush <= pushLimit) {
                dataSync.setPushdataarray(cursorRequestBody(pushLimit, offset));
                dataSync.setPrevrecs(prevrecs);
                dataSync.setPaginationrecs(pushLimit);
                dataSync.setOnebyonepush(false);
                RestPost restPost = new RestPost(DataSyncManager.API_ACTION_NAME, DataSyncUtils.constructPayload(dataSync));
                parseResult(restPost.restPost(DataSyncUtils.constructPayload(dataSync)));
            } else {
                int paginationCount = MathUtils.getTotalPagination(totalRecordToPush, pushLimit);
                for(int i = 0;i < paginationCount;i++) {
                    dataSync.setPushdataarray(cursorRequestBody(pushLimit, (i * offset)));
                    dataSync.setPrevrecs(prevrecs);
                    dataSync.setPaginationrecs(pushLimit);
                    //assume this is the last push
                    boolean isLastPush = false;
                    if(i == (paginationCount - 1)) {
                        isLastPush = true;
                    }
                    dataSync.setOnebyonepush(isLastPush);
                    RestPost restPost = new RestPost(DataSyncManager.API_ACTION_NAME, DataSyncUtils.constructPayload(dataSync));
                    parseResult(restPost.restPost(DataSyncUtils.constructPayload(dataSync)));
                    offset = pushLimit;
                    pushLimit += pushLimit;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLog.logString("Caught with exception :: "+e.getMessage());
        }
    }

    public int getTotalRecordPriorPush() {
        Cursor cursor = null;
        int totalRecordPush = 0;
        try {
            cursor = getCursor("count(*)");
            while(cursor.moveToNext()) {
                totalRecordPush = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLog.logString("Error during get count to push record "+e.getMessage());
        } finally {
            if(null != cursor) {
                cursor.close();
            }
        }
        return totalRecordPush;
    }

    private void parseResult(String result) throws IOException, JSONException {
        // this is the one going to handle pulled/fetched results to perform operation
        // either insert or update the records
        if(result != null) {
            JSONObject response = new JSONObject(result);
            float percentage = 0f;
            //assuming that this is pushed result. so there is no need to handle the result.
            AppLog.logString("Pushed records to server: "+pushLimit);
            AppLog.logString("Total record to push: "+totalRecordToPush);
            //calculate percentage only if there is an record
            //if(totalRecordToPush > 0) {
                percentage = pushLimit * 50 / totalRecordToPush;
                publishProgress(pushLimit, (int)percentage);
            //} else {
                //publishProgress(pushLimit, 50);
            //}
        }
    }

    public Cursor getCursor(String selectQuery, int limit, int offset) {
        String sql = "select "+selectQuery+" from "+dataSync.getTablename()+" where updatedAt >= ? OR userid = ?";
        if(limit != 0 & offset != 0) {
            sql = sql+" LIMIT "+limit+" OFFSET "+offset;
        }
        return executeQuery(sql);
    }

    public Cursor getCursor(String selectQuery) {
        return executeQuery("select "+selectQuery+" from "+dataSync.getTablename()+" where updatedAt >= ? OR userid = ?");
    }

    private Cursor executeQuery(String sqlQuery) {
        Cursor cursor;
        String[] args = new String[]{String.valueOf(dataSync.getLastsynced()), ""};
        try {
            cursor = db.rawQuery(sqlQuery, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return cursor;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        this.dataSyncManager.onDataSyncPushCompleted();
    }
}
