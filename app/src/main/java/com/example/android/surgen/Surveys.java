package com.example.android.surgen;

/**
 * Created by PHANI SANTOSH on 04-07-2017.
 */

public class Surveys {

    private String tablenameindb;

    public Surveys(String tablename){
        tablenameindb = tablename;
    }

    public String getTablenameindb(){
        return tablenameindb;
    }
}
