package com.example.android.surgen;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surgen.data.ResponseContract;
import com.example.android.surgen.data.ResponseDbHelper;
import com.example.android.surgen.data.SurveyContract;
import com.example.android.surgen.data.SurveyDbHelper;

public class GiveResponse extends Activity {

    private TextView mQuestionTextView;

    private EditText mEditTextforResponse;

    RadioGroup mOptionsRadioGroup;

    private RadioButton mOptionARadioButton;
    private RadioButton mOptionBRadioButton;
    private RadioButton mOptionCRadioButton;
    private RadioButton mOptionDRadioButton;

    private RatingBar mAnswerRatingBar;

    private ResponseDbHelper responseDbHelper;
    private SurveyDbHelper mDbHelper;

    private String surveyname;
    private int positionClicked;
    private int currentQuestiontype;
    private String optionChoosed;
    int currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_response);

        setTitle("Response");

        surveyname = getIntent().getExtras().getString("survey_name");
        positionClicked = getIntent().getExtras().getInt("position_clicked");

        mQuestionTextView = (TextView) findViewById(R.id.questiontextviewinresponse);
        mEditTextforResponse = (EditText) findViewById(R.id.editTextforresponse);
        mOptionsRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_giveresponse);

        mOptionARadioButton = (RadioButton) findViewById(R.id.option_a_radiobutton);
        mOptionBRadioButton = (RadioButton) findViewById(R.id.option_b_radiobutton);
        mOptionCRadioButton = (RadioButton) findViewById(R.id.option_c_radiobutton);
        mOptionDRadioButton = (RadioButton) findViewById(R.id.option_d_radiobutton);

        mAnswerRatingBar = (RatingBar) findViewById(R.id.ratingbar_giveresponse);

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

        int idColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.COLUMN_QUESTION);
        int choiceaColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_1);
        int choicebColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_2);
        int choicecColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_3);
        int choicedColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.CHOICE_4);
        int typeofquestionColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.QUESTION_TYPE);
        int numberofstarsColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.NUMBER_OF_STARS);
        int stepsizeColumnIndex = cursor.getColumnIndex(SurveyContract.SurveyEntry.STEP_SIZE);

        cursor.moveToPosition(positionClicked);

            currentID = cursor.getInt(idColumnIndex);
            currentQuestiontype = cursor.getInt(typeofquestionColumnIndex);
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

        cursor.close();

        mQuestionTextView.setText(currentName);


        if (currentQuestiontype == 0){
            mEditTextforResponse.setVisibility(View.VISIBLE);
            mOptionsRadioGroup.setVisibility(View.GONE);
            mAnswerRatingBar.setVisibility(View.GONE);


        } else if (currentQuestiontype == 1){
            mEditTextforResponse.setVisibility(View.GONE);
            mOptionsRadioGroup.setVisibility(View.VISIBLE);
            mAnswerRatingBar.setVisibility(View.GONE);

            mOptionARadioButton.setText(currentChoiceA);
            mOptionBRadioButton.setText(currentChoiceB);
            mOptionCRadioButton.setText(currentChoiceC);
            mOptionDRadioButton.setText(currentChoiceD);
        } else{
            mEditTextforResponse.setVisibility(View.GONE);
            mOptionsRadioGroup.setVisibility(View.GONE);
            mAnswerRatingBar.setVisibility(View.VISIBLE);

            mAnswerRatingBar.setNumStars(currentNumberofstars);
            mAnswerRatingBar.setStepSize(currentStepsize);
        }



    }

    private void saveResponse(){

        responseDbHelper = new ResponseDbHelper(this);

        SQLiteDatabase db = responseDbHelper.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(ResponseContract.ResponseEntry._SURVEYNAME,surveyname);
        values.put(ResponseContract.ResponseEntry._QUESTIONID,currentID);

        if (currentQuestiontype == 0){

            values.put(ResponseContract.ResponseEntry._RESPONSETYPE,currentQuestiontype);
            String responsedescriptive = mEditTextforResponse.getText().toString().trim();
            values.put(ResponseContract.ResponseEntry._RESPONSE,responsedescriptive);


        } else if (currentQuestiontype == 1){

            values.put(ResponseContract.ResponseEntry._RESPONSETYPE,currentQuestiontype);

            switch ( mOptionsRadioGroup.getCheckedRadioButtonId()) {
                case R.id.option_a_radiobutton:
                    optionChoosed = ResponseContract.ResponseEntry.OPTION_A;
                    break;
                case R.id.option_b_radiobutton:
                    optionChoosed = ResponseContract.ResponseEntry.OPTION_B;
                    break;
                case R.id.option_c_radiobutton:
                    optionChoosed = ResponseContract.ResponseEntry.OPTION_C;
                    break;
                case R.id.option_d_radiobutton:
                    optionChoosed = ResponseContract.ResponseEntry.OPTION_D;
                    break;
            }

            values.put(ResponseContract.ResponseEntry._RESPONSE,optionChoosed);
        } else {

            values.put(ResponseContract.ResponseEntry._RESPONSETYPE,currentQuestiontype);
            String x = String.valueOf(mAnswerRatingBar.getRating());

            values.put(ResponseContract.ResponseEntry._RESPONSE,x);

        }

        long newRowId = db.insert(ResponseContract.ResponseEntry.TABLE_NAME,null,values);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving response", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "response saved with row id: "+ newRowId, Toast.LENGTH_SHORT).show();
        }




    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor_saveresponse, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.saveresponse:
                // Save pet to database
                saveResponse();
                // Exit activity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
