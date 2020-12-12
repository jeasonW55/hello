package com.example.recyclerviewdemo;

import java.util.ArrayList;

public class Information {
    public String nextPage;
    public ArrayList<PageItem> pageItems;

    @Override
    public String toString() {
        return "Information{" +
                "nextPage='" + nextPage + '\'' +
                ", pageItems=" + pageItems.toArray() +
                '}';
    }
}
