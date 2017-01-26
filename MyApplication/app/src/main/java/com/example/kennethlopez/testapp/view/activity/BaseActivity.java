package com.example.kennethlopez.testapp.view.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.kennethlopez.testapp.di.component.ActivityComponent;
import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.di.component.DaggerActivityComponent;
import com.example.kennethlopez.testapp.di.component.DaggerAppComponent;
import com.yyyjobs.android.App;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends AppCompatActivity {

//    private ActivityComponent mComponent;

    private AppComponent mComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mComponent = DaggerActivityComponent.builder()
//                .appComponent(getApp().getAppComponent())
//                .build();
        mComponent = DaggerAppComponent.builder()
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public AppComponent getComponent() {
        return mComponent;
    }

//    public ActivityComponent getComponent() {
//        return mComponent;
//    }

    protected App getApp() {
        return (App) getApplicationContext();
    }
}