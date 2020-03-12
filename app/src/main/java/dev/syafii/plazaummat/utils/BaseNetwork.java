package dev.syafii.plazaummat.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.view.View;

import androidx.annotation.Nullable;

public class BaseNetwork{

    private Context context;
    private NetworkUtils networkUtils;
    public BaseNetworkCallback baseNetworkCallback;

    private BaseNetwork(Context context, View view) {
        this.context = context;
        networkUtils = new NetworkUtils(view);
        networkUtils.networkCallback = new NetworkCallback() {
            @Override
            public void onInternetChanged() {
                baseNetworkCallback.onRefreshInternet();
            }
        };
    }

    public static BaseNetwork shared(Context context, @Nullable View view) {
        return new BaseNetwork(context, view);
    }

    public boolean isConnected() {
        return networkUtils.isOnline(context);
    }

    public void registerNetworkBroadcastForNougat() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.registerReceiver(networkUtils, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        }
    }

    public void unregisterNetworkChanges() {
        try {
            context.unregisterReceiver(networkUtils);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
