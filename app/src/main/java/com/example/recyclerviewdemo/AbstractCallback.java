package com.example.recyclerviewdemo;

import com.jeason.http.httpUtils.ICallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public abstract class AbstractCallback<T> implements ICallback {

    @Override
    public Object parse(HttpURLConnection httpURLConnection) {
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String line = "0";
            String result = "";
            while (null != (line = bReader.readLine())) {
                result += line;
            }
            bReader.close();
            httpURLConnection.disconnect();
            return result;
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
