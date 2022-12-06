package com.example.mb.quizapp;

import java.util.ArrayList;

class QuestionsList {
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Integer> colorsList = new ArrayList<>();

    QuestionsList() {
        questions.add(new Question(R.string.q1, true));
        questions.add(new Question(R.string.q2, false));
        questions.add(new Question(R.string.q3, false));
        questions.add(new Question(R.string.q4, true));
        questions.add(new Question(R.string.q5, true));
        questions.add(new Question(R.string.q6, false));
        questions.add(new Question(R.string.q7, true));
        colorsList.add(R.color.blue1);
        colorsList.add(R.color.blue2);
        colorsList.add(R.color.blue3);
        colorsList.add(R.color.blue4);
        colorsList.add(R.color.blue5);
        colorsList.add(R.color.blue6);
        colorsList.add(R.color.blue7);
    }

}
