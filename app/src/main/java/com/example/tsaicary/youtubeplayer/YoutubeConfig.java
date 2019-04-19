package com.example.tsaicary.youtubeplayer;

/**
 * Created by tsaicary on 2018/3/29.
 */

public
class YoutubeConfig {

    private static final String API_KEY = "AIzaSyD_YYnTMRk5Hrqa_9NzU-H7BszGgr6f7sQ";

    private static final String PLAY_LIST = "PL7rBJWuEBrPYjYyNkEQymlx_Wj9BkyTbM";

    private static final String PAGETOKEN = "CAEQAA";

    private static final int MAX_RESULITS = 50;

    private static final String SNIPPET = "snippet";

    private static final String VIDEO_ID = "";

    private static final String PART = "snippet,contentDetails,statistics";

    public static
    String getApiKey() {
        return API_KEY;
    }

    public static
    String getPlayList() {
        return PLAY_LIST;
    }

    public static
    String getPagetoken() {
        return PAGETOKEN;
    }

    public static
    int getMaxResults() {
        return MAX_RESULITS;
    }

    public static
    String getPart() {
        return SNIPPET;
    }

    public  static String getVideoPart(){
        return PART;
    }

    public static String getVideoid(){
        return VIDEO_ID;
    }


}
