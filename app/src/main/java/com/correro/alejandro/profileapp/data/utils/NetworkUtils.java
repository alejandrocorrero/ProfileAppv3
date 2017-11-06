package com.correro.alejandro.profileapp.data.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager gestorConectivity = (ConnectivityManager) context
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectivity.getActiveNetworkInfo();
        return infoRed != null && infoRed.isConnected();
    }

}