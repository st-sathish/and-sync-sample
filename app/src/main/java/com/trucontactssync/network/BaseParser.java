/*
 * Copyright (c) Ravikiran Srinivasan
 */

/**
 *
 */
package com.trucontactssync.network;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class BaseParser {
    public BaseParser() {

    }

    /**
     * Method to read error messages from the service response and put back in Bundle
     *
     * @param response
     * @param bundle
     * @return bundle
     */
    /*public Bundle readErrorResponse(String response, Bundle bundle) {

		try {
			JSONObject reader = new JSONObject(response);
			
			CmolError error = new CmolError();
			
			if (reader.has(Constants.ERROR_CODE)) {
				error.setErrorCode(reader.getString(Constants.ERROR_CODE));
			}
			else {
				error.setErrorCode("999");
			}
			
			error.setErrorMessage(reader.getString(Constants.RESPONSE_EXCEPTION));
			
			bundle.putParcelable(Constants.ENTITY_ERROR_RESPONSE, error);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return bundle;
	}*/
    public Bundle readResponse(String response, Bundle bundle) {

        if (response != null && !response.equals("")) {
            try {
                JSONObject reader = new JSONObject(response);

                Iterator<String> keysIterator = reader.keys();

                while (keysIterator.hasNext()) {
                    String key = keysIterator.next();

                    Object value = reader.get(key);

                    bundle.putString(key, value.toString());

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            bundle.putString(NetworkConstants.RESPONSE_EMPTY_KEY, NetworkConstants.RESPONSE_EMPTY_VALUE);
        }

        return bundle;
    }
}
