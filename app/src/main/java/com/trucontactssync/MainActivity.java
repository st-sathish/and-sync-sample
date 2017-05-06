package com.trucontactssync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.managers.DatabaseManager;
import com.trucontactssync.model.DataSync;
import com.trucontactssync.network.NetworkConstants;
import com.trucontactssync.network.NetworkLogger;
import com.trucontactssync.utils.MathUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar = null;
    Context mContext = null;
    LinearLayout syncDbParentLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        syncDbParentLayout = (LinearLayout) findViewById(R.id.syncDbParentLayout);

        //startSync();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 3 sec
                GetDataSyncTableAsyncTask getDataSyncTableAsyncTask = new GetDataSyncTableAsyncTask(syncDbParentLayout);
                getDataSyncTableAsyncTask.execute();
            }
        }, 3000);
    }

    private class GetDataSyncTableAsyncTask extends AsyncTask<String, Integer, List<DataSync>> {

        private LinearLayout syncDbParentLayout;

        public GetDataSyncTableAsyncTask(LinearLayout syncDbParentLayout) {
            this.syncDbParentLayout = syncDbParentLayout;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //textView.setText("Counting "+Integer.toString(values[0]));
        }

        @Override
        protected List<DataSync> doInBackground(String... strings) {
            SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
            Cursor cursor = null;
            List<DataSync> dataSyncList = null;
            try {
                cursor = database.rawQuery("select * from datasync", null);
                if (cursor != null) {
                    dataSyncList = new ArrayList<DataSync>();
                    while (cursor.moveToNext()) {
                        DataSync dataSync = new DataSync();
                        dataSync.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                        dataSync.setTablename(cursor.getString(cursor.getColumnIndex("tablename")));
                        dataSync.setUsercheck(Integer.parseInt(cursor.getString(cursor.getColumnIndex("usercheck"))));
                        dataSync.setLastsynced(Long.parseLong(cursor.getString(cursor.getColumnIndex("lastsynced"))));
                        dataSync.setDisplayname(cursor.getString(cursor.getColumnIndex("displayname")));
                        dataSync.setColumnlist(cursor.getString(cursor.getColumnIndex("columnlist")));
                        dataSync.setKeynames(cursor.getString(cursor.getColumnIndex("keynames")));
                        dataSync.setSplfunction(cursor.getString(cursor.getColumnIndex("splfunction")));

                        //sathish
                        //dataSync.setSessionid("69FCEC30C24A");
                        //mohan
                        dataSync.setSessionid("7E3E8D875BBF");
                        dataSyncList.add(dataSync);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(null != cursor) {
                    cursor.close();
                }
            }
            return dataSyncList;
        }

        @Override
        protected void onPostExecute(List<DataSync> dataSyncs) {
            View syncDbListItemView;
            TextView tvSyncDbName;
            LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
            for(int i= 0; i < dataSyncs.size();i++){
                syncDbListItemView = layoutInflater.inflate(R.layout.sync_db_list_item, syncDbParentLayout, false);
                syncDbListItemView.findViewById(R.id.sync_parent_layout).setTag(dataSyncs.get(i).getId());
                tvSyncDbName = (TextView)syncDbListItemView.findViewById(R.id.tvSyncDbName);
                tvSyncDbName.setText(dataSyncs.get(i).getDisplayname());
                syncDbParentLayout.addView(syncDbListItemView);
            }
            SyncDBAsyncTaskMain syncDBAsyncTaskMain = new SyncDBAsyncTaskMain(syncDbParentLayout);
            syncDBAsyncTaskMain.execute(dataSyncs);
        }
    }

    private  class SyncDBAsyncTaskMain extends AsyncTask<Object, Integer, Void> {

        private int prevrecs;
        private int paginationRecord;
        private int totalRecords;
        DataSync dataSync = null;
        LinearLayout syncTableLayout;
        TextView mSyncDbPull, mSyncDbPush;
        ProgressBar mProgressBar;
        SQLiteDatabase db;
        int totalRecordToPush = 0;
        float currentRow;

        public SyncDBAsyncTaskMain(LinearLayout syncTableLayout) {
            this.syncTableLayout = syncTableLayout;
            db = DatabaseManager.getInstance().openDatabase();
        }

        @Override
        protected Void doInBackground(Object... params) {
            List<DataSync> list = (List<DataSync>) params[0];
            try {
                for(DataSync dataSync : list) {
                    if(dataSync.getDisplayname().equals("vCard Titles")) {
                        initialize();
                        this.currentRow = 0;
                        this.dataSync = dataSync;
                        currentDataSyncTable(dataSync.getId());
                        if(dataSync.getUsercheck() == 0) {
                            AppLog.logString("Processing table :: "+dataSync.getDisplayname());
                            //do only pull
                            pullData(prevrecs);
                        } else {
                            //do push and pull
                            totalRecordToPush = getTotalRecordPriorPush();
                            if(totalRecordToPush > 0) {
                                pushMetadata(totalRecordToPush);
                            }
                            else {
                                pullData(prevrecs);
                            }
                        }
                    }
                }
            } catch (IOException  | JSONException e) {
                AppLog.logString("Caught with Exception ::" +e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        private void initialize() {
            prevrecs = 0;
            paginationRecord = 50;
            totalRecords = 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            String currentRow = String.valueOf(values[0]);
            mProgressBar.setProgress(values[1]);
            mSyncDbPull.setText(currentRow);
            AppLog.logString("Helloooooooooooooo Tag: "+dataSync.getDisplayname());
            AppLog.logString("Helloooooooooooooo: "+mSyncDbPull.getText());
            if(values.length == 3)
                mSyncDbPush.setText(String.valueOf(values[2]));
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        public void currentDataSyncTable(Integer id) {
            for(int i =0; i < syncDbParentLayout.getChildCount(); i++) {
                View view = syncDbParentLayout.getChildAt(i);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.sync_parent_layout);
                if(linearLayout.getTag() != null && linearLayout.getTag().equals(id)) {
                    mProgressBar = (ProgressBar) view.findViewById(R.id.loading);
                    mSyncDbPull = (TextView)view.findViewById(R.id.syncDbPull);
                    mSyncDbPush = (TextView)view.findViewById(R.id.syncDbPush);
                    break;
                }
            }
        }

        private String constructPayload(DataSync dataSync) throws JSONException{
            JSONObject requestJSONObject = new JSONObject();
            requestJSONObject.put("prevrecs", dataSync.getPrevrecs());
            requestJSONObject.put("sessionid", dataSync.getSessionid());
            requestJSONObject.put("lastsynced", dataSync.getLastsynced());
            requestJSONObject.put("id", dataSync.getId());
            requestJSONObject.put("displayname", dataSync.getDisplayname());
            requestJSONObject.put("tablename", dataSync.getTablename());
            requestJSONObject.put("usercheck", dataSync.getUsercheck());
            requestJSONObject.put("paginationrecs", dataSync.getPaginationrecs());
            requestJSONObject.put("keynames", dataSync.getKeynames());
            requestJSONObject.put("columnlist", dataSync.getColumnlist());
            requestJSONObject.put("onebyonepush", dataSync.getOnebyonepush());
            String post = requestJSONObject.toString();
            AppLog.logString("parameters = " + post);
            return post;
        }

        private void pullData(int prevrecs) throws IOException, JSONException{
            dataSync.setPrevrecs(prevrecs);
            dataSync.setPaginationrecs(paginationRecord);
            dataSync.setOnebyonepush(false);
            AppLog.logString("Pagination record started from : "+prevrecs+" to "+paginationRecord+" And Total Record to process: "+totalRecords);
            parseResult(restPost(constructPayload(dataSync)));
        }

        private String restPost(String payload) throws IOException, JSONException {
            URL url;
            HttpURLConnection urlConnection = null;
            String result = null;
            BufferedReader bufferedReader = null;
            try {
                url = new URL(Constants.BASE_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(NetworkConstants.HEADER_POST);
                urlConnection.setRequestProperty("api-action", "getsyncdata");
                urlConnection.setRequestProperty(NetworkConstants.HEADER_CONTENT_LENGTH, String.valueOf(payload.getBytes().length));
                urlConnection.setRequestProperty("Content-Type", "application/json");

                OutputStream outStream = urlConnection.getOutputStream();
                Writer out = new OutputStreamWriter(outStream);
                out.write(payload);
                out.flush();
                out.close();
                int responseCode = urlConnection.getResponseCode();
                NetworkLogger.debugLog("responseCode " + responseCode);

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }
                result = builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        AppLog.logString("Error while closing stream "+ e.getMessage());
                    }
                }
            }
            return result;
        }

        private void parseResult(String result) throws IOException, JSONException{
            if(result != null) {
                JSONObject response = new JSONObject(result);
                float percentage = 0f;
                if(dataSync.getOnebyonepush()) {
                    //assuming that this is pushed result. so there is no need to handle the result.
                    AppLog.logString("Pushed records to server: "+pushLimit);
                    AppLog.logString("Total record to push: "+totalRecordToPush);
                    //calculate percentage only if there is an record
                    if(totalRecordToPush > 0) {
                        percentage = pushLimit * 50 / totalRecordToPush;
                        publishProgress(pushLimit, (int)percentage);
                    } else {
                        publishProgress(pushLimit, 50);
                    }
                } else {
                    // this is the one going to handle pulled/fetched results to perform operation
                    // either insert or update the records
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
                                currentRow += (i + 1);
                                percentage = currentRow * 50 / this.totalRecords;
                                insertOrUpdate((JSONObject)jsonArray.get(i));
                                AppLog.logString("current row:"+(int) currentRow);
                                AppLog.logString("percentage:"+percentage);
                                publishProgress((int)currentRow, (int) percentage);
                            }
                            if(dataSync.getUsercheck() == 0) {
                                publishProgress(totalRecords, 50, 0);
                            }
                        } else {
                            publishProgress(totalRecords, 50, 0);
                        }
                        //assume that we fetched all the records in a single http request for non-transaction
                        //table e.g., country, state and address_type etc. so there is no reason to fetch again
                        if(dataSync.getUsercheck() != 0) {
                            if(totalRecords > (prevrecs )) {
                                prevrecs = paginationRecord + prevrecs;
                                //paginationRecord = 50 + paginationRecord;
                                pullData(prevrecs);
                            }
                        }
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

        int offset = 1;
        int pushLimit = 50;
        public void pushMetadata(int totalRecordToPush) {
            try {
                if(totalRecordToPush <= pushLimit) {
                    dataSync.setPushdataarray(cursorRequestBody(pushLimit, offset));
                    dataSync.setPrevrecs(prevrecs);
                    dataSync.setPaginationrecs(pushLimit);
                    dataSync.setOnebyonepush(false);
                    parseResult(restPost(constructPayload(dataSync)));
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
                        parseResult(restPost(constructPayload(dataSync)));
                        offset = pushLimit;
                        pushLimit += pushLimit;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                AppLog.logString("Caught with exception :: "+e.getMessage());
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
    }
}
