package com.trucontactssync.datasync.utils;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.model.DataSync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CS39 on 5/7/2017.
 */

public class DataSyncUtils {

    private DataSyncUtils(){};

    /**
     * DataSync to construct payload
     * @param dataSync
     * @return
     * @throws JSONException
     */
    public static String constructPayload(DataSync dataSync) throws JSONException {
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
}
