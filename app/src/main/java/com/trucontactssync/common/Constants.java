/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.common;


import com.trucontactssync.access.VCardAccess;

/**
 * Created by user on 5/2/2016.
 */
public interface Constants extends BaseConstants {

    //make this false while moving to production
    boolean IS_LOG_ENABLED = true;//BuildConfig.MODE_DEV;

    //make this dev to false while moving to production
    boolean IS_DEV = true;//BuildConfig.MODE_DEV;

    int QUERY_LIMIT = 50;

    // Splash Screen
    int SPLASH_TIME = 2000;

    String KEY_EMAIL = "email";
    String KEY_PASSWORD = "password";
    String KEY_FIRST_NAME = "first_name";
    String KEY_LAST_NAME = "last_name";
    String KEY_SESSION_TOKEN = "sessionToken";
    String KEY_ERR_MSG = "errmsg";
    String KEY_ERROR = "error";
    String KEY_NEW_PWD = "newpwd";
    String KEY_OLD_PWD = "oldpwd";
    String KEY_EMAIL_MSG = "emailmsg";
    String KEY_OBJECT_ID = "objectId";
    String KEY_FIRSTNAME = "firstname";
    String KEY_LASTNAME = "lastname";
    String KEY_PROFILE_ID = "profiletypeid";
    String KEY_SOCIAL_LINK = "sociallink";
    String VALUE_OLD_PASSWORD = "Administrator";
    String INTENT_KEY_FROM = "from";
    String INTENT_VALUE_EDIT_VCARD = "edit_vcard";
    String INTENT_VALUE_ADD_VCARD = "add_vcard";
    String INTENT_KEY_OBJECT_ID = "vcard_id";
    String INTENT_VALUE_VIEW_VCARD = "view_vcard";
    String INTENT_VALUE_SHOW_VCARD = "show_vcard";
    String INTENT_VALUE_SHARE_VCARD = "share_vcard";
    String INTENT_VALUE_UN_SHARE_VCARD = "un_share_vcard";
    String INTENT_KEY_SHARE_TEXT = "share_text";
    String INTENT_KEY_SHARE_TEXT_TITLE = "share_text_title";
    String INTENT_KEY_SHARE_EMAIL_TITLE = "share_email_title";
    String INTENT_VALUE_DASHBOARD = "dashboard_activity";
    String INTENT_SELECTED_VCARD = "selected_vcard";
    String KEY_SUBJECT = "subject";
    String KEY_LONG_URL = "longUrl";

    String TAG_JSON_ERROR = "errmsg";

    String DIALOG_BUTTON_OK = "Ok";
    String DIALOG_BUTTON_YES = "Yes";
    String DIALOG_BUTTON_CANCEL = "Cancel";
    String DIALOG_BUTTON_NO = "No";

    /** For both testing & dev */
    String DEV_DOMAIN = "http://amci.dyndns.tv:3377/tcapis";

    /** Point this url when you move the app to production */
    String PROD_DOMAIN = "http://trucontacts.com";

    /** Base Domain */
    String URL_SHORTNER_URL = ((IS_DEV) ? (DEV_DOMAIN) : (PROD_DOMAIN)).concat("/showvcard.aspx");

    String BASE_URL = ((IS_DEV) ? (DEV_DOMAIN) : (PROD_DOMAIN)).concat("/processapi.aspx");

    // network constants
    //String BASE_URL = "http://trucontacts.com/processapi.aspx";

    String IMAGE_DOWNLOAD_URL = (IS_DEV) ? DEV_DOMAIN : PROD_DOMAIN;//"http://trucontacts.com";

    String APP_SHARE_URL = ((IS_DEV) ? DEV_DOMAIN : PROD_DOMAIN)+"/html/login-signup.html?inviteid=";//"http://trucontacts.com/html/login-signup.html?inviteid=";

