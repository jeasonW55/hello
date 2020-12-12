package com.example.recyclerviewdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.AbstractCallback;
import com.example.recyclerviewdemo.NewsActivity;
import com.example.recyclerviewdemo.PageItem;
import com.example.recyclerviewdemo.R;
import com.jeason.http.httpUtils.CreateHttp;
import com.jeason.http.httpUtils.Request;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PageItem> pageItemArrayList;

    private static final int TYPE_HAS_IMAGE = 1;
    private static final int TYPE_NO_IMAGE = 0;

    private Context context;

    private Handler handler;

    public InformationAdapter(ArrayList<PageItem> pageItems){
        this.pageItemArrayList = pageItems;
    }



    public static class InformationViewHolder extends RecyclerView.ViewHolder {

        TextView item_info, item_hot, item_time;
        ImageView item_image;

        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.info_image);
            item_info= itemView.findViewById(R.id.item_information_text);
            item_hot = itemView.findViewById(R.id.item_hot_text);
            item_time = itemView.findViewById(R.id.item_tem_text);
        }

    }

    public static class InformationNoImageViewHolder extends RecyclerView.ViewHolder {

        TextView item_info, item_hot, item_time;

        public InformationNoImageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_info= itemView.findViewById(R.id.item_information_text);
            item_hot = itemView.findViewById(R.id.item_hot_text);
            item_time = itemView.findViewById(R.id.item_tem_text);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(pageItemArrayList.get(position).pic == null)
            return TYPE_NO_IMAGE;
        return TYPE_HAS_IMAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        handler = new Handler(context.getMainLooper());
        if(viewType == TYPE_HAS_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_item, parent, false);
            return new InformationViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_item_no_image, parent,false);
            return new InformationNoImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof InformationViewHolder){
            InformationViewHolder informationViewHolder = (InformationViewHolder) holder;
            informationViewHolder.item_time.setText(pageItemArrayList.get(position).ctime.substring(11,16) + "");
            informationViewHolder.item_hot.setText(pageItemArrayList.get(position).hot + " 评论");
            informationViewHolder.item_info.setText(pageItemArrayList.get(position).title);
            //在此处加载网络图片
            if(pageItemArrayList.get(position).pic != null){
                String imageUrl = pageItemArrayList.get(position).pic;
                CreateHttp createHttp = new CreateHttp();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            createHttp.setRequestParameter(imageUrl,null)
                                    .setRequestMethod(Request.RequestMethod.GET)
                                    .setiCallback(new AbstractCallback() {
                                        @Override
                                        public Object parse(HttpURLConnection httpURLConnection) {
                                            try {
                                                Log.d("cache", context.getExternalCacheDir().toString());
                                                pageItemArrayList.get(position).pic = context.getExternalCacheDir() + "/" +imageUrl.substring(imageUrl.length() - 10);
                                                FileOutputStream out = new FileOutputStream(pageItemArrayList.get(position).pic);
                                                InputStream is = httpURLConnection.getInputStream();
                                                byte[] buffer =    new byte[2048];
                                                int len;
                                                while ((len = is.read(buffer)) != -1) {
                                                    out.write(buffer, 0, len);
                                                }
                                                return "ok";
                                            }catch (IOException e){
                                                e.printStackTrace();
                                            }
                                            return "fail";
                                        }

                                        @Override
                                        public void onSuccess(Object o) {
                                            if(o != null){
                                                Log.d("cache", o.toString());
                                                Bitmap bitmap = BitmapFactory.decodeFile(pageItemArrayList.get(position).pic);
                                                if(bitmap != null){
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

//                                                            informationViewHolder.item_image.setBackgroundColor(Color.WHITE);
                                                            informationViewHolder.item_image.setImageBitmap(bitmap);
                                                        }
                                                    },0);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure() {

                                        }
                                    }).connnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //informationViewHolder.item_image.setBackground(context.getResources().getDrawable(R.drawable.ic_test));
                    }
                },0);
            }
        }else if(holder instanceof InformationNoImageViewHolder){
            InformationNoImageViewHolder no = (InformationNoImageViewHolder) holder;
            no.item_time.setText(pageItemArrayList.get(position).ctime.substring(11,16) + "");
            no.item_hot.setText(pageItemArrayList.get(position).hot + " 评论");
            no.item_info.setText(pageItemArrayList.get(position).title);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra("url", pageItemArrayList.get(position).url);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pageItemArrayList.size();
    }
}
