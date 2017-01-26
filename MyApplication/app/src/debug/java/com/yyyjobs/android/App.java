package com.yyyjobs.android;

import android.app.Application;

import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.di.component.DaggerAppComponent;
import com.example.kennethlopez.testapp.di.module.AppModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application {
    private AppComponent mAppComponent;

    public App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        // Configure Realm for application
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        // Make this Realm the default
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}