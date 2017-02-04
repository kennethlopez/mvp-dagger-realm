package com.example.kennethlopez.testapp.view.fragment;


import android.app.Fragment;
import android.os.Bundle;

import com.example.kennethlopez.testapp.di.component.ActivityComponent;
import com.example.kennethlopez.testapp.di.component.DaggerActivityComponent;
import com.testapp.android.App;

public class BaseFragment extends Fragment {

    private ActivityComponent mComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mComponent = DaggerActivityComponent.builder()
                .appComponent(getApp().getAppComponent())
                .build();
    }

    protected App getApp() {
        return (App) getActivity().getApplicationContext();
    }

    public ActivityComponent getComponent() {
        return mComponent;
    }
}