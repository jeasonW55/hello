package com.example.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.annotation.Nullable;

public class NewsActivity extends Activity {

    private WebView news_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();

        String url = getIntent().getStringExtra("url");
        news_info.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initView() {
        news_info = findViewById(R.id.news_info);
        news_info.getSettings().setAllowContentAccess(true);
        news_info.getSettings().setJavaScriptEnabled(true);
    }
}
