package com.example.kennethlopez.testapp.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kennethlopez.testapp.di.module.AppModule;
import com.example.kennethlopez.testapp.job.FetchUserJob;
import com.example.kennethlopez.testapp.model.UserModel;
import com.google.gson.Gson;
import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context context();

    SharedPreferences sharedPreferences();

    Gson gson();

    HttpLoggingInterceptor httpLoggingInterceptor();

    Cache cache();

    OkHttpClient okHttpClient();

    Retrofit retrofit();

    JobManager jobManager();

    UserModel userModel();

    void inject(FetchUserJob fetchUserJob);

    void inject(UserModel userModel);
}