package com.manu.retrofit_minitwitter.retrofit;

import com.manu.retrofit_minitwitter.common.Constantes;
import com.manu.retrofit_minitwitter.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Authinterceptor implements Interceptor {
    @Override
    // El objeto chain nos permite enlazar la peticion que recibimos y la que finalmente vamos a lanzar
    public Response intercept(Chain chain) throws IOException {
        // Recuperamos (Interceptamos) el dato
        String token =  SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN);

        // Creamos la peticion añadiendo la cabecera
        Request request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(request);
    }
}
