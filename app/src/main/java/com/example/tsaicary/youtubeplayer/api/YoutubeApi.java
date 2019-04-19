package com.example.tsaicary.youtubeplayer.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CaryTsai on 2017/12/29.
 */

public class YoutubeApi {

    private static YoutubeApi retrofitManager = new YoutubeApi();

    private YoutubeApiService paxtoolApiService;

    private
    YoutubeApi(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        paxtoolApiService = retrofit.create(YoutubeApiService.class);
    }

    public static YoutubeApi getInstance(){
        return retrofitManager;
    }

    public YoutubeApiService getAPI(){
        return paxtoolApiService;
    }






}

