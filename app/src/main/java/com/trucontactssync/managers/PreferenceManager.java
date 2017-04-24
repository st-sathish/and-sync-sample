/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.managers;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.trucontactssync.app.AppController;
import com.trucontactssync.common.AppLog;


public class PreferenceManager {

    private static SharedPreferences.Editor mPreferenceEditor = null;

    private static PreferenceManager mInstance = null;

    /*As private constructor is used so can not create object of this class directly.*/
    private PreferenceManager() {

    }

    @SuppressLint("CommitPrefEdits")
    public static synchronized PreferenceManager getInstance() {
        if (mInstance == null) {
            mInstance = new PreferenceManager();
            mPreferenceEditor = AppController.getInstance().getSharedPreferences().edit();
        }
        return mInstance;
    }

    public SharedPreferences.Editor getEditor(){
        return mPreferenceEditor;
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public void putString(String key, String value) {
        mPreferenceEditor.putString(key, value);
        commit();
    }


    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public void putInt(String key, int value) {
        mPreferenceEditor.putInt(key, value);
        commit();
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public void putLong(String key, long value) {
        mPreferenceEditor.putLong(key, value);
        commit();
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public void putFloat(String key, float value) {
        mPreferenceEditor.putFloat(key, value);
        commit();
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public void putBoolean(String key, boolean value) {
        mPreferenceEditor.putBoolean(key, value);
        commit();
    }

    /**
     * commit preference editor after putting values.
     */
    private void commit() {
        mPreferenceEditor.apply();
    }


    /**
     * @param key   - preference key name
     * @param value - preference value
     * @return - preference value
     */
    public String getString(String key, String value) {
        return AppController.getInstance().getSharedPreferences().getString(key, value);
    }


    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public int getInt(String key, int value) {
        return AppController.getInstance().getSharedPreferences().getInt(key, value);
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public Long getLong(String key, long value) {
        return AppController.getInstance().getSharedPreferences().getLong(key, value);
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public Float getFloat(String key, float value) {
        return AppController.getInstance().getSharedPreferences().getFloat(key, value);
    }

    /**
     * @param key   - preference key name
     * @param value - preference value
     */
    public boolean isBoolean(String key, boolean value) {
        return AppController.getInstance().getSharedPreferences().getBoolean(key, value);
    }

    /**
     * Reset preference manager data
     */
    public static void clear() {
        PreferenceManager preferenceManager = PreferenceManager.getInstance();
        SharedPreferences.Editor editor = preferenceManager.getEditor();
        if(editor != null){
            editor.clear().commit();
            AppLog.logString(" cleared shared preference ");
        }
    }
}
