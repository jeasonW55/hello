package com.example.recyclerviewdemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeason.http.httpUtils.ICallback;
import com.jeason.http.httpUtils.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


public abstract class JSONCallback<T> extends AbstractCallback<T> {

    @Override
    public T  parse(HttpURLConnection connection) {
        String result = (String) super.parse(connection);
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray itemsList = jsonObject.optJSONArray("pageItems");
        ArrayList<PageItem> items = gson.fromJson(itemsList.toString(), new TypeToken<List<PageItem>>(){}.getType());
        return (T)items;
    }

}
