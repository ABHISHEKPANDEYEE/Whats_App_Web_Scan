package com.colliery.whatsweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class WebWhats extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private LottieAnimationView nointernet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_whats);



        //Banner Ads ADMob
        MobileAds.initialize(this, "ca-app-pub-7406187827227478~2156818881");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        //nointernet
        nointernet = findViewById(R.id.animation_view);








        //ProgressBar
        progressBar = findViewById(R.id.progressBar);



        //Webview
        webView = findViewById(R.id.donView);
        String weburl = "https://web.whatsapp.com/%F0%9F%8C%90/en";
        webView.loadUrl(weburl);

        // phone rotation data protect
        if (savedInstanceState != null)
        {
            webView.restoreState(savedInstanceState);
        }
        else {
            checkconnection();
        }


            //WebView Setting Enable

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(false);


            String newUserAgent = webView.getSettings().getUserAgentString();
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }

            webView.getSettings().setUserAgentString(newUserAgent);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.reload();








        //webview client
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });



        //chrome client
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }

                super.onProgressChanged(view, newProgress);
            }
        });




        //webview media download

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, final String userAgent, final String contentDisposition, final String mimetype, long contentLength) {
                Dexter.withActivity(WebWhats.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                                request.setMimeType(mimetype);
                                String cookies = CookieManager.getInstance().getCookie(url);


                                request.addRequestHeader("cookie",cookies);
                                request.addRequestHeader("User-Agent",userAgent);
                                request.setDescription("Donloading Media.......");
                                request.setTitle(URLUtil.guessFileName(url,contentDisposition,mimetype));
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(url,contentDisposition,mimetype));


                                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                downloadManager.enqueue(request);


                                Toast.makeText(WebWhats.this, "Check Downloads Folder ", Toast.LENGTH_SHORT).show();


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                             token.continuePermissionRequest();
                            }
                        }).check();

            }
        });






                                                        }


    @Override
    public void onBackPressed()
    {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }



    }


    private void checkconnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilenetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected())
        {
            webView.setVisibility(View.VISIBLE);
            nointernet.setVisibility(View.GONE);



        } else if (mobilenetwork.isConnected())
        {
           webView.setVisibility(View.VISIBLE);
           nointernet.setVisibility(View.GONE);

        }
        else
        {
           webView.setVisibility(View.GONE);
           nointernet.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }


    @Override
    protected void onPause() {

        super.onPause();
        webView.onPause();

    }

    @Override
    protected void onResume() {

        super.onResume();
        webView.onResume();
    }
}

