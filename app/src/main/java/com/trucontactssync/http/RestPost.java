package com.trucontactssync.http;

import com.trucontactssync.common.AppLog;
import com.trucontactssync.common.Constants;
import com.trucontactssync.model.DataSync;
import com.trucontactssync.network.NetworkConstants;
import com.trucontactssync.network.NetworkLogger;

import org.json.JSONException;

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

/**
 * Created by CS39 on 5/7/2017.
 */

public class RestPost {

    private String payload;
    private String action;

    public RestPost(String action, String payload) {
        this.action = action;
        this.payload = payload;
    }

    private HttpURLConnection getHttpUrlConnection() {
        URL url;
        HttpURLConnection urlConnection = null;
        return urlConnection;
    }

    public String restPost(String payload) throws IOException, JSONException {
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
}
