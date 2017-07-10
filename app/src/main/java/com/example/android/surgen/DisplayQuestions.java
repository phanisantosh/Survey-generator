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
import android.widget.Toast;

import com.example.android.surgen.data.SurveyContract;
import com.example.android.surgen.data.SurveyDbHelper;

import java.util.ArrayList;

public class DisplayQuestions extends Activity {

    String nameOfSurvey;
    private SurveyDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);

        nameOfSurvey = getIntent().getExtras().getString("survey_name");
        setTitle("List of Questions");

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        mDbHelper = new SurveyDbHelper(this);

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
                nameOfSurvey,
                projection,
                null,
                null,
                null,
                null,
                null);

        ArrayList<Question> listques = new ArrayList<Question>();


        TextView displayView = (TextView) findViewById(R.id.textview_in_takesurvey);

        try {

            displayView.setText("This survey contains " + cursor.getCount() + " questions.\n\n");

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

        ListView lv = (ListView) findViewById(R.id.listofquestionsin_takesurvey);
        lv.setAdapter(quesAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(DisplayQuestions.this,GiveResponse.class);
                Bundle extras = new Bundle();
                extras.putString("survey_name",nameOfSurvey);
                extras.putInt("position_clicked",position);

                intent.putExtras(extras);
                startActivity(intent);



            }
        });

    }


}
