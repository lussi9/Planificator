package es.upm.etsiinf.planificator.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnection {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCon = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobCon = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiCon != null && wifiCon.isConnected()) || (mobCon != null && mobCon.isConnected());
    }
}
