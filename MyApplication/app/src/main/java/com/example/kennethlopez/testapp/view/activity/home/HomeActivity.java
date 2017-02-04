package com.example.kennethlopez.testapp.view.activity.home;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kennethlopez.testapp.R;
import com.example.kennethlopez.testapp.view.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeContract.HomeView{
    private HomeContract.HomePresenter mPresenter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        mPresenter = new HomePresenterImpl(getComponent());
        mPresenter.setView(this);
    }

    @Override
    public void setButtonAddUserOnClickListener() {
        mButtonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonAddUserClick();
            }
        });
    }

    @Override
    public String getUsername() {
        return mUsername.getText().toString();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.fetching_user_data_text));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showToast() {
        Toast.makeText(this, R.string.user_data_fetched_message, Toast.LENGTH_LONG).show();
    }

    @Bind(R.id.home_activity_button_add_user) AppCompatButton mButtonAddUser;
    @Bind(R.id.home_activity_user_name) EditText mUsername;
}