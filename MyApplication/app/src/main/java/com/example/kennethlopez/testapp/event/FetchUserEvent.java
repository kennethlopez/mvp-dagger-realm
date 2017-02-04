package com.example.kennethlopez.testapp.event;


import com.example.kennethlopez.testapp.vo.User;

public class FetchUserEvent {
    private User mUser;

    public FetchUserEvent(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }
}