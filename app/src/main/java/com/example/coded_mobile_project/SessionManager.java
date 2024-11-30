package com.example.coded_mobile_project;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Save login state and email
    public static void saveLoginState(Context context, boolean isLoggedIn, String email) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    // Get login state
    public static boolean isLoggedIn(Context context) {
        return getPreferences(context).getBoolean(IS_LOGGED_IN, false);
    }

    // Get logged-in user's email
    public static String getLoggedInUserEmail(Context context) {
        return getPreferences(context).getString(KEY_EMAIL, null);
    }

    // Clear session (e.g., for logout)
    public static void clearSession(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}
