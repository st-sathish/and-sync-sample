/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.network;

import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;


public class AsyncNetworkTask extends AsyncTask<Void, Void, Bundle> {

    HttpURLConnection mConnection;
    int mRequestType;
    ServiceProvider mCallback;
    String mPostBody;

    public AsyncNetworkTask(HttpURLConnection connection, String postBody,
                            int requestType, ServiceProvider callback) {
        this.mConnection = connection;
        this.mRequestType = requestType;
        this.mCallback = callback;
        this.mPostBody = postBody;
    }

    @Override
    protected Bundle doInBackground(Void... params) {
        InputStream inputStream = null;
        Bundle bundle = new Bundle();
        bundle.putInt(NetworkConstants.REQUEST_TYPE, mRequestType);
        String responseMsg = null;

        try {
            NetworkLogger.infoLog("Connection URL: " + mConnection.getURL());
            NetworkLogger.infoLog("Inside AynctaskPOSTBODY: " + mPostBody);
            if (mPostBody != null) {

                mConnection.setRequestProperty(NetworkConstants.HEADER_CONTENT_LENGTH,
                        String.valueOf(mPostBody.getBytes().length));
                mConnection.setRequestProperty("Content-Type", "application/json");
                mConnection.setInstanceFollowRedirects(false);

                OutputStream outStream = mConnection.getOutputStream();
                Writer out = new OutputStreamWriter(outStream);
                out.write(mPostBody);
                out.flush();
                out.close();
            }

            int responseCode = mConnection.getResponseCode();
            NetworkLogger.debugLog("responseCode " + responseCode);

            if (mConnection != null) {
                responseMsg = mConnection.getResponseMessage();
            } else {
                bundle.putString(NetworkConstants.CONNECTION, NetworkConstants.NO_CONNECTION);
            }
            NetworkLogger.debugLog("responseMsg " + responseMsg);

            try {
                inputStream = mConnection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
                //Logger.errorLog("Network Exceptn: ", e.getMessage());
                //inputStream = mConnection.getErrorStream();
            }

            if (inputStream != null) {
                /*Gson gson = new Gson();

				Reader reader = new InputStreamReader(inputStream);
				Customer memberDetails = gson.fromJson(reader, Customer.class);
				GlobalCache.getInstance().setCustomer(memberDetails);*/
                String responseString = readInputStream(inputStream);
                NetworkLogger.debugLog("responseString " + responseString);


                // Checking whether received any server exceptions or not
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    bundle.putInt(NetworkConstants.RESPONSE_CODE, responseCode);
                    bundle.putString(NetworkConstants.RESPONSE_DATA, responseString);
                    bundle.putString(NetworkConstants.ERROR_CODE, NetworkConstants.RESPONSE_ON_SUCCESS);
                } else {
                    bundle.putString(NetworkConstants.ERROR_CODE, String.valueOf(responseCode));
                    bundle.putString(NetworkConstants.RESPONSE_EXCEPTION, responseString);
                }
            }
        } catch (IOException e) {
            NetworkLogger.errorLog("Exception " + e);
            bundle.putString(NetworkConstants.ERROR_CODE, NetworkConstants.RESPONSE_NETWORK_EXCEPTION);
            bundle.putString(NetworkConstants.RESPONSE_STATUS, NetworkConstants.DIALOG_TITLE_CONNECTIVITY);
            bundle.putString(NetworkConstants.RESPONSE_EXCEPTION, NetworkConstants.DIALOG_MESSAGE_SOCKET_ERROR);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    NetworkLogger.errorLog("Exception " + e);
                }
            }

            if (mConnection != null) {
                mConnection.disconnect();
            }
        }

        return bundle;
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        mCallback.parseResponse(bundle);
    }


    public String readInputStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            NetworkLogger.errorLog("Exception " + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    NetworkLogger.errorLog("Exception " + e);
                }
            }
        }
        return total.toString();
    }
}