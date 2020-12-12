package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.recyclerviewdemo.adapter.InformationAdapter;
import com.example.recyclerviewdemo.adapter.TurnAdapter;
import com.jeason.http.httpUtils.CreateHttp;
import com.jeason.http.httpUtils.Request;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private InformationAdapter adapter;

    private TurnAdapter turnAdapter;

    private ViewPager mViewPager;

    private ArrayList<PageItem> pageItems;

    private ArrayList<View> mViewPagers;

    private LinearLayout mLinearLayout;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        handler = new Handler(getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreateHttp createHttp = new CreateHttp();
                try {
                    createHttp.setiCallback(new JSONCallback<Information>() {
                        @Override
                        public void onSuccess(Object s) {
                                pageItems = (ArrayList<PageItem>) s;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initAdapter();
                                        initRecyclerView();
                                    }
                                },0);
                                if(pageItems != null){
                                    Log.d("TAG-info", s.toString() + "\n" + pageItems.get(0).title);
                                }
                        }

                        @Override
                        public void onFailure() {
                        }
                    }).setRequestMethod(Request.RequestMethod.GET)
                            .setRequestParameter("https://m.10jqka.com.cn/gsrd/V2/todayhot_1.json",null)
                            .connnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mViewPager.setAdapter(turnAdapter);
    }

    private void initAdapter() {
        adapter = new InformationAdapter(pageItems);
        turnAdapter = new TurnAdapter(mViewPagers);
    }

    private void findView() {
        mViewPager = findViewById(R.id.view_pager);
        recyclerView = findViewById(R.id.recycler_view);
        mViewPagers = new ArrayList<>();
        for(int i = 0;i < 3;i++){
            mViewPagers.add(new ViewPagerItem(this));
        }
    }
}