    // NETWORK REQUESTING VARIABLES
    // Login
    int REQUEST_LOGIN = 1;
    int REQUEST_REGISTRATION = 2;
    int REQUEST_CHANGE_PWD = 3;
    int REQUEST_FORGOT_PASSWORD = 4;
    int REQUEST_DATA_SYNC = 5;
    int REQUEST_FORGOT_PASSWORD_SEND_EMAIL = 6;
    int REQUEST_SIGN_UP_SEND_EMAIL = 7;

    // Login
    String KEY_RESULT = "result";

    /**
     * Progress Dialog's Text Messages & Titles
     */
    String LOADING_GET_CONTACTS_LIST = "Please wait, Fetching the contacts ...";
    String LOADING_VCARD_DETAILS = "Please wait, Fetching the details ...";
    String LOADING_LOGIN = "Please wait, logging in...";
    String LOADING_REGISTRATION = "Please wait, registering your details...";
    String LOADING_SEND_EMAIL = "Please wait, sending email...";

    String DIALOG_MESSAGE_NO_STATES = "No states available for the selected country";



    // webservice keys
    String STATUS = "Status";
    String USERID = "userid";

    // webservice values
    String STATUS_SUCCESS = "success";
    String STATUS_FAILURE = "failure";


    //request code
    int USER_ADDRESS_DIALOG_REQUEST_CODE = 1;
    int USER_PHONE_DIALOG_REQUEST_CODE = 2;
    int USER_EMAIL_DIALOG_REQUEST_CODE = 3;
    int USER_URL_DIALOG_REQUEST_CODE = 4;
    int USER_SOCIAL_DIALOG_REQUEST_CODE = 5;
    int SELECT_PHOTO_DIALOG_REQUEST_CODE = 6;
    int PICK_IMAGE_FROM_GALLERY = 7;
    int TAKE_PHOTO = 8;
    int SELECT_SHARE_DIALOG_REQUEST_CODE = 9;
    int TERMS_AND_CONDITIONS_REQUEST_CODE = 10;

    //create vCard Fragment
    String TYPE = "SelectType";
    String SELECT_ADDRESS_TYPE = "Select Address Type";
    String SELECT_PHONE_TYPE = "Select Phone Type";
    String SELECT_EMAIL_TYPE = "Select Email Type";
    String SELECT_URL_TYPE = "Select URL Type";
    String SELECT_PROFILE_TYPE = "Select Profile Type";
    String SELECT_PHOTO_TYPE = "Choose Your Action";
    String SELECT_SHARE_TYPE = "Select Share option";
    String DIALOG_TAG = "dialog";
    String SELECTED_TYPE = "Selected Type";
    String RESULT = "Result";
    String OK = "OK";
    String SETTINGS = "Settings";
    String LOGIN_FRAGMENT = "login_fragment";
    String LOGIN_SIGN_UP_BTN_LABEL = "loginOrSignUpBtnLbl";
    String SIGN_UP_FRAGMENT = "sign_up_fragment";
    String FORGOT_PASSWORD_FRAGMENT = "forgot_password_fragment";
    String SIGN_UP_LOGIN_FRAGMENT = "sign_up-login_fragment";

    String DELETE_ALERT = "Are you sure you want to delete?";
    String VCARD_NAME_ALERT = "Enter Vcard Name";
    String VCARD_SUCCESSFUL_ALERT = "Vcard created successfully";
    String CREATE_VCARD_SUCCESSFUL_ALERT = "Vcard created successfully";
    String EDIT_VCARD_SUCCESSFUL_ALERT = "Vcard modified successfully";
    String DELETE_VCARD_SUCCESSFUL_ALERT = "Vcard deleted successfully";
    String UNSHARE_ALERT = "Are you sure to Unshare?";
    String INVITE_RECALL_SUCCESS = "Invite recalled successfully";
    String DELETE_VCARD_ALERT = "Are you sure you want to delete this vCard?";


    String IMAGE_URI = "image_uri";

