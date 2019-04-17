package com.manu.retrofit_minitwitter.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.manu.retrofit_minitwitter.retrofit.response.Tweet;

import java.util.List;
import java.util.PriorityQueue;

public class TweetViewModel extends AndroidViewModel {
    // Definimos el acceso al repostorio (Webservice)
    private TweetRepository tweetRepository;

    // Variabe para observar los cambios de los datos
    private LiveData<List<Tweet>> tweets;

    private LiveData<List<Tweet>> favTweets;


    public TweetViewModel(@NonNull Application application) {
        super(application);
        // Instanciamos el repositorio
        tweetRepository =  new TweetRepository();

        // Obtenemos la lista de tweets
        tweets = tweetRepository.getAllTweets();
    }


    public LiveData<List<Tweet>> getTweets(){
        return tweets;
    }

    public LiveData<List<Tweet>> getFavTweets(){
        favTweets = tweetRepository.getFavsTweets();
        return favTweets;
    }

    public LiveData<List<Tweet>> getNewTweets(){
        tweets = tweetRepository.getAllTweets();
        return tweets;
    }

    public LiveData<List<Tweet>> getNewFavTweets(){
        getNewTweets();
        return getFavTweets();
    }

    public void insertTweet(String mensaje){
        tweetRepository.createTweet(mensaje);
    }

    public void likeTweet (int idTweet){
        tweetRepository.likeTweet(idTweet);
    }


}
