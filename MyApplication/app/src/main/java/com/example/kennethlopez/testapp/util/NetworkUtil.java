package com.example.kennethlopez.testapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.path.android.jobqueue.network.NetworkUtilImpl;

public class NetworkUtil {

    public static boolean hasNetwork(Context ctx) {
        // check if connected to WiFi/data
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isConnectedToInternet(Context context) {
        NetworkUtilImpl networkUtil = new NetworkUtilImpl(context);
        return networkUtil.isConnected(context);
    }
}
