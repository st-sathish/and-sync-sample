/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.network;

/**
 * Created by DuraiTamil on 10/1/2015.
 */
public class NetworkConstants {
    public static final String APPLICATION_TAG = "TruContacts";
    public static final int CONNECTION_TIME_OUT = 60 * 1000; // 60 secs
    public static final int READ_TIME_OUT = 30 * 1000; // 30 secs
    public static final String HEADER_POST = "POST";
    public static final String HEADER_GET = "GET";

    public static final String REQUEST_TYPE = "requesttype";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String CONNECTION = "connection";
    public static final String NO_CONNECTION = "noConnection";
    public static final String RESPONSE_CODE = "responsecode";
    public static final String RESPONSE_DATA = "responsedata";
    public static final String RESPONSE_ERROR = "responseerror";
    public static final String RESPONSE_EMPTY_KEY = "responseempty";
    public static final String RESPONSE_EMPTY_VALUE = "yes";
    public static final String RESPONSE_ON_SUCCESS = "000";

    // General keys
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String ERROR_CODE = "errorCode";
    public static final String RESPONSE_STATUS = "status";
    public static final String RESPONSE_EXCEPTION = "errorMessage";
    public static final String DIALOG_TITLE_CONNECTIVITY = "System Error";

    public static final String DIALOG_MESSAGE_SOCKET_ERROR = "We are unable to process your request at this time. Please try again later.";
    public static final String DIALOG_MESSAGE_FORGOT_PASSWORD_EMAIL = "Please check your email. It may take few minutes to reach your inbox.";
    public static final String DIALOG_MESSAGE_EMAIL_ERROR = "We are unable to send email at this time. Please try again later.";


    public static final String RESPONSE_NETWORK_EXCEPTION = "999";

}
