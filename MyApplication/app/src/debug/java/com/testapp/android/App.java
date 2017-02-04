package com.testapp.android;

import android.app.Application;

import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.di.component.DaggerAppComponent;
import com.example.kennethlopez.testapp.di.module.AppModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {
    private AppComponent mAppComponent;

    public App() { }

    @Override
    public void onCreate() {
        super.onCreate();

        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}