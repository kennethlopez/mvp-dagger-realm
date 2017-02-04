package com.example.kennethlopez.testapp.api;


import com.example.kennethlopez.testapp.vo.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "https://api.github.com";

    @GET("/users/{username}")
    Call<User> getUser(
            @Path("username") String username
    );
}
