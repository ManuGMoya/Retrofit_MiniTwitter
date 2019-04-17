package com.manu.retrofit_minitwitter.retrofit;

import com.manu.retrofit_minitwitter.retrofit.request.RequestLogin;
import com.manu.retrofit_minitwitter.retrofit.request.RequestSingup;
import com.manu.retrofit_minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MiniTwittterService {

    @POST("auth/login")
    Call<ResponseAuth>doLogin(@Body RequestLogin requestLogin);

    @POST("auth/signup")
    Call<ResponseAuth>doSingup(@Body RequestSingup requestSingup);



}
