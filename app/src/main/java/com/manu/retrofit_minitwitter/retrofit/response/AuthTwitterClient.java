package com.manu.retrofit_minitwitter.retrofit.response;

import com.manu.retrofit_minitwitter.common.Constantes;
import com.manu.retrofit_minitwitter.retrofit.AuthTwitterService;
import com.manu.retrofit_minitwitter.retrofit.Authinterceptor;
import com.manu.retrofit_minitwitter.retrofit.MiniTwittterService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient {

    // La declaramos static para poder instanciarla sin necesidad de llamar a la clase
    private static AuthTwitterClient instance = null;

    // Necesitamos tb una instancia de la interfaz que contiene los metodos de llamadas a la API
    private AuthTwitterService miniTwittterService;

    // Variable de tipo retrofit como indica la documentacion
    private Retrofit retrofit;


    // Metodo constructor , para instanciar a retrofit
    public AuthTwitterClient(){

        // Hacemos uso del interceptor para el TOKEN que autoriza al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new Authinterceptor());
        // Creamos el cliente al que le podemos añadir informacion
        OkHttpClient cliente = okHttpClientBuilder.build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL) // url de la base de la API
                .addConverterFactory(GsonConverterFactory.create()) // llamada al conversor
                // añadimos el cliente
                .client(cliente)
                .build();

        miniTwittterService = retrofit.create(AuthTwitterService.class);
    }

    // Metodo para devolver una instancia de MiniTwitterClient
    // Patron SINGLETON. La instancia solo se crea una vez
    public static AuthTwitterClient getInstance() {
        // En caso de ser nula, la instanciamos
        if(instance == null){
            instance = new AuthTwitterClient();
        }
        // Solo la instaciamos la primera vez (SINGLETON)
        return instance;
    }

    // Nos hace falta un metodo para devolcer un objeto de tipo MiniTwittterService
    // para poder consumir los servicios que hemos definido
    public AuthTwitterService getAuthTwittterService(){
        return miniTwittterService;
    }
}
