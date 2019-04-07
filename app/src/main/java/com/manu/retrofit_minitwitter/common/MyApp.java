package com.manu.retrofit_minitwitter.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    // un objeto de tipo Application es un contexto
    private static MyApp instance;

    public static MyApp getinstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    // Este metodo se ejecuta una sola vez al abrir la App
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