    //sqlite db version. increase this value for db upgrade.
    int DB_VERSION = 1;

    int INDEX_COUNTRY = 0;

    String PREV_RECS = "prevrecs";
    String SESSION_ID = "sessionid";
    String LAST_SYNCED = "lastsynced";
    String ID = "id";
    String DISPLAY_NAME = "displayname";
    String TABLE_NAME = "tablename";
    String USER_CHECK = "usercheck";
    String PAGINATION_RECS = "paginationrecs";
    String KEY_NAMES = "keynames";
    String PUSH_DATA_ARRAY = "pushdataarray";
    String COLUMN_LIST = "columnlist";
    String KEY_ONE_BY_ONE_PUSH = "onebyonepush";

    String WIFI = "WIFI";
    String MOBILE = "MOBILE";

    String COUNTRY_LAST_SYNCED = "country_last_synced";
    String COUNTRY_PREV_RECS = "country_prev_recs";
    int COUNTRY_RECS_TO_LOAD = 200;
    String NO_OF_RECS = "noofrecs";
    String OBJECT_ID = "objectId";
    String UPDATED_AT = "updatedAt";
    String COUNTRY_NAME = "countryname";
    String COUNTRY_ID = "countryid";
    String CODE = "code";
    String STATE_NAME = "name";
    String VCARD_ID = "vcardid";
    String USER_ID = "userid";
    String ISDELETED = "isdeleted";
    String PARSE_OBJ_ID = "parseobjid";
    String ROWDATA = "rowdata";
    String ADDRESS_TYPE_ID = "addresstypeid";
    String URL_PATH = "urlpath";
    String LAST_CHECKED = "lastchecked";
    String ACCEPTED = "accepted";
    String FIRST_NAME = "firstname";
    String LAST_NAME = "lastname";
    String CARD_NICK_NAME = "cardnickname";
    String CARD_NOTES = "cardnotes";
    String PHONE_NO = "phoneno";
    String EMAIL_ID = "emailid";
    String SENT_USER_NAME = "sentusername";
    String INVITE_TYPE = "invitetype";
    String SENT_BY_FN = "sentbyfn";
    String SENT_BY_LN = "sentbyln";
    String INVITE_ID = "inviteid";
    String SOURCE_APP = "sourceapp";
    String RECD_BY_FN = "recdbyfn";
    String RECD_BY_LN = "recdbyln";
    String IMAGE_LINK = "imagelink";
    String IMAGE_DATA = "imagedata";
    String CONTACTS_ADDRESS_TYPE = "addresstype";
    String CONTACTS_TYPE = "type";
    String CARD_TYPE = "cardtype";


    String STATE_LAST_SYNCED = "state_last_synced";
    String STATE_PREV_RECS = "state_prev_recs";
    int STATE_RECS_TO_LOAD = 200;
    String SORT_ASCENDING = " ASC ";
    String SORT_DESCENDING = " DESC ";

    String ADDRESS_TYPE_LAST_SYNCED = "address_type_last_synced";
    String ADDRESS_TYPE_PREV_RECS = "address_type_prev_recs";
    int ADDRESS_TYPE_RECS_TO_LOAD = 200;

    String INVITE_SENT_LAST_SYNCED = "invite_sent_last_synced";
    String INVITE_SENT_PREV_RECS = "invite_sent_prev_recs";
    int INVITE_SENT_RECS_TO_LOAD = 200;

    String INVITE_RECEIVED_LAST_SYNCED = "invite_received_last_synced";
    String INVITE_RECEIVED_PREV_RECS = "invite_received_prev_recs";
    int INVITE_RECEIVED_RECS_TO_LOAD = 200;

    String VCARD_HEADER_LAST_SYNCED = "vcard_header_last_synced";
    String VCARD_HEADER_PREV_RECS = "vcard_header_prev_recs";
    int VCARD_HEADER_RECS_TO_LOAD = 200;

