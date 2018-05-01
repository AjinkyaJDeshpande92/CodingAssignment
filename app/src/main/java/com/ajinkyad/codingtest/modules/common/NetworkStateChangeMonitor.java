package com.ajinkyad.codingtest.modules.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkStateChangeMonitor extends BroadcastReceiver {

    private NetworkListener networkListener;


    public void addListener(NetworkListener networkListener) {
        this.networkListener = networkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo WiFiInfo = connectivityManager != null ? connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) : null;
            NetworkInfo mobileInfo = connectivityManager != null ? connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) : null;

            // Check internet connection and accrding to state change the
            // text of activity by calling method
            if (WiFiInfo != null && WiFiInfo.isConnectedOrConnecting()) {

                if (networkListener != null) {
                    networkListener.onConnecting();
                }
            } else if (WiFiInfo != null && WiFiInfo.isConnected()) {

                if (networkListener != null) {
                    networkListener.onConnected();
                }
            } else if (mobileInfo != null && mobileInfo.isConnectedOrConnecting()) {

                if (networkListener != null) {
                    networkListener.onConnecting();
                }
            } else if (mobileInfo != null && mobileInfo.isConnected()) {

                if (networkListener != null) {
                    networkListener.onConnected();
                }
            } else {
                if (networkListener != null) {
                    networkListener.onDisconnected();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
