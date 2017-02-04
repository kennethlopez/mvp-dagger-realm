package com.example.kennethlopez.testapp.job;

import android.content.Context;
import android.support.annotation.IntDef;

import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

public abstract class BaseJob extends Job {

    @Inject
    public Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UI_HIGH, BACKGROUND})
    public @interface Priority {

    }

    public static final int UI_HIGH = 10;
    public static final int BACKGROUND = 1;
    private AppComponent mAppComponent;

    public BaseJob(Params params) {
        super(params);
    }

    public void inject(AppComponent appComponent) {
        mAppComponent = appComponent;
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected int getRetryLimit() {
        // https://github.com/yigit/android-priority-jobqueue/issues/239
        return super.getRetryLimit();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
