package com.example.android.surgen;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.surgen.data.ResponseContract;
import com.example.android.surgen.data.ResponseDbHelper;
import com.example.android.surgen.data.SurveyContract;
import com.example.android.surgen.data.SurveyDbHelper;

import java.util.ArrayList;

public class ViewResponses extends Activity {

    private String surveyname;
    private int question_position;
    private ResponseDbHelper rDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_responses);
        setTitle("Responses");

        surveyname = getIntent().getExtras().getString("survey_name");
        question_position = getIntent().getExtras().getInt("position_clicked");

        rDbHelper = new ResponseDbHelper(this);
        SQLiteDatabase db = rDbHelper.getReadableDatabase();


        question_position++;
        Cursor  c = db.rawQuery("SELECT response FROM allresponses WHERE nameofsurvey = ? AND idofquestion = "+question_position+"", new String[] {surveyname});

        ArrayList<Surveys> listsurveys = new ArrayList<>();

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {

                listsurveys.add(new Surveys(c.getString(0)));
                c.moveToNext();
            }
        }

        c.close();

        SurveyListAdaper survAdapter = new SurveyListAdaper(this,listsurveys);

        ListView lv = (ListView) findViewById(R.id.viewresponses_in_list);
        lv.setAdapter(survAdapter);

    }
}
