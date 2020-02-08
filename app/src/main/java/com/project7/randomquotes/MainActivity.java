package com.project7.randomquotes;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    RelativeLayout relativeLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    AdView adView;
    InterstitialAd popUpAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        relativeLayout = findViewById(R.id.relativeLayout);
        swipeRefreshLayout= findViewById(R.id.swipeRefresh);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        checkConnection();
        MobileAds.initialize(this,getString(R.string.admob_app_id));
        adView = findViewById(R.id.adView);
        popUpAd = new InterstitialAd(this);
        popUpAd.setAdUnitId(getString(R.string.popup_ad_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GRAY,Color.BLACK,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkConnection();
            }
        });
    }
    public void displayInterstitial(){
        if(popUpAd.isLoaded()){
            popUpAd.show();
        }
    }

    public void checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected()){
            webView.loadUrl("file:///android_asset/www/Index.html");
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else if(mobileNetwork.isConnected()){
            webView.loadUrl("file:///android_asset/www/Index.html");
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else{
            relativeLayout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onStop() {
        super.onStop();
        popUpAd.loadAd(new AdRequest.Builder().build());

    }
    @Override
    protected void onResume() {
        super.onResume();
        displayInterstitial();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

}
