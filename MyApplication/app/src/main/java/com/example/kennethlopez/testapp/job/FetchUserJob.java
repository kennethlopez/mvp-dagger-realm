package com.example.kennethlopez.testapp.job;


import android.util.Log;

import com.example.kennethlopez.testapp.api.ApiService;
import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.event.FetchUserEvent;
import com.example.kennethlopez.testapp.model.UserModel;
import com.example.kennethlopez.testapp.vo.User;
import com.path.android.jobqueue.Params;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchUserJob extends BaseJob {

    public static final String TAG = "FetchUserJob";

    @Inject
    transient Retrofit mRetrofit;

    private String mUsername;

    public FetchUserJob(int priority, String username) {
        super(new Params(priority).requireNetwork().persist());
        mUsername = username;
    }

    @Override
    public void inject(AppComponent appComponent) {
        super.inject(appComponent);
        appComponent.inject(this);
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "onAdded: ");
    }

    @Override
    public void onRun() throws Throwable {
        ApiService apiService = mRetrofit.create(ApiService.class);
        Response<User> response = apiService.getUser(mUsername).execute();

        if(response.isSuccessful()) {
            Log.d(TAG, "onRun: isSuccessful");

            EventBus.getDefault().postSticky(new FetchUserEvent(response.body()));
        }
    }

    @Override
    protected void onCancel() {
        Log.d(TAG, "onCancel: ");
    }
}