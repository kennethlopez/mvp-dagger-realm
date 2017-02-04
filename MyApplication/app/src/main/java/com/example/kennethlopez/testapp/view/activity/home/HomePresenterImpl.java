package com.example.kennethlopez.testapp.view.activity.home;


import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.job.BaseJob;
import com.example.kennethlopez.testapp.job.FetchUserJob;
import com.example.kennethlopez.testapp.view.base.BaseView;
import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

public class HomePresenterImpl implements HomeContract.HomePresenter {
    @Inject
    transient JobManager mJobManager;

    private HomeContract.HomeView mView;

    public HomePresenterImpl(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    public void setView(BaseView baseView) {
        mView = (HomeContract.HomeView) baseView;
        mView.setButtonAddUserOnClickListener();
    }

    @Override
    public void onButtonAddUserClick() {
        String username = mView.getUsername();

        mView.showProgressDialog();
        mJobManager.addJobInBackground(new FetchUserJob(BaseJob.UI_HIGH, username));
    }
}