package com.manu.retrofit_minitwitter.data;

import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.manu.retrofit_minitwitter.common.Constantes;
import com.manu.retrofit_minitwitter.common.MyApp;
import com.manu.retrofit_minitwitter.common.SharedPreferencesManager;
import com.manu.retrofit_minitwitter.retrofit.AuthTwitterService;
import com.manu.retrofit_minitwitter.retrofit.request.RequestCreateTweet;
import com.manu.retrofit_minitwitter.retrofit.response.AuthTwitterClient;
import com.manu.retrofit_minitwitter.retrofit.response.Like;
import com.manu.retrofit_minitwitter.retrofit.response.Tweet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {
    AuthTwitterService authTwitterService;
    AuthTwitterClient authTwitterClient;
    // Para la lista de datos 'vivos'
    MutableLiveData<List<Tweet>> allTweets;

    MutableLiveData<List<Tweet>> favTweets;
    String userName;

    // Creamos el constructor para poer inicializar las variables

    TweetRepository(){
            authTwitterClient = authTwitterClient.getInstance();
            authTwitterService = authTwitterClient.getAuthTwittterService();
            allTweets = getAllTweets();
            userName = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USERNAME);
        }



       public MutableLiveData<List<Tweet>> getAllTweets(){
            if(allTweets == null){
                allTweets = new MutableLiveData<>();
            }

           // Aqui ejecutamos la peticion
           Call<List<Tweet>> call = authTwitterService.getAllTweets();

           call.enqueue(new Callback<List<Tweet>>() {
               @Override
               public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                   if(response.isSuccessful()){
                       allTweets.setValue(response.body());

                   }else{
                       Toast.makeText(MyApp.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onFailure(Call<List<Tweet>> call, Throwable t) {
                   Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
               }
           });

           return allTweets;
       }

       public MutableLiveData<List<Tweet>> getFavsTweets(){
        if(favTweets== null){
            favTweets = new MutableLiveData<>();
        }

        List<Tweet> newFavList = new ArrayList<>();
        Iterator itTweets = allTweets.getValue().iterator();

        while (itTweets.hasNext()){
            Tweet current = (Tweet) itTweets.next();
            // ahora hay q evaluar los likes, otro Iterator para los likes
            Iterator itLikes = current.getLikes().iterator();
            // usuario encontrado
            boolean enc =  false;
            while (itLikes.hasNext() && !enc){
                Like like = (Like)itLikes.next();
                if(like.getUsername().equals(userName)){
                    // encontrado el like
                    enc = true;
                    newFavList.add(current);
                }
            }
        }

        favTweets.setValue(newFavList);

        return favTweets;
       }


    // Metodo para refrescar la lista de tweets que tenemos arriba
    // Recibimos el mensaje por parametro
    public void createTweet(String mensaje) {
        // Cuerpo de la peticion, le pasamos el mensaje
        RequestCreateTweet requestCreateTweet = new RequestCreateTweet(mensaje);
        // Realizamos la peticion con el authTwitterService
        Call<Tweet> call = authTwitterService.createTweet(requestCreateTweet);
        // Invocamos a la llamada
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if(response.isSuccessful()){
                    List<Tweet> listaClonada = new ArrayList<>();
                    // Añadimos en primer lugar el nuevo tweet que nos llega del servidor
                    listaClonada.add(response.body());
                    // Añadimos el rest de tweets
                    for(int i=0; i< allTweets.getValue().size();i++){
                        listaClonada.add(new Tweet(allTweets.getValue().get(i)));
                    }
                    // refrescamos la lista
                    allTweets.setValue(listaClonada);

                }else{
                    Toast.makeText(MyApp.getContext(), "Algo ha dio mal, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void likeTweet(final int idTweet) {

        // Realizamos la peticion con el authTwitterService
        Call<Tweet> call = authTwitterService.likeTweet(idTweet);
        // Invocamos a la llamada
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if(response.isSuccessful()){
                    List<Tweet> listaClonada = new ArrayList<>();


                    for(int i=0; i< allTweets.getValue().size();i++){
                        // Comprobamos si el Tweet por el que pasamos tiene el id al que hemos hecho like
                        if(allTweets.getValue().get(i).getId() == idTweet){
                            // Si es el tweet al que hemos hecho like
                            // introducimos el elemento que nos llega del servidor
                            listaClonada.add(response.body());
                        }else{
                            listaClonada.add(new Tweet(allTweets.getValue().get(i)));
                        }
                    }
                    // refrescamos la lista
                    allTweets.setValue(listaClonada);

                    getAllTweets();

                }else{
                    Toast.makeText(MyApp.getContext(), "Algo ha dio mal, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });


    }

    }



