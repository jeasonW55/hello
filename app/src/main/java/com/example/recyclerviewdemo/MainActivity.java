package com.example.recyclerviewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeason.http.httpUtils.CreateHttp;
import com.jeason.http.httpUtils.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private InformationAdapter adapter;

    private ArrayList<PageItem> pageItems;

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
       // initAdapter();

      //  initRecyclerView();
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void initAdapter() {
//        ArrayList<String> data = new ArrayList<>();
//        for(int i = 0;i < 20;i++){
//            data.add("条目" + i);
//        }
        adapter = new InformationAdapter(pageItems);
    }

    private void findView() {
        recyclerView = findViewById(R.id.recycler_view);
    }
}