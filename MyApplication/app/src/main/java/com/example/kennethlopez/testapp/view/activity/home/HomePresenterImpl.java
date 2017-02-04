package com.example.kennethlopez.testapp.view.activity.home;


import android.util.Log;

import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.event.FetchUserEvent;
import com.example.kennethlopez.testapp.job.BaseJob;
import com.example.kennethlopez.testapp.job.FetchUserJob;
import com.example.kennethlopez.testapp.model.UserModel;
import com.example.kennethlopez.testapp.view.base.BaseView;
import com.example.kennethlopez.testapp.vo.User;
import com.path.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.realm.RealmResults;

public class HomePresenterImpl implements HomeContract.HomePresenter {
    @Inject
    transient JobManager mJobManager;
    @Inject
    transient UserModel mUserModel;

    private static final String TAG = "HomePresenterImpl";

    private HomeContract.HomeView mView;

    public HomePresenterImpl(AppComponent appComponent) {
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) @SuppressWarnings("unused")
    @Override
    public void onEvent(FetchUserEvent event) {
        mUserModel.save(event.getUser());

        mView.hideProgressDialog();
        mView.showToast();

        RealmResults<User> results = mUserModel.getAll();
        for(User user : results) {
            Log.d(TAG, "onEvent: " + user.getName());
        }

        EventBus.getDefault().removeStickyEvent(FetchUserEvent.class);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        EventBus.getDefault().unregister(this);
    }
}