package com.vlg.testick;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.vlg.testick.ui.QuestionFragment;

public class MainActivity extends AppCompatActivity implements Navigation {

/**
    1 [title]
    2 [titleh1]
    style: [[style], default]
    question [name]
    [{[type] [name] [text] [value] [isRight] [newLine]},
    {[type] [name] [text] [value] [isRight] [newLine]},
    ...]
    ...

    title token - title, t, nameQuiz, квизСайт, названиеСайт
    titleH1 token - h1, titleH1, th1, quiz, квиз, название, заголовок
    style token - s. style, с, стиль
    question token - ?, question, q, вопрос, в
    radio token - radio, r, радио, р
    checkbox token - c, chex, checkbox, ч, чекбокс, чек
    text token - t, text, текст, т
    end line token - e
    right answer token - +, r, к

    title title
    titleQuiz h1
    style default

    ? who r q1
    val1 "dr. who"
    val2 "i am" +

    ? second c
    v1 v1 "fffff"
    v2 v2 "ffff" +
    v3 v3 "ffffss"

    ? third
    text q1 "who is fuck" ok n
 **/
    private App app;

    public App getApp() {
        return app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startFragment(new QuestionFragment());
        app = (App) getApplication();
    }

    @Override
    public void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.container, fragment).commit();
    }
}