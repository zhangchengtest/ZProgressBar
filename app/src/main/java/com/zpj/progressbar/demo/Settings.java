package com.zpj.progressbar.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

public class Settings {

    public static final String SERVICE = "_p2p._udp";
    public static final String GATEWAY = "https://ipfs.io";

    public static final String DOWNLOADS = "content://com.android.externalstorage.documents/document/primary:Download";
    public static final int CLICK_OFFSET = 500;
    private static final String APP_KEY = "AppKey";
    private static final String PREF_KEY = "prefKey";

    private static final String REDIRECT_URL_KEY = "redirectUrlKey";
    private static final String REDIRECT_INDEX_KEY = "redirectIndexKey";
    private static final String SORT_KEY = "sortKey";
    private static final String JAVASCRIPT_KEY = "javascriptKey";

    public static void setRedirectUrlEnabled(Context context, boolean enable) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(REDIRECT_URL_KEY, enable);
        editor.apply();
    }

    public static boolean isRedirectUrlEnabled(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(REDIRECT_URL_KEY, false);
    }

    public static void setRedirectIndexEnabled(Context context, boolean auto) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(REDIRECT_INDEX_KEY, auto);
        editor.apply();
    }

    public static boolean isRedirectIndexEnabled(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(REDIRECT_INDEX_KEY, true);

    }


    @NonNull
    public static String getDefaultSearchEngine(@NonNull String query) {
        return "https://duckduckgo.com/?q=" + query + "&kp=-1";
    }

    public static void setJavascriptEnabled(Context context, boolean auto) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(JAVASCRIPT_KEY, auto);
        editor.apply();
    }

    public static boolean isJavascriptEnabled(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(JAVASCRIPT_KEY, true);

    }

}
