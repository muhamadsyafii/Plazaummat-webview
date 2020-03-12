package dev.syafii.plazaummat.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import dev.syafii.plazaummat.R;


public class NetworkUtils extends BroadcastReceiver {

    View view;
    Snackbar snackbar;
    NetworkCallback networkCallback;

    public NetworkUtils(View view) {
        this.view = view;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                showDialog(false, context);
                networkCallback.onInternetChanged();
                Log.e("Internet", "Online Connect Intenet ");
            } else {
                showDialog(true, context);
                Log.e("Internet", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showDialog(boolean isShow, Context context) {

        if(isShow) {
            showSnackBar((Activity) context, view, "You're offline, please check your internet connection!");
        } else {
            hideSnackBar();
        }

    }

    public void showSnackBar(Activity activity, View view, String message){
        if (view != null){
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
            View v = snackbar.getView();
            v.setBackgroundColor(ContextCompat.getColor(activity, R.color.snackbar_bg));
            TextView textView = v.findViewById(R.id.snackbar_text);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            }

            textView.setTextColor(ContextCompat.getColor(activity, R.color.white));
            snackbar.show();
        }
    }

    private void hideSnackBar() {
        if(snackbar != null) {
            snackbar.dismiss();
        }
    }
}