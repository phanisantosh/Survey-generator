package com.example.android.surgen.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.surgen.data.SurveyContract.SurveyEntry;
/**
 * Created by PHANI SANTOSH on 30-06-2017.
 */

public class SurveyDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = SurveyDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "surveys.db";

    private static int DATABASE_VERSION = 2;
    private String name;


    public SurveyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public SurveyDbHelper(Context context,String name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.name = name;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*String SQL_CREATE_SURVEY_TABLE = "CREATE TABLE " + name + " ("
                + SurveyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SurveyEntry.QUESTION_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + SurveyEntry.COLUMN_QUESTION + " TEXT NOT NULL, "
                + SurveyEntry.CHOICE_1 + " TEXT NOT NULL, "
                + SurveyEntry.CHOICE_2 + " TEXT NOT NULL, "
                + SurveyEntry.CHOICE_3 + " TEXT NOT NULL, "
                + SurveyEntry.CHOICE_4 + " TEXT NOT NULL, "
                + SurveyEntry.STEP_SIZE + " INTEGER NOT NULL DEFAULT 1, "
                + SurveyEntry.NUMBER_OF_STARS + " INTEGER NOT NULL DEFAULT 5 ); ";


        db.execSQL(SQL_CREATE_SURVEY_TABLE);*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {






    }
}
