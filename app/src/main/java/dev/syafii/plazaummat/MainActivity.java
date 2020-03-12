package dev.syafii.plazaummat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import dev.syafii.plazaummat.utils.BaseNetwork;

public class MainActivity extends AppCompatActivity {

    private BaseNetwork baseNetwork;
    private String TAG = "MainActivity";
    private RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView view = findViewById(R.id.webView);
        parentLayout = findViewById(R.id.rlayout);

        baseNetwork = BaseNetwork.shared(this, parentLayout);
        baseNetwork.registerNetworkBroadcastForNougat();


        if (baseNetwork.isConnected()) {
            view.getSettings().setLoadsImagesAutomatically(true);
            view.getSettings().setJavaScriptEnabled(true);
            view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            view.getSettings().setLoadWithOverviewMode(true);
            view.getSettings().setUseWideViewPort(true);
            view.loadUrl("https://plazaummat.com/");
        } else {
            Log.e(TAG, "onCreate: connection failure");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseNetwork = BaseNetwork.shared(this, parentLayout);
        baseNetwork.registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseNetwork = BaseNetwork.shared(this, parentLayout);
        baseNetwork.registerNetworkBroadcastForNougat();
    }

}
