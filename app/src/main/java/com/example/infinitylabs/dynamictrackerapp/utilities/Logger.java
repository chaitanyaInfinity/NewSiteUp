package com.example.infinitylabs.dynamictrackerapp.utilities;

import android.util.Log;

import com.example.infinitylabs.dynamictrackerapp.BuildConfig;

/**
 * Created by infinity on 19/4/17.
 */

public class Logger {

    public static void logError(String tag, String message) {

        if (BuildConfig.isToShowLog) {
            Log.e(tag, message+"\n\n");
        }
    }

    public static void logDebug(String tag, String message) {

        if (BuildConfig.isToShowLog) {
            Log.d(tag, message+"\n\n");
        }
    }
}
