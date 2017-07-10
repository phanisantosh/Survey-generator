package com.example.android.surgen.data;

import android.provider.BaseColumns;

/**
 * Created by PHANI SANTOSH on 30-06-2017.
 */

public class SurveyContract {

    private SurveyContract(){}

    public static final class SurveyEntry implements BaseColumns{


        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_QUESTION = "question";
        public final static String QUESTION_TYPE = "typeofquestion";

        public final static String CHOICE_1 = "choice1";
        public final static String CHOICE_2 = "choice2";
        public final static String CHOICE_3 = "choice3";
        public final static String CHOICE_4 = "choice4";
        public final static String NUMBER_OF_STARS = "numberofstars";
        public final static String STEP_SIZE = "stepsize";

        public static final int QUES_DESCRIPTIVE = 0;
        public static final int QUES_MULTI = 1;
        public static final int QUES_RATING = 2;

    }
}
