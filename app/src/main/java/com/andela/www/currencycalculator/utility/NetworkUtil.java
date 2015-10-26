package com.andela.www.currencycalculator.utility;

import android.content.Context;
import android.net.ConnectivityManager;

import com.andela.www.currencycalculator.R;

import java.net.InetAddress;

/**
 * Created by andela on 8/7/15.
 */
public class NetworkUtil {

    // METHOD TESTS NETWORK CONNECTIVITY STATE
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null);
    }

    // METHOD TESTS INTERNET CONNECTIVITY STATE
    public static boolean isInternetConnected(Context context) {
        try {
            InetAddress ipAddress = InetAddress.getByName(context.getString(R.string.internet_test_url)); //You can replace it with your name
            return (ipAddress != null);
        } catch (Exception e) {
            return false;
        }
    }
}