    String VCARD_URL_LAST_SYNCED = "vcard_url_last_synced";
    String VCARD_URL_PREV_RECS = "vcard_url_prev_recs";
    int VCARD_URL_RECS_TO_LOAD = 200;

    String VCARD_SOCIAL_PROFILE_LAST_SYNCED = "vcard_profile_last_synced";
    String VCARD_SOCIAL_PROFILE_PREV_RECS = "vcard_profile_prev_recs";
    int VCARD_SOCIAL_PROFILE_RECS_TO_LOAD = 200;

    String VCARD_ADDRESS_LAST_SYNCED = "vcard_address_last_synced";
    String VCARD_ADDRESS_PREV_RECS = "vcard_address_prev_recs";
    int VCARD_ADDRESS_RECS_TO_LOAD = 200;

    String VCARD_EMAIL_LAST_SYNCED = "vcard_email_last_synced";
    String VCARD_EMAIL_PREV_RECS = "vcard_email_prev_recs";
    int VCARD_EMAIL_RECS_TO_LOAD = 200;

    String VCARD_PHONE_LAST_SYNCED = "vcard_phone_last_synced";
    String VCARD_PHONE_PREV_RECS = "vcard_phone_prev_recs";
    int VCARD_PHONE_RECS_TO_LOAD = 200;

    String USER_ALERT_CHECK_LAST_SYNCED = "user_alert_check_last_synced";
    String USER_ALERT_CHECK_PREV_RECS = "user_alert_check_prev_recs";
    int USER_ALERT_CHECK_RECS_TO_LOAD = 200;

    String VCARD_CHANGE_LOG_SYNCED = "vcard_change_log_last_synced";
    String VCARD_CHANGE_LOG_PREV_RECS = "vcard_change_log_prev_recs";
    int VCARD_CHANGE_LOG_RECS_TO_LOAD = 200;

    String VCARD_IMAGE_LAST_SYNCED = "vcard_image_last_synced";
    String VCARD_IMAGE_PREV_RECS = "vcard_image_log_prev_recs";
    int VCARD_IMAGE_RECS_TO_LOAD = 200;

    String INVITE_WITHDRAWN_LAST_SYNCED = "invite_withdrawn_last_synced";
    String INVITE_WITHDRAWN_PREV_RECS = "invite_withdrawn_prev_recs";
    int INVITE_WITHDRAWN_RECS_TO_LOAD = 200;

    String FEEDBACK_LAST_SYNCED = "feedback_last_synced";
    String FEEDBACK_PREV_RECS = "feedback_prev_recs";
    int FEEDBACK_RECS_TO_LOAD = 200;

    String LOCAL_CONTACTS_LAST_SYNCED = "local_contacts_last_synced";
    String LOCAL_CONTACTS_PREV_RECS = "local_conatcts_prev_recs";
    int LOCAL_CONTACTS_RECS_TO_LOAD = 100;


    // SYNC Constants
    int SYNC_COUNTRY = 0;
    int SYNC_STATE = 1;
    int SYNC_ADDRESS_TYPE = 2;
    int SYNC_VCARD_HEADER = 3;
    int SYNC_VCARD_URL = 4;
    int SYNC_VCARD_SOCIAL_PROFILE = 5;
    int SYNC_VCARD_PHONE = 6;
    int SYNC_VCARD_EMAIL = 7;
    int SYNC_VCARD_ADDRESS = 8;
    int SYNC_INVITE_RECEIVED = 9;
    int SYNC_INVITE_SENT = 10;
    int SYNC_VCARD_CHANGE_LOG = 13;
    int SYNC_USER_ALERT_CHECK = 14;
    int SYNC_VCARD_IMAGE = 17;
    int SYNC_INVITE_WITHDRAWN = 15;
    int SYNC_FEEDBACK = 18;
    int SYNC_LOCAL_CONTACTS = 19;
    int COPY_LOCAL_CONTACTS = 20;
    int UPLOAD_LOCAL_CONTACTS = 21;


