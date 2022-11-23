package com.zpj.progressbar.demo;

import android.util.Log;

import androidx.annotation.Nullable;


public class LogUtils {

    @SuppressWarnings("SameReturnValue")
    public static boolean isDebug() {
        return false;
    }

    @SuppressWarnings("SameReturnValue")
    public static boolean isError() {
        return true;
    }

    public static void warning(@Nullable final String tag, @Nullable String message) {
        if (isDebug()) {
            Log.w(tag, "" + message);
        }
    }

    public static void info(@Nullable final String tag, @Nullable String message) {
        if (isDebug()) {
            Log.i(tag, "" + message);
        }
    }


    public static void debug(@Nullable final String tag, @Nullable String message) {
        if (isDebug()) {
            Log.d(tag, "" + message);
        }
    }

    public static void error(@Nullable final String tag, @Nullable String message) {
        if (isError()) {
            Log.e(tag, "" + message);
        }
    }

    public static void error(final String tag, @Nullable Throwable throwable) {
        if (isError()) {
            if (throwable != null) {
                Log.e(tag, "" + throwable.getLocalizedMessage(), throwable);
            } else {
                Log.e(tag, "no throwable");
            }
        }
    }
}
