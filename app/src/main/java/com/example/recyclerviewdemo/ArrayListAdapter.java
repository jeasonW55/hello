package com.example.recyclerviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ArrayListAdapter extends RecyclerView.Adapter<ArrayListAdapter.TextViewHolder> {

    private final ArrayList<String> mArrayList;

    public ArrayListAdapter(ArrayList<String> arrayList){
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public ArrayListAdapter.TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArrayListAdapter.TextViewHolder holder, int position) {
        holder.text.setText(mArrayList.get(position));
        holder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        public TextView text, time;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text_item);
            time = itemView.findViewById(R.id.time_item);

        }

    }
}
