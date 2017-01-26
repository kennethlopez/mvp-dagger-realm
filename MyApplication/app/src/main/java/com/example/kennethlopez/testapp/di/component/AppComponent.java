package com.example.kennethlopez.testapp.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kennethlopez.testapp.di.module.AppModule;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();

    SharedPreferences sharedPreferences();

    Gson gson();
}
