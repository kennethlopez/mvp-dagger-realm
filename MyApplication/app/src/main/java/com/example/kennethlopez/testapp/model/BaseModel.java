package com.example.kennethlopez.testapp.model;


import io.realm.Realm;

public class BaseModel {
    protected Realm mRealm;

    public BaseModel() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mRealm.close();
    }
}