/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.networkprovider;

import android.os.Bundle;
import android.text.TextUtils;


import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.model.DataSync;
import com.trucontactssync.network.NetworkConstants;
import com.trucontactssync.network.NetworkDelegate;
import com.trucontactssync.network.ServiceProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class DataSyncProvider extends ServiceProvider {

    NetworkDelegate networkDelegate;
    DataSync mDataSync = null;

    public void createRequest(int requestType, NetworkDelegate networkDelegate, Bundle requestBundle, DataSync dataSync) {
        this.networkDelegate = networkDelegate;
        mDataSync = dataSync;
        HttpURLConnection request = null;
        String bodyContent = null;
        if (requestType == Constants.REQUEST_DATA_SYNC) {
            bodyContent = getContentBodyForRegistration(requestBundle);
            request = getWebRequest(requestType);
        }
        if (request != null) {
            requestWebServiceMethod(request, bodyContent, requestType, this);
        }
    }

    private HttpURLConnection setCommonRequestParameters(HttpURLConnection request) {
        return request;
    }

    private String getContentBodyForRegistration(Bundle requestBundle) {
        if (mDataSync == null) {
            mDataSync = new DataSync();
            mDataSync.setPrevrecs(requestBundle.getInt(Constants.PREV_RECS));
            mDataSync.setSessionid(requestBundle.getString(Constants.SESSION_ID));
            mDataSync.setLastsynced(requestBundle.getLong(Constants.LAST_SYNCED));
            mDataSync.setId(requestBundle.getInt(Constants.ID));
            mDataSync.setDisplayname(requestBundle.getString(Constants.DISPLAY_NAME));
            mDataSync.setTablename(requestBundle.getString(Constants.TABLE_NAME));
            mDataSync.setUsercheck(requestBundle.getInt(Constants.USER_CHECK));
            mDataSync.setPaginationrecs(requestBundle.getInt(Constants.PAGINATION_RECS));
            mDataSync.setKeynames(requestBundle.getString(Constants.KEY_NAMES));
//            if (requestBundle.containsKey(Constants.PUSH_DATA_ARRAY)) {
//                dataSyncRequest.setPushdataarray(requestBundle.getStringArrayList(Constants.PUSH_DATA_ARRAY));
//            }
            mDataSync.setColumnlist(requestBundle.getString(Constants.COLUMN_LIST));
        }

        JSONObject requestJSONObject = new JSONObject();
        try {
            requestJSONObject.put("prevrecs", mDataSync.getPrevrecs());
            requestJSONObject.put("sessionid", mDataSync.getSessionid());
            requestJSONObject.put("lastsynced", mDataSync.getLastsynced());
            requestJSONObject.put("id", mDataSync.getId());
            requestJSONObject.put("displayname", mDataSync.getDisplayname());
            requestJSONObject.put("tablename", mDataSync.getTablename());
            requestJSONObject.put("usercheck", mDataSync.getUsercheck());
            requestJSONObject.put("paginationrecs", mDataSync.getPaginationrecs());
            requestJSONObject.put("keynames", mDataSync.getKeynames());
            requestJSONObject.put("columnlist", mDataSync.getColumnlist());
            if(mDataSync.getTablename().equals("vcardimages")){
                requestJSONObject.put("onebyonepush", mDataSync.getOnebyonepush());
            }

            if (mDataSync.getPushdataarray() != null) {
                requestJSONObject.put("pushdataarray", mDataSync.getPushdataarray());
            }
        } catch (JSONException exception) {
            AppLog.logString("Exception " + exception);
        }

        String post = requestJSONObject.toString();
        AppLog.logString("parameters = " + post);
        return post;
    }

    HttpURLConnection getWebRequest(int requestType) {
        HttpURLConnection request = null;
        try {
            request = createHttpConnection(Constants.BASE_URL);
            if (requestType == Constants.REQUEST_DATA_SYNC) {
                request.setRequestProperty("api-action", "getsyncdata");
            }
            request.setRequestMethod(NetworkConstants.HEADER_POST);
            request = setCommonParameters(request);
            request = setCommonRequestParameters(request);

        } catch (ProtocolException e) {
            AppLog.logString("Exception " + e);
        }
        return request;
    }

    public void parseResponse(Bundle bundle) {
        String response = bundle.getString(NetworkConstants.RESPONSE_DATA);
        int requestType = bundle.getInt(NetworkConstants.REQUEST_TYPE);
        int responseCode = bundle.getInt(NetworkConstants.RESPONSE_CODE);
        if (responseCode != -1 && response != null) {
            try {
                if (requestType == Constants.REQUEST_DATA_SYNC) {
                    JSONObject jsonObject = new JSONObject(response);
                    String errorMsg = jsonObject.getString(Constants.KEY_ERR_MSG);
                    if (!TextUtils.isEmpty(errorMsg)) {
                        bundle.putString(Constants.STATUS, Constants.STATUS_FAILURE);
                        bundle.putString(Constants.TAG_JSON_ERROR, errorMsg);
                    }
                }
            } catch (JSONException exception) {
                bundle.putString(Constants.STATUS, Constants.STATUS_SUCCESS);
            }
        }
        networkDelegate.callBack(bundle);
    }
}
