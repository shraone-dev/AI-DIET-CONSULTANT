package com.aidiet.consultant.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.aidiet.consultant.BMIApp;


public class UserPref {

    private static UserPref _instance = null;
    ;

    private static SharedPreferences _sharedPreferences = null;
    private static Editor _sharedPrefEditor = null;
    private final String SHARED_PREFERENCE_NAME = "UserPref";

    private final String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";

    /**
     * Private Constructor
     */
    private UserPref() {

    }

    public static UserPref getInstance() {
        if (_instance == null) {
            _instance = new UserPref();
            _instance._initSharedPreferences();
        }
        return _instance;
    }

    /**
     * This method is used to initialized {@link SharedPreferences} and
     * {@link Editor}
     */
    private void _initSharedPreferences() {
        _sharedPreferences = _getSharedPref();
        _sharedPrefEditor = _getSharedPrefEditor();
    }

    /**
     * Method to get the SharedPreferences.
     *
     * @return the {@link SharedPreferences} object.
     */
    private SharedPreferences _getSharedPref() {
        if (_sharedPreferences == null) {
            _sharedPreferences = BMIApp.appContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return _sharedPreferences;
    }

    /**
     * Method to get the {@link Editor} for writing values to
     * {@link SharedPreferences}.
     *
     * @return the {@link Editor} object.
     */
    private Editor _getSharedPrefEditor() {
        if (_sharedPrefEditor == null) {
            _sharedPrefEditor = _getSharedPref().edit();
        }
        return _sharedPrefEditor;
    }


    public void setIsLoggedIn(boolean val) {
        _getSharedPrefEditor().putBoolean(KEY_IS_LOGGED_IN, val).commit();
    }

    public boolean isLoggedIn() {
        return _getSharedPref().getBoolean(KEY_IS_LOGGED_IN, false);
    }


}
