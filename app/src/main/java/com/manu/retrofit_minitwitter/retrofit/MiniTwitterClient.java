package com.manu.retrofit_minitwitter.retrofit;

import com.manu.retrofit_minitwitter.common.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {

    // La declaramos static para poder instanciarla sin necesidad de llamar a la clase
    private static MiniTwitterClient instance = null;

    // Necesitamos tb una instancia de la interfaz que contiene los metodos de llamadas a la API
    private MiniTwittterService miniTwittterService;

    // Variable de tipo retrofit como indica la documentacion
    private Retrofit retrofit;


    // Metodo constructor , para instanciar a retrofit
    public MiniTwitterClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL) // url de la base de la API
                .addConverterFactory(GsonConverterFactory.create()) // llamada al conversor
                .build();

        miniTwittterService = retrofit.create(MiniTwittterService.class);
    }

    // Metodo para devolver una instancia de MiniTwitterClient
    // Patron SINGLETON. La instancia solo se crea una vez
    public static MiniTwitterClient getInstance() {
        // En caso de ser nula, la instanciamos
        if(instance == null){
            instance = new MiniTwitterClient();
        }
        // Solo la instaciamos la primera vez (SINGLETON)
        return instance;
    }

    // Nos hace falta un metodo para devolcer un objeto de tipo MiniTwittterService
    // para poder consumir los servicios que hemos definido
    public MiniTwittterService getMiniTwittterService(){
        return miniTwittterService;
    }
}
