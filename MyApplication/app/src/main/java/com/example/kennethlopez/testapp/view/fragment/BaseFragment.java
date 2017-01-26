package com.example.kennethlopez.testapp.view.fragment;


import android.app.Fragment;
import android.os.Bundle;

import com.example.kennethlopez.testapp.di.component.ActivityComponent;
import com.example.kennethlopez.testapp.di.component.AppComponent;
import com.example.kennethlopez.testapp.di.component.DaggerActivityComponent;
import com.example.kennethlopez.testapp.di.component.DaggerAppComponent;
import com.yyyjobs.android.App;

public class BaseFragment extends Fragment {

//    private ActivityComponent mComponent;

    private AppComponent mComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mComponent = DaggerActivityComponent.builder()
//                .appComponent(getApp().getAppComponent())
//                .build();

        mComponent = DaggerAppComponent.builder()
                .build();
    }

    protected App getApp() {
        return (App) getActivity().getApplicationContext();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

//    public ActivityComponent getComponent() {
//        return mComponent;
//    }
}
