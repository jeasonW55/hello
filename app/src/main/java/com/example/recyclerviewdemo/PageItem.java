package com.example.recyclerviewdemo;

public class PageItem {
    public long seq;
    public String ctime;
    public String title;
    public int hot;
    public String url;
    public String pic;

    @Override
    public String toString() {
        return "PageItem{" +
                "seq=" + seq +
                ", ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", hot=" + hot +
                ", url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
