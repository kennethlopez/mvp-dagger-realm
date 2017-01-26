package com.example.kennethlopez.testapp.di.module;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.kennethlopez.testapp.BuildConfig;
import com.example.kennethlopez.testapp.api.ApiService;
import com.example.kennethlopez.testapp.job.BaseJob;
import com.example.kennethlopez.testapp.util.Common;
import com.example.kennethlopez.testapp.util.L;
import com.example.kennethlopez.testapp.util.NetworkUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.yyyjobs.android.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

@Module
public class AppModule implements Common {

    private static String TAG = "AppModule";
    private final App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(Common.USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        // http://stackoverflow.com/a/28361096/5134063
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, message);
            }
        });
        interceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return interceptor;
    }

    @Provides
    @Singleton
    public Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(mApp.getCacheDir(), "http-cache"), 1024 * 1024 * 10); // 10mb
        } catch (Exception e) {
            Log.e(TAG, "Could not create cache!");
        }
        return cache;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {  // cache
                        Response response = chain.proceed(chain.request());
                        // re-write response header to force use of cache
                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxAge(2, TimeUnit.MINUTES)
                                .build();
                        return response.newBuilder()
                                .header("Cache-Control", cacheControl.toString())
                                .build();
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {  // offline cache
                        Request request = chain.request();
                        if (!NetworkUtil.hasNetwork(provideContext())) {
                            CacheControl cacheControl = new CacheControl.Builder()
                                    .maxStale(7, TimeUnit.DAYS)
                                    .build();
                            request = request.newBuilder()
                                    .cacheControl(cacheControl)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {  // User-Agent changes
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .addHeader("User-Agent", "gzip")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(provideHttpLoggingInterceptor())
                .cache(provideCache())
                .connectTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .build();
    }

    // https://square.github.io/okhttp/2.x/okhttp/com/squareup/okhttp/OkHttpClient.html
    // http://stackoverflow.com/a/37156033
    // https://goo.gl/KGMcsr
    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(mApp)
                .consumerKeepAlive(45)
                .maxConsumerCount(3)
                .minConsumerCount(1)
                .loadFactor(3)
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        if (job instanceof BaseJob) {
                            ((BaseJob) job).inject(mApp.getAppComponent());
                        }
                    }
                })
                .customLogger(new L())
                .build();
        return new JobManager(mApp, configuration);
    }
}
