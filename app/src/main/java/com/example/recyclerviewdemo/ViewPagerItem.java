package com.example.recyclerviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ViewPagerItem extends LinearLayout {

    public ViewPagerItem(Context context) {
        this(context, null);
    }

    public ViewPagerItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0 );
    }

    public ViewPagerItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_pager_item, this);
    }
}
