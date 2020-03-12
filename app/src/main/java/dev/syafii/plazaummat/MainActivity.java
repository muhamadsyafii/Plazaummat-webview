package dev.syafii.plazaummat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import dev.syafii.plazaummat.utils.BaseNetwork;

public class MainActivity extends AppCompatActivity {

    private BaseNetwork baseNetwork;
    private String TAG = "MainActivity";
    private RelativeLayout parentLayout;
    private WebView view;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.webView);
        parentLayout = findViewById(R.id.rlayout);
        progressBar = findViewById(R.id.progressBar);

        baseNetwork = BaseNetwork.shared(this, parentLayout);
        baseNetwork.registerNetworkBroadcastForNougat();


        if (baseNetwork.isConnected()) {
            view.setWebViewClient(new myWebClient());
            view.getSettings().setLoadsImagesAutomatically(true);
            view.getSettings().setJavaScriptEnabled(true);
            view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            view.getSettings().setLoadWithOverviewMode(true);
            view.getSettings().setUseWideViewPort(true);
            view.loadUrl("https://plazaummat.com/");
        } else {
            showLoading();
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

    private class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (baseNetwork.isConnected()){
                view.loadUrl(url);
                hideLoading();
            }else {
                showLoading();
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideLoading();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }
}
