package com.example.kennethlopez.testapp.view.activity.home;


import com.example.kennethlopez.testapp.view.base.BasePresenter;
import com.example.kennethlopez.testapp.view.base.BaseView;

public interface HomeContract {
    interface HomePresenter extends BasePresenter{
        void onButtonAddUserClick();
    }
    interface HomeView extends BaseView{
        void setButtonAddUserOnClickListener();

        String getUsername();

        void showProgressDialog();

        void hideProgressDialog();
    }
}