/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.network;

import android.os.Bundle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * ServiceProvider class which provides methods to initiate web service calls
 *
 * @author A71776
 */
public class ServiceProvider {

    NetworkDelegate networkDelegate;

    /**
     * Method to create a new HTTP Connection
     *
     * @param url
     * @return
     */
    protected HttpURLConnection createHttpConnection(String url) {
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();
        } catch (MalformedURLException e) {
            NetworkLogger.errorLog("Exception " + e);
        } catch (IOException e) {
            NetworkLogger.errorLog("Exception " + e);
        }
        return connection;
    }

    /**
     * Method to trigger the asynchronous network call
     *
     * @param connection
     * @param postBody
     * @param requestType
     * @param providerCallback
     */
    protected void requestWebServiceMethod(HttpURLConnection connection,
                                           String postBody, int requestType, ServiceProvider providerCallback) {

        AsyncNetworkTask networktask = new AsyncNetworkTask(connection,
                postBody, requestType, providerCallback);
        networktask.execute();

    }

    /**
     * Method which will be overridden by the sub classes to parse the network
     * response
     *
     * @param bundle
     */
    public void parseResponse(Bundle bundle) {
        // Do nothing
    }

    /**
     * Method to set the common HTTP Parameters
     *
     * @param connection
     * @return
     */
    protected HttpURLConnection setCommonParameters(
            HttpURLConnection connection) {

        connection.setDoInput(true);
        connection.setConnectTimeout(NetworkConstants.CONNECTION_TIME_OUT);
        connection.setReadTimeout(NetworkConstants.READ_TIME_OUT);
        if (connection.getRequestMethod().equalsIgnoreCase(NetworkConstants.HEADER_POST)) {
            connection = setCommonPostParameters(connection);
        }
        return connection;
    }

    /**
     * Method to set the common HTTP Post Parameters
     *
     * @param connection
     * @return
     */
    private HttpURLConnection setCommonPostParameters(
            HttpURLConnection connection) {
        connection.setDoOutput(true);
        return connection;
    }

    /**
     * Method to check error and fetch the Refresh token
     *
     * @param bundle
     */
    void fetchErrorInformation(Bundle bundle) {

        String response = bundle.getString(NetworkConstants.RESPONSE_DATA);

        if (response != null) {
            // Here Need to generate new access token using refresh token and
            // then make the failed call again
        }
    }
}