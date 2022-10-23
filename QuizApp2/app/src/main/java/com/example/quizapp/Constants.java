package com.example.quizapp;

import java.util.ArrayList;


public final class Constants {
    public static final String USER_NAME = "username";
    public static final String TOTAL_Questions = "total_questions";
    public static final String CORRECT_ANSWERS = "correct_answer";

    public final ArrayList getQuestions() {
        ArrayList questionList = new ArrayList();
        Question que1 = new Question(1, "What Country does this flag belong to?", R.drawable.ic_flag_of_argentina, "Pakistan", "Armenia", "Australia", "Argentina", 4);
        questionList.add(que1);
        Question que2 = new Question(2, "What Country does this flag belong to?", R.drawable.ic_flag_of_pakistan, "Argentina", "Pakistan", "Australia", "Syria", 2);
        questionList.add(que2);
        Question que3 = new Question(3, "What Country does this flag belong to?", R.drawable.ic_flag_of_india, "Pakistan", "Armenia", "India", "Argentina", 3);
        questionList.add(que3);
        Question que4 = new Question(4, "What Country does this flag belong to?", R.drawable.ic_flag_of_china, "USA", "Armenia", "Australia", "China", 4);
        questionList.add(que4);
        Question que5 = new Question(5, "What Country does this flag belong to?", R.drawable.ic_flag_of_australia, "Afganistan", "Armenia", "Australia", "Argentina", 3);
        questionList.add(que5);
        Question que6 = new Question(6, "What Country does this flag belong to?", R.drawable.ic_flag_of_egypt, "India", "Egypt", "Australia", "Iran", 2);
        questionList.add(que6);
        Question que7 = new Question(7, "What Country does this flag belong to?", R.drawable.ic_flag_of_turkey, "Iraq", "Iran", "Syria", "Turkey", 4);
        questionList.add(que7);
        Question que8 = new Question(8, "What Country does this flag belong to?", R.drawable.ic_flag_of_usa, "Australia", "USA", "UK", "France", 2);
        questionList.add(que8);
        Question que9 = new Question(9, "What Country does this flag belong to?", R.drawable.ic_flag_of_saudi_arabia, "Pakistan", "Iran", "Saudi Arabia", "Syria", 3);
        questionList.add(que9);
        Question que10 = new Question(10, "What Country does this flag belong to?", R.drawable.ic_flag_of_united_kingdom, "UK", "USA", "Autralia", "Argentina", 1);
        questionList.add(que10);
        return questionList;
    }




}
