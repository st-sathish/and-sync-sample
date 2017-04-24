/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.managers;

import android.content.Context;
import android.os.Bundle;


import com.trucontactssync.access.country.CountryAccess;
import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.common.ErrorResponse;
import com.trucontactssync.common.util.NetworkUtil;
import com.trucontactssync.model.Country;
import com.trucontactssync.model.DataSync;
import com.trucontactssync.network.NetworkDelegate;
import com.trucontactssync.networkprovider.DataSyncProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCountryManager extends AbstractManager implements NetworkDelegate {

    //private final Context mContext;
    //private OnNextDataSync onNextDataSync;
    //private int totalRecords;
    CountryAccess countryAccess;
    //SQLiteDatabase database;

    public GetCountryManager(Context context) {
        this.mContext = context;
        //database = DatabaseManager.getInstance().openDatabase();
        countryAccess = new CountryAccess(database);
    }

    public GetCountryManager(Context context, DataSync dataSync) {
        this.mContext = context;
        this.mDataSync = dataSync;
        database = DatabaseManager.getInstance().openDatabase();
        countryAccess = new CountryAccess(database);
    }

    public void callCountryService() {
        if (mDataSync != null) {
            DataSync dataSync = buildDataSync(Constants.COUNTRY_PREV_RECS, Constants.COUNTRY_LAST_SYNCED, Constants.COUNTRY_RECS_TO_LOAD);
            if (NetworkUtil.isNetworkAvailable(mContext)) {
                DataSyncProvider dataSyncProvider = new DataSyncProvider();
                dataSyncProvider.createRequest(Constants.REQUEST_DATA_SYNC, this, getDataSyncBundle(dataSync), null);
            }
        }
    }

    @Override
    public void callBack(Bundle bundle) {
        callbackHandler(bundle, Constants.COUNTRY_LAST_SYNCED, Constants.COUNTRY_PREV_RECS, Constants.COUNTRY_RECS_TO_LOAD);
    }

    @Override
    protected void repeatService() {
        callCountryService();
    }

    @Override
    protected void errorCallback(ErrorResponse errorResponse) {

    }

    @Override
    protected void successCallback(String responseData) {
        insertDataInDB(getDataFromResponseObject(responseData));
    }

    private List<Country> getDataFromResponseObject(String responseData) {
        List<Country> countryList = new ArrayList<Country>();
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            setPaginationTotalRecords(jsonObject);
            String result = jsonObject.getString(Constants.KEY_RESULT);
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Country country = new Country();
                    JSONObject jsonChildObject = jsonArray.getJSONObject(i);
                    country.setObjectid(jsonChildObject.getString(Constants.OBJECT_ID));
                    country.setUpdatedAt(jsonChildObject.getString(Constants.UPDATED_AT));
                    JSONObject rowData = new JSONObject();
                    rowData.put(Constants.COUNTRY_NAME, jsonChildObject.getString(Constants.COUNTRY_NAME));
                    country.setRowdata(rowData.toString());
                    //country.setCountryname(jsonChildObject.getString(Constants.COUNTRY_NAME));
                    countryList.add(country);
                }
            }
        } catch (JSONException exception) {
            AppLog.logString("Exception " + exception);
        }
        return countryList;
    }

    private void insertDataInDB(List<Country> countryList) {
        for (int i = 0; i < countryList.size();i++) {
            String[] whereArgs = {countryList.get(i).getObjectid()};
            List<Country> dataList = (List<Country>) countryAccess.select(Constants.TABLE_OBJECT_ID_SELECT,
                    whereArgs, null);
            if(dataList != null && dataList.size() > 0){
                //updateDataInDB(countryList.get(i));
            }else{
                //insertDataInDB(countryList.get(i));
            }
            trProgressCompleted +=1 ;
            AppLog.logString("Total record download & inserted completed: "+trProgressCompleted);
        }
    }

    private void insertDataInDB(Country country) {
        countryAccess.insert(country);
    }

    private void updateDataInDB(Country country) {
        String[] whereArgs = {country.getObjectid()};
        countryAccess.update(country, Constants.TABLE_OBJECT_ID_SELECT, whereArgs);
    }
}
