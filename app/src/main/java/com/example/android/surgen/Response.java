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

import com.example.android.surgen.data.SurveyDbHelper;

import java.util.ArrayList;

public class Response extends Activity {

    private SurveyDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        setTitle("View the Responses");

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){

        SurveyDbHelper mDbHelper;

        mDbHelper = new SurveyDbHelper(this);


        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ArrayList<Surveys> listsurveys = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {

                listsurveys.add(new Surveys(c.getString(0)));
                c.moveToNext();
            }
        }


        c.close();

        SurveyListAdaper survAdapter = new SurveyListAdaper(this,listsurveys);

        ListView lv = (ListView) findViewById(R.id.surveystorespond_in_list);
        lv.setAdapter(survAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                TextView survename =(TextView) view.findViewById(R.id.surveynameinlist);

                String surveyNameFromList = survename.getText().toString();

                Intent intent = new Intent(Response.this,DisplayQuestionsinResponse.class);
                intent.putExtra("survey_name",surveyNameFromList);
                startActivity(intent);
                finish();

            }
        });

    }

}
