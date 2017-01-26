package com.example.kennethlopez.testapp.util;

import com.path.android.jobqueue.log.CustomLogger;

public class L implements CustomLogger {

    private static final String TAG = "L";

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void d(String text, Object... args) {
        String message = String.format(text, args);
//        Log.d(TAG, ic_message);
    }

    @Override
    public void e(Throwable t, String text, Object... args) {
        String message = String.format(text, args);
//        Log.e(TAG, ic_message);
//        Log.e(TAG, Log.getStackTraceString(t));
    }

    @Override
    public void e(String text, Object... args) {
        String message = String.format(text, args);
//        Log.e(TAG, ic_message);
    }
}
