package com.example.kennethlopez.testapp.view.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kennethlopez.testapp.di.component.ActivityComponent;
import com.example.kennethlopez.testapp.di.component.DaggerActivityComponent;
import com.testapp.android.App;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComponent = DaggerActivityComponent.builder()
                .appComponent(getApp().getAppComponent())
                .build();
    }

    public ActivityComponent getComponent() {
        return mComponent;
    }

    protected App getApp() {
        return (App) getApplicationContext();
    }
}