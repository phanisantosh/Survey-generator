package com.example.android.surgen;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.surgen.data.SurveyContract;
import com.example.android.surgen.data.SurveyDbHelper;

public class UpdateSurvey extends Activity {

    private EditText mNameEditText;
    private EditText moptionaEditText;
    private EditText moptionbEditText;
    private EditText moptioncEditText;
    private EditText moptiondEditText;
    private EditText mnumberofstarsEditText;
    private EditText mstepsizeofstarsEditText;

    private Spinner mQuestypeSpinner;

    private LinearLayout layoutDescriptive;
    private LinearLayout layoutMultipleChoice;
    private LinearLayout layoutRatingbar;

    private SurveyDbHelper mDbHelper;
    String surveyname;
    Long question_id;
    int question_position;

    private int mQuestype = SurveyContract.SurveyEntry.QUES_DESCRIPTIVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey);
        setTitle("Edit Question");
        surveyname = getIntent().getExtras().getString("survey_name");
        question_id = getIntent().getExtras().getLong("question_id");
        question_position = getIntent().getExtras().getInt("question_position");

        mNameEditText = (EditText) findViewById(R.id.editText);
        mQuestypeSpinner = (Spinner) findViewById(R.id.spinner);
        moptionaEditText = (EditText) findViewById(R.id.option_a);
        moptionbEditText = (EditText) findViewById(R.id.option_b);
        moptioncEditText = (EditText) findViewById(R.id.option_c);
        moptiondEditText = (EditText) findViewById(R.id.option_d);
        mnumberofstarsEditText = (EditText) findViewById(R.id.number_of_stars);
        mstepsizeofstarsEditText = (EditText) findViewById(R.id.step_size_of_rating);

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
                surveyname,
                projection,
                null,
                null,
                null,
                null,
                null);

        setupSpinner();

        cursor.moveToPosition(question_position);
        int typeofquestionColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.QUESTION_TYPE);
        int currentQuestiontype = cursor.getInt(typeofquestionColumnIndex);

        int nameColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.COLUMN_QUESTION);
        String currentName = cursor.getString(nameColumnIndex);
        mNameEditText.setText(currentName);

        if (currentQuestiontype == 0){

            mQuestypeSpinner.setSelection(0);




        } else if (currentQuestiontype == 1){

            mQuestypeSpinner.setSelection(1);

            int choiceaColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_1);
            int choicebColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_2);
            int choicecColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_3);
            int choicedColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_4);

            String currentChoiceA = cursor.getString(choiceaColumnIndex);
            String currentChoiceB = cursor.getString(choicebColumnIndex);
            String currentChoiceC = cursor.getString(choicecColumnIndex);
            String currentChoiceD = cursor.getString(choicedColumnIndex);

            moptionaEditText.setText(currentChoiceA);
            moptionbEditText.setText(currentChoiceB);
            moptioncEditText.setText(currentChoiceC);
            moptiondEditText.setText(currentChoiceD);



        } else {

            mQuestypeSpinner.setSelection(2);

            int numberofstarsColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.NUMBER_OF_STARS);
            int stepsizeColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.STEP_SIZE);

            int currentNumberofstars = cursor.getInt(numberofstarsColumnIndex);
            int currentStepsize = cursor.getInt(stepsizeColumnIndex);

            String stringcurrentNumberofstars = String.valueOf(currentNumberofstars);
            String stringcurrentStepsize = String.valueOf(currentStepsize);

            mnumberofstarsEditText.setText(stringcurrentNumberofstars);
            mstepsizeofstarsEditText.setText(stringcurrentStepsize);

        }

        cursor.close();

    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout

        layoutDescriptive = (LinearLayout) findViewById(R.id.layoutfordescriptive);
        layoutMultipleChoice = (LinearLayout) findViewById(R.id.layoutformultiplechoice);
        layoutRatingbar = (LinearLayout) findViewById(R.id.layoutforratingbar);

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mQuestypeSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mQuestypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.question_descriptive))) {
                        mQuestype = SurveyContract.SurveyEntry.QUES_DESCRIPTIVE;

                        layoutDescriptive.setVisibility(View.VISIBLE);
                        layoutMultipleChoice.setVisibility(View.GONE);
                        layoutRatingbar.setVisibility(View.GONE);

                    } else if (selection.equals(getString(R.string.question_multiplechoice))) {
                        mQuestype = SurveyContract.SurveyEntry.QUES_MULTI;

                        layoutDescriptive.setVisibility(View.VISIBLE);
                        layoutMultipleChoice.setVisibility(View.VISIBLE);
                        layoutRatingbar.setVisibility(View.GONE);

                    } else {
                        mQuestype = SurveyContract.SurveyEntry.QUES_RATING;

                        layoutDescriptive.setVisibility(View.VISIBLE);
                        layoutMultipleChoice.setVisibility(View.GONE);
                        layoutRatingbar.setVisibility(View.VISIBLE);
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mQuestype = SurveyContract.SurveyEntry.QUES_DESCRIPTIVE;
            }
        });
    }


    private void updatePet() {

        String nameString = mNameEditText.getText().toString().trim();
        String nameString1 = moptionaEditText.getText().toString().trim();
        String nameString2 = moptionbEditText.getText().toString().trim();
        String nameString3 = moptioncEditText.getText().toString().trim();
        String nameString4 = moptiondEditText.getText().toString().trim();
        String nameString5 = mnumberofstarsEditText.getText().toString().trim();
        String nameString6 = mstepsizeofstarsEditText.getText().toString().trim();



        SurveyDbHelper mDbHelper = new SurveyDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.COLUMN_QUESTION, nameString);
        values.put(SurveyContract.SurveyEntry.CHOICE_1, nameString1);
        values.put(SurveyContract.SurveyEntry.CHOICE_2, nameString2);
        values.put(SurveyContract.SurveyEntry.CHOICE_3, nameString3);
        values.put(SurveyContract.SurveyEntry.CHOICE_4, nameString4);
        values.put(SurveyContract.SurveyEntry.QUESTION_TYPE, mQuestype);

        if (mQuestype == 2)
        {
            int numofstars = Integer.valueOf(nameString5);
            int stepsizeofstars = Integer.valueOf(nameString6);
            values.put(SurveyContract.SurveyEntry.NUMBER_OF_STARS, numofstars);
            values.put(SurveyContract.SurveyEntry.STEP_SIZE, stepsizeofstars);

        }

        int positiontoupdate = question_position + 1;

        String condition = SurveyContract.SurveyEntry._ID + " = "+ positiontoupdate;
        long newRowId = db.update(surveyname,values,condition,null);
                //insert(surveyname, null, values);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving question", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "question updated ", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void DeletePet(){

        SurveyDbHelper mDbHelper = new SurveyDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int positiontoupdate = question_position + 1;

        String condition = SurveyContract.SurveyEntry._ID + " = "+ positiontoupdate;

        long newRowId = db.delete(surveyname,condition,null);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with deleting question", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "question deleted ", Toast.LENGTH_SHORT).show();
        }


    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.save:
                // Save pet to database
                updatePet();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.delete:
                // Do nothing for now
                //DeletePet();
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
