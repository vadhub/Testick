package com.vlg.testick.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vlg.testick.R;

import java.io.File;

public class WebFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_fragment, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        File cacheFile = new File(view.getContext().getCacheDir(), "index.html");

        WebView mWebView = view.findViewById(R.id.webTest);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setTextZoom(180);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.loadUrl(cacheFile.getAbsolutePath());
    }
}
