package com.example.android.surgen;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surgen.data.SurveyContract;
import com.example.android.surgen.data.SurveyDbHelper;

import java.util.ArrayList;


public class GenerateSurvey extends Activity {

    EditText et;
    String surveyname123;
    private SurveyDbHelper mDbHelper123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_survey);


        Button b1 = (Button) findViewById(R.id.addquesbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et = (EditText) findViewById(R.id.survey_name);
                surveyname123 = et.getText().toString();
                Intent intent = new Intent(GenerateSurvey.this,NewSurvey.class);
                intent.putExtra("survey_name",surveyname123);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){

        mDbHelper123 = new SurveyDbHelper(this);


        SQLiteDatabase db = mDbHelper123.getReadableDatabase();

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

        ListView lv = (ListView) findViewById(R.id.generatedsurveys_in_list);
        lv.setAdapter(survAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                TextView survename =(TextView) view.findViewById(R.id.surveynameinlist);

                String surveyNameFromList = survename.getText().toString();

                Intent intent = new Intent(GenerateSurvey.this,NewSurvey.class);
                intent.putExtra("survey_name",surveyNameFromList);
                startActivity(intent);
                finish();

            }
        });

    }
}
