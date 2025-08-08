package com.vlg.testick;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.vlg.testick.ui.QuestionFragment;

public class MainActivity extends AppCompatActivity implements Navigation {

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