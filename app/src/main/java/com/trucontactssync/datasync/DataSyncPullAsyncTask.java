package com.trucontactssync.datasync;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.datasync.utils.DataSyncUtils;
import com.trucontactssync.http.RestPost;
import com.trucontactssync.managers.DatabaseManager;
import com.trucontactssync.model.DataSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.trucontactssync.datasync.DataSyncManager.API_ACTION_NAME;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncPullAsyncTask extends AsyncTask<Object, Integer, Void> {

    DataSyncManager dataSyncManager;
    private int prevrecs;
    private int paginationRecord = 50;
    private int totalRecords;
    DataSync dataSync = null;
    float currentRow;
    SQLiteDatabase db;

    public DataSyncPullAsyncTask(DataSyncManager dataSyncManager) {
        this.dataSyncManager = dataSyncManager;
        db = DatabaseManager.getInstance().openDatabase();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        this.dataSyncManager.publishPullProgress(dataSync, String.valueOf(values[0]), values[1]);
    }

    @Override
    protected Void doInBackground(Object... objects) {
        dataSync = (DataSync) objects[0];
        try {
            pullData();
        } catch (Exception exception) {
            AppLog.logString("Caught with exception ::"+exception.getMessage());
        }
        return null;
    }

    private void pullData() throws IOException, JSONException {
        dataSync.setPrevrecs(prevrecs);
        dataSync.setPaginationrecs(paginationRecord);
        dataSync.setOnebyonepush(false);
        AppLog.logString("Pagination record started from : "+prevrecs+" to "+paginationRecord+" And Total Record to process: "+totalRecords);
        RestPost restPost = new RestPost(API_ACTION_NAME
                , DataSyncUtils.constructPayload(dataSync));
        parseResult(restPost.restPost(DataSyncUtils.constructPayload(dataSync)));
    }

    private void parseResult(String result) throws IOException, JSONException {
        // this is the one going to handle pulled/fetched results to perform operation
        // either insert or update the records
        if(result != null) {
            JSONObject response = new JSONObject(result);
            float percentage = 0f;
            if(response.has(Constants.NO_OF_RECS)) {
                this.totalRecords = Integer.parseInt(response.getString(Constants.NO_OF_RECS));
            }
            if(response.has(Constants.KEY_RESULT)) {
                result = response.getString(Constants.KEY_RESULT);
                JSONArray jsonArray = new JSONArray(result);

                AppLog.logString("Total "+dataSync.getDisplayname()+" Length: "+jsonArray.length());
                if(jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        AppLog.logString("response jsonArray Loop :" + jsonArray.get(i));
                        AppLog.logString("i value:"+i);
                        currentRow += 1;
                        percentage = currentRow * 50 / this.totalRecords;
                        insertOrUpdate((JSONObject)jsonArray.get(i));
                        AppLog.logString("current row:"+(int) currentRow);
                        AppLog.logString("percentage:"+percentage);
                        publishProgress((int)currentRow, (int) percentage);
                    }
                }
                //assume that we fetched all the records in a single http request for non-transaction
                //table e.g., country, state and address_type etc. so there is no reason to fetch again
                if((totalRecords > prevrecs) && (dataSync.getUsercheck() != 0)) {
                    prevrecs = (paginationRecord + prevrecs);
                    pullData();
                }
            }
        }
    }

    public int insertOrUpdate(JSONObject object) {
        Cursor cursor = null;
        try {
            String objectId = object.getString("objectId");

            //put row data value which is not match both default and keyname
            List<String> excludeRowDatacolumns = new ArrayList<>();
            excludeRowDatacolumns.add("objectId");
            excludeRowDatacolumns.add("updatedAt");
            excludeRowDatacolumns.add("isdeleted");

            //insert or Update content values
            ContentValues contentValues = new ContentValues();
            contentValues.put("updatedAt", object.getString("updatedAt"));
            JSONObject jsonObject = new JSONObject();
            if(dataSync.getKeynames() != null) {
                String[] keyNames = dataSync.getKeynames().split(",");
                for(String key : keyNames) {
                    jsonObject.put(key, object.getString(key));
                    excludeRowDatacolumns.add(key);
                }
            }

            //set row data
            JSONObject rowData = new JSONObject();
            Iterator<String> keys = object.keys();
            boolean hasExcludedColumn = false;
            while(keys.hasNext()){
                hasExcludedColumn = false;
                String key = keys.next();
                for(String column : excludeRowDatacolumns) {
                    if(column.equals(key)) {
                        hasExcludedColumn = true;
                        break;
                    }
                }
                if(!hasExcludedColumn) {
                    rowData.put(key, object.getString(key));
                }
            }
            jsonObject.put("rowdata", rowData);

            String sql = "select * from "+dataSync.getTablename()+" where objectId ='"+objectId+"'";
            cursor = db.rawQuery(sql,null);
            if(cursor != null && cursor.getCount() > 0){
                String[] whereArgs = {objectId};
                db.update(dataSync.getTablename(), contentValues, "objectId = ? ", whereArgs);
                AppLog.logString("Updating "+dataSync.getTablename()+" "+contentValues.toString());
            }
            else {
                contentValues.put("objectId", objectId);
                db.insert(dataSync.getTablename(), null, contentValues);
                AppLog.logString("Inserted "+dataSync.getTablename()+" "+contentValues.toString());
            }
        } catch (JSONException | SQLiteException e) {
            AppLog.logString("Caught with exception ::"+e.getMessage());
        } finally {
            if(cursor !=null)
                cursor.close();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        this.dataSyncManager.onDataSyncPullCompleted();
    }

}
