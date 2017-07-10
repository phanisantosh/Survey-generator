package com.example.android.surgen.data;

import android.provider.BaseColumns;

/**
 * Created by PHANI SANTOSH on 06-07-2017.
 */

public class ResponseContract {

    private ResponseContract(){}

    public static final class ResponseEntry implements BaseColumns{

        public final static String TABLE_NAME = "allresponses";

        public final static String _ID = BaseColumns._ID;
        public final static String _SURVEYNAME = "nameofsurvey";
        public final static String _QUESTIONID = "idofquestion";
        public final static String _RESPONSETYPE = "typeofresponse";
        public final static String _RESPONSE = "response";


        public static final int QUES_DESCRIPTIVE = 0;
        public static final int QUES_MULTI = 1;
        public static final int QUES_RATING = 2;

        public static final String OPTION_A = "A";
        public static final String OPTION_B = "B";
        public static final String OPTION_C = "C";
        public static final String OPTION_D = "D";


    }

}
