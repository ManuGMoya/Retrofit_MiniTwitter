package com.manu.retrofit_minitwitter.retrofit;

import com.manu.retrofit_minitwitter.RequestLogin;
import com.manu.retrofit_minitwitter.RequestSingup;
import com.manu.retrofit_minitwitter.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwittterService {


    @POST("/auth/login")
    Call<ResponseAuth>doLogin(@Body RequestLogin requestLogin);

    @POST("/auth/signup")
    Call<ResponseAuth>doSingup(@Body RequestSingup requestSingup);



}
