package com.example.kennethlopez.testapp.model;


import com.example.kennethlopez.testapp.vo.User;
import com.testapp.android.App;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserModel extends BaseModel {

    public UserModel (App app) {
        super();
        app.getAppComponent().inject(this);
    }

    public void save(final User user) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
    }

    public RealmResults<User> getAll() {
        return mRealm.where(User.class).findAll();
    }
}