package com.vlg.testick;

import android.app.Application;

import com.vlg.testick.model.Question;
import com.vlg.testick.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private Quiz quiz;
    private List<Question> questions;

    public Quiz getQuiz() {
        if (quiz != null) {
            return quiz;
        }
        quiz = new Quiz();
        return quiz;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void onCreate() {
        questions = new ArrayList<>();
        super.onCreate();
    }
}
