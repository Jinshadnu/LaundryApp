package com.example.laundryapp.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static java.util.Objects.requireNonNull;

public class NetworkUtilities {
    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    @SuppressLint("StaticFieldLeak")
    private static NetworkUtilities networkUtil = null;
    private Context mCtx;

    /* ------------------------------------------------------------- *
     * Constructor
     * ------------------------------------------------------------- */

    private NetworkUtilities(Context mCtx) {
        this.mCtx = mCtx;
    }

    /* ------------------------------------------------------------- *
     * Public Methods
     * ------------------------------------------------------------- */

    public static NetworkUtilities getNetworkInstance(Context mCtx) {
        if (networkUtil == null)
            networkUtil = new NetworkUtilities(mCtx);

        return networkUtil;
    }

    /**
     * This method is invoked to check whether user device is connected to internet or not.
     *
     * @return a boolean value based on internet status.
     */
    public boolean isConnectedToInternet() {
        if (mCtx != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mCtx
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = requireNonNull(connectivityManager).getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }


}