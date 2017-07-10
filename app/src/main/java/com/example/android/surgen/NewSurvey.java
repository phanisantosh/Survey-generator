package com.example.android.surgen;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surgen.data.SurveyContract;
import com.example.android.surgen.data.SurveyDbHelper;

import java.util.ArrayList;

public class NewSurvey extends Activity {

    private SurveyDbHelper mDbHelper;
    String surveyname1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);
        surveyname1 = getIntent().getExtras().getString("survey_name");
        setTitle(surveyname1);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewSurvey.this,EditSurvey.class);
                i.putExtra("survey_name",surveyname1);
                startActivity(i);
            }
        });

        mDbHelper = new SurveyDbHelper(this,surveyname1);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String SQL_CREATE_SURVEY_TABLE = "CREATE TABLE IF NOT EXISTS " + surveyname1 + " ("
                + SurveyContract.SurveyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SurveyContract.SurveyEntry.QUESTION_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + SurveyContract.SurveyEntry.COLUMN_QUESTION + " TEXT NOT NULL, "
                + SurveyContract.SurveyEntry.CHOICE_1 + " TEXT NOT NULL, "
                + SurveyContract.SurveyEntry.CHOICE_2 + " TEXT NOT NULL, "
                + SurveyContract.SurveyEntry.CHOICE_3 + " TEXT NOT NULL, "
                + SurveyContract.SurveyEntry.CHOICE_4 + " TEXT NOT NULL, "
                + SurveyContract.SurveyEntry.STEP_SIZE + " INTEGER NOT NULL DEFAULT 1, "
                + SurveyContract.SurveyEntry.NUMBER_OF_STARS + " INTEGER NOT NULL DEFAULT 5 ); ";

        db.execSQL(SQL_CREATE_SURVEY_TABLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                SurveyContract.SurveyEntry._ID,
                SurveyContract.SurveyEntry.QUESTION_TYPE,
                SurveyContract.SurveyEntry.COLUMN_QUESTION,
                SurveyContract.SurveyEntry.CHOICE_1,
                SurveyContract.SurveyEntry.CHOICE_2,
                SurveyContract.SurveyEntry.CHOICE_3,
                SurveyContract.SurveyEntry.CHOICE_4,
                SurveyContract.SurveyEntry.NUMBER_OF_STARS,
                SurveyContract.SurveyEntry.STEP_SIZE};

        Cursor cursor = db.query(
                surveyname1,
                projection,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Question> listques = new ArrayList<Question>();


        TextView displayView = (TextView) findViewById(R.id.text_view_survey);

        try {

            displayView.setText("This survey has " + cursor.getCount() + " questions.\n\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.COLUMN_QUESTION);
            int choiceaColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_1);
            int choicebColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_2);
            int choicecColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_3);
            int choicedColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_4);
            int typeofquestionColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.QUESTION_TYPE);
            int numberofstarsColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.NUMBER_OF_STARS);
            int stepsizeColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.STEP_SIZE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                int currentQuestiontype = cursor.getInt(typeofquestionColumnIndex);
                int currentNumberofstars = cursor.getInt(numberofstarsColumnIndex);
                int currentStepsize = cursor.getInt(stepsizeColumnIndex);

                String currentName = cursor.getString(nameColumnIndex);
                String currentChoiceA = cursor.getString(choiceaColumnIndex);
                String currentChoiceB = cursor.getString(choicebColumnIndex);
                String currentChoiceC = cursor.getString(choicecColumnIndex);
                String currentChoiceD = cursor.getString(choicedColumnIndex);

                String stringcurrentID = String.valueOf(currentID);
                String stringcurrentQuestiontype = String.valueOf(currentQuestiontype);
                String stringcurrentNumberofstars = String.valueOf(currentNumberofstars);
                String stringcurrentStepsize = String.valueOf(currentStepsize);


                listques.add(new Question(stringcurrentID,currentName));
            }
        } finally {

            cursor.close();
        }

        QuesListAdapter quesAdapter = new QuesListAdapter(this,listques);

        ListView lv = (ListView) findViewById(R.id.questions_in_list);
        lv.setAdapter(quesAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                TextView num=(TextView) view.findViewById(R.id.question_number);
                TextView quest=(TextView) view.findViewById(R.id.question_text);

                Intent openUpdater = new Intent(NewSurvey.this,UpdateSurvey.class);
                Bundle extras = new Bundle();
                extras.putString("survey_name",surveyname1);
                extras.putLong("question_id",id);
                extras.putInt("question_position",position);
                openUpdater.putExtras(extras);
                startActivity(openUpdater);


                Toast.makeText(getApplicationContext(),
                        num.getText().toString()+". "+quest.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }



}
