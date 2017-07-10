package com.example.android.surgen;

/**
 * Created by PHANI SANTOSH on 02-07-2017.
 */

public class Question {

    private String question_id;
    private String question;

    public Question(String id, String ques){
        question_id = id;
        question = ques;
    }

    public String getQuestion_id(){
        return question_id;
    }

    public String  getquestion(){
        return question;
    }
}
