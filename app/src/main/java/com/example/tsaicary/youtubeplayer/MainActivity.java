package com.example.tsaicary.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.example.tsaicary.youtubeplayer.api.YoutubeApi;
import com.example.tsaicary.youtubeplayer.api.YoutubeApiService;
import com.example.tsaicary.youtubeplayer.model.VideoData.VideoData;
import com.example.tsaicary.youtubeplayer.model.YouTubeData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public
class MainActivity extends MingjingHuopaiBaseActivity {


    private YouTubeData mYouTubeData;
    private VideoData mVideoData;
    private YoutubeApiService mYoutubeApiService = YoutubeApi.getInstance().getAPI();
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainAdapter mMainAdapter;
    private AdView mAdView;


    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IninView();
        CallbackYoutubeApi();

    }


    protected
    void IninView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.youtubelist_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getApplicationContext());


        mToolbar = (Toolbar) findViewById(R.id.youtubelist_toolbar);
        setToolbar(mToolbar,"");
        setInterstitial();

        AdView mAdView = (AdView) findViewById(R.id.mingjinghuopai_AdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }




    protected
    void processController() {
        mMainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public
            void onItemClick(int position) {

                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", mYouTubeData.getItems().get(position).getSnippet().getPosition());
                bundle.putString("TITLE", mYouTubeData.getItems().get(position).getSnippet().getTitle());
                bundle.putString("VIDEOID", mYouTubeData.getItems().get(position).getSnippet().getResourceId().getVideoId());
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, YoutubePlayerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                displayInterstitial();
            }
        });
    }


    protected
    void CallbackYoutubeApi() {

        Call<YouTubeData> callSnippet = mYoutubeApiService.getYouTubeData(YoutubeConfig.getPart(), YoutubeConfig.getMaxResults(), YoutubeConfig.getPlayList(), YoutubeConfig.getApiKey());
        callSnippet.enqueue(new Callback<YouTubeData>() {
            @Override
            public
            void onResponse(Call<YouTubeData> call, Response<YouTubeData> response) {
                if (response.body() != null) {
                    mYouTubeData = response.body();
                    Log.d("MainActivity", response.code() + "");
                    Log.d("MainActivity", new Gson().toJson(mYouTubeData));
                    mMainAdapter = new MainAdapter(getApplicationContext(), mYouTubeData);
                    mRecyclerView.setAdapter(mMainAdapter);
                    processController();

                }

            }

            @Override
            public
            void onFailure(Call<YouTubeData> call, Throwable t) {
            }
        });
    }


}
