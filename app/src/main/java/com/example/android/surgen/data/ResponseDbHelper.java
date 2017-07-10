package com.example.android.surgen.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PHANI SANTOSH on 06-07-2017.
 */

public class ResponseDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ResponseDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "responses.db";

    private static int DATABASE_VERSION = 2;
    private String name;


    public ResponseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public ResponseDbHelper(Context context,String name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.name = name;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_SURVEY_TABLE = "CREATE TABLE " + ResponseContract.ResponseEntry.TABLE_NAME + " ("
                + ResponseContract.ResponseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ResponseContract.ResponseEntry._SURVEYNAME + " TEXT NOT NULL, "
                + ResponseContract.ResponseEntry._QUESTIONID + " INTEGER NOT NULL DEFAULT 0, "
                + ResponseContract.ResponseEntry._RESPONSETYPE + " INTEGER NOT NULL DEFAULT 0, "
                + ResponseContract.ResponseEntry._RESPONSE + " TEXT NOT NULL ); ";


        db.execSQL(SQL_CREATE_SURVEY_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
