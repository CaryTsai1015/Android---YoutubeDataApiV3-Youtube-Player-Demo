package com.example.tsaicary.youtubeplayer;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tsaicary.youtubeplayer.api.YoutubeApi;
import com.example.tsaicary.youtubeplayer.api.YoutubeApiService;
import com.example.tsaicary.youtubeplayer.model.VideoData.VideoData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.youtube.player.YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT;

/**
 * Created by tsaicary on 2018/3/29.
 */

public
class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView mYouTubePlayerView;

    private Button mPlayerButton, mPauseButton, mNextButton, mPrevious;

    private YouTubePlayer mYouTubePlayer;

    private YouTubePlayer.OnInitializedListener mOnInitializedListener;

    private Toolbar mToolbar;

    private TextView mTitleTextView, mDescription, mViewCount;

    private YoutubeApiService mYoutubeApiService = YoutubeApi.getInstance().getAPI();

    private VideoData mVideoData;

    private String mVideoid;

    private int mPosition;

    private InterstitialAd interstitial;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtubeplayer);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeview);
        mYouTubePlayerView.initialize(YoutubeConfig.getApiKey(), this);
        mPlayerButton = (Button) findViewById(R.id.youtubebutton);
        mViewCount = (TextView) findViewById(R.id.viewCount_youtube_textview);
        mTitleTextView = (TextView) findViewById(R.id.title_youtube_textview);
//        mDescription = (TextView) findViewById(R.id.description_toolbar_textview);
        mPauseButton = (Button) findViewById(R.id.youtubebuttonstop);
//        mNextButton = (Button) findViewById(R.id.nextbutton);
//        mPrevious = (Button) findViewById(R.id.previousbutton);


        mPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                mYouTubePlayer.play();
                displayInterstitial();

            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                mYouTubePlayer.pause();
                displayInterstitial();

            }
        });

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-6428195540030903/4149440022");

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

//        mNextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public
//            void onClick(View v) {
//                mYouTubePlayer.next();
//
//
//            }
//        });
//
//        mPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public
//            void onClick(View v) {
//
//                mYouTubePlayer.previous();
//            }
//        });
//
    }


    @Override
    public
    void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Bundle bundle = getIntent().getExtras();
        mPosition = bundle.getInt("POSITION");
        Log.d("position", mPosition + "");
        mTitleTextView.setText(bundle.getString("TITLE"));
        mVideoid = bundle.getString("VIDEOID");
        CallbackVideoData();
//        mDescription.setText(bundle.getString("DESCRIPION"));
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//        youTubePlayer.loadPlaylist(YoutubeConfig.getPlayList(), mPosition, 0);
        youTubePlayer.loadVideo("SY8KJLE75IQ");
        youTubePlayer.setFullscreenControlFlags(FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        Log.d("時間", youTubePlayer.getCurrentTimeMillis() + "");
        mYouTubePlayer = youTubePlayer;
    }

    @Override
    public
    void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public
    void onPointerCaptureChanged(boolean hasCapture) {

    }


    protected
    void CallbackVideoData() {
        Call<VideoData> callvideodata = mYoutubeApiService.getVideoData(YoutubeConfig.getVideoPart(), mVideoid, YoutubeConfig.getApiKey());
        callvideodata.enqueue(new Callback<VideoData>() {
            @Override
            public
            void onResponse(Call<VideoData> call, Response<VideoData> response) {
                mVideoData = response.body();
                Log.d("VideoData", new Gson().toJson(mVideoData));
                Log.d("Videoc", mVideoData.getItems().get(0).getStatistics().getViewCount());
                mViewCount.setText(getResources().getString(R.string.viewCount) + mVideoData.getItems().get(0).getStatistics().getViewCount());
            }

            @Override
            public
            void onFailure(Call<VideoData> call, Throwable t) {

            }
        });
    }

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public
    void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}
