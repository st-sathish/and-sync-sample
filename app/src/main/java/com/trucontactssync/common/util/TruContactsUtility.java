/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.trucontactssync.common.TruContactsConstant;

import org.json.JSONObject;

/**
 * Utility class for tru Contacts
 */
public final class TruContactsUtility {

    private static final String DEFAULT_ERROR_MSG = "Some thing went wrong";

    private TruContactsUtility() {
        //avoid to create new instance
    }

    /**
     *
     * @param context
     *      the application context
     * @return the db absolute path
     */
    public static String getDBPath(Context context) {
        return (android.os.Build.VERSION.SDK_INT >= 17) ? context.getApplicationInfo().dataDir + "/databases/" : context.getFilesDir().getPath() + context.getPackageName() + "/databases/";
    }

    /**
     * Get the value using key from jsonObject
     * @param key
     *      the key which is going to get the value from the json object
     * @return
     *   the error message
     */
    public static String getErrorMessage(String key, JSONObject jsonObjectParam) {
        JSONObject jsonObject;
        try {
            if(null != jsonObjectParam) {
                if(jsonObjectParam.has(key)) {
                    return jsonObjectParam.getString(key);
                }
            }
        } catch(Exception e) {

        }
        return DEFAULT_ERROR_MSG;
    }

    /**
     * Validate the email
     * @param target
     *      check the target is the valid email
     * @return
     *  the boolean
     */
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * @param context
     *      current activity
     * @return the user id
     */
    public static String getUserIdFromSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", "");
    }

    public static String getPassowrdFromSharedPreference(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }

//    public static String getSessionTokenFromSharedPreference(Activity activity) {
//        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
//        return sharedPreferences.getString("sessionToken", "");
//    }

    public static String getSessionTokenFromSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("sessionToken", "");
    }

    public static String getFirstNameFromSharedPreference(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("first_name", "");
    }

    public static String getLastNameFromSharedPreference(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("last_name", "");
    }

    public static String getInviteIdFromSharedPreference(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("invite_id", "");
    }

    /**
     * Put the user id in the shared preference
     * @param activity
     *      activity
     * @param userId
     *      object id for the authenticated user
     */
    public static void putUserIdInSharedPreference(Activity activity, String userId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }

    public static void putPassowrdInSharedPreference(Activity activity, String password) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
    }

//    public static void putSessionTokenInSharedPreference(Activity activity, String sessionToken) {
//        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("sessionToken", sessionToken);
//        editor.apply();
//    }

    public static void putSessionTokenInSharedPreference(Context context, String sessionToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sessionToken", sessionToken);
        editor.apply();
    }

    public static void putFirstNameInSharedPreference(Activity activity, String firstName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", firstName);
        editor.apply();
    }

    public static void putLastNameInSharedPreference(Activity activity, String lastName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_name", lastName);
        editor.apply();
    }

    public static void putInviteIdInSharedPreference(Activity activity, String inviteId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("invite_id", inviteId);
        editor.apply();
    }

    public static void hideSoftKeyboard(final EditText editText,
                                        final Context context) {
        if (((Activity) context).getCurrentFocus() != null
                && ((Activity) context).getCurrentFocus() instanceof EditText) {
            final InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static String getFirstTimeContactsSynced(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString("local_contacts_synced", "no");
    }

    public static void setFirstTimeContactsSynced(Context context, String isSynced) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TruContactsConstant.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("local_contacts_synced", isSynced);
        editor.apply();
    }

    private static String receivedPendingInviteId = null;

    public static void setReceivedPendingInviteId(String inviteId) {
        receivedPendingInviteId = inviteId;
    }

    public static String getReceivedPendingInviteId() {
        return receivedPendingInviteId;
    }

    public static void resetReceivedPendingInviteId() {
        receivedPendingInviteId = null;
    }
}