    int COMPLETE_SYNC = 51;


    String SELECT_COUNTRY = "Select Country";
    String SELECT_STATE = "Select State";
    String TYPE_COUNTRY = "type_country";
    String TYPE_STATE = "type_state";
    String TAG_ASSIGNED = "tag_assigned";
    String TAG_UNASSIGNED = "tag_unassigned";
    String EMPTY_STRING = "";
    String SUB_TYPE = "subtype";
    String TYPE_NAME = "typename";
    String ORDER_NO_TYPE = "orderno";
    String TYPE_INVITES_RECEIVED = "invites_received";
    String TYPE_INVITES_SENT = "invites_sent";
    String TYPE_INVITES_ACCEPTED = "invites_accepted";
    String TYPE_VCARD_CHANGE_LOG = "vcard_change_log";
    String TYPE_VCARD_DELETED = "vcard_deleted";
    String TYPE_INVITES_WITHDRAWN_RECEIVER = "invites_withdrawn_receiver";
    String TYPE_INVITES_WITHDRAWN_SENDER = "invites_withdrawn_sender";
    String TYPE_FORWARDS_SENT = "forwards_sent";
    String TYPE_FORWARDS_ACCEPTED = "forwards_accepted";
    String TYPE_FORWARDS_RECEIVED = "forwards_received";

    String TYPE_DEVICE_CONTACT = "device_contact";
    String TYPE_TRUCONTACTS_CONTACT = "trucontacts_contact";


    // TABLE Constants
    int VCARD_ADDRESS = 0;
    int VCARD_MAIL = 1;
    int VCARD_URL = 2;
    int VCARD_SOCIAL = 3;
    int VCARD_PHONE = 4;
    int VCARD_MAIN = 5;
    int INVITES_SENT = 6;
    int INVITES_RECEIVED = 7;
    int VCARD_IMAGE = 8;

    //String TABLE_HEADER_SELECT = VCardAccess.COLUMN_USER_ID + " = ? AND "+VCardAccess.COLUMN_IS_DELETED + " = ? AND "+VCardAccess.COLUMN_CARD_TYPE+"=\""+CardTypeConstants.VCARD_CONTACT+"\"";
    String TABLE_HEADER_UPDATE = VCardAccess.COLUMN_OBJECT_ID + " = ? AND "+VCardAccess.COLUMN_USER_ID + " = ?";
    //String TABLE_COMMON_VALUES_UPDATE = VCardMailAccess.COLUMN_OBJECT_ID + " = ? AND "+VCardMailAccess.COLUMN_USER_ID + " = ? AND "+VCardMailAccess.COLUMN_VCARD_ID + " = ?";
    String TABLE_OBJECT_ID_SELECT = VCardAccess.COLUMN_OBJECT_ID + " = ?";
    String TABLE_HEADER_VCARD_SELECT = VCardAccess.COLUMN_OBJECT_ID + " = ?";
    //String TABLE_VCARD_IMAGE_SELECT = VCardImageAccess.COLUMN_VCARD_ID + " = ? AND "+VCardImageAccess.COLUMN_USER_ID + " = ? AND "+VCardImageAccess.COLUMN_IS_DELETED + " = ?";
    String TABLE_HEADER_SELECT_OTHERS_CONTACT = VCardAccess.COLUMN_USER_ID + " != ?";
    String TABLE_LOCAL_CONTACTS_SELECT = VCardAccess.COLUMN_OBJECT_ID + " = ?";


    public static final String EXIT_APP="Are you sure want to exit the app?";
    public static final String APP_NAME="TruContacts";

    String INTENT_VALUE_CONTACT = "contacts";

    /** This api key was created using Sathish account for dev */
    String GOOGLE_API_KEY_DEV = "AIzaSyARKumR6z4acOM66A4ln6Yxu0wrTayqpCQ";

    /** Gogole shorter url */
    String GOOGLE_SHORTER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=";

}
