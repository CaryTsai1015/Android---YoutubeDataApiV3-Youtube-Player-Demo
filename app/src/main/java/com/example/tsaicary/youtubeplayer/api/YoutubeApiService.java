package com.example.tsaicary.youtubeplayer.api;


import com.example.tsaicary.youtubeplayer.model.VideoData.VideoData;
import com.example.tsaicary.youtubeplayer.model.YouTubeData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by CaryTsai on 2017/12/29.
 */

public
interface YoutubeApiService {


    @GET("playlistItems")
    Call<YouTubeData> getYouTubeData(@Query("part") String part,
                                     @Query("maxResults") Integer maxResults,
//                             @Query("pageToken")String pageToken,
                                     @Query("playlistId") String playlistId,
                                     @Query("key") String key);

    @GET("videos")
    Call<VideoData> getVideoData(@Query("part") String part,
                                 @Query("id") String id,
                                 @Query("key") String key);
}
