package com.prozy.recycletrash.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.prozy.recycletrash.R;

public class DetailVIdeo extends YouTubeBaseActivity {

    YouTubePlayerView mPlayer;
    String key = "AIzaSyA7BiWThMvwyV-HDJfc6kMtXo6-ctCRCQw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);

        mPlayer = findViewById(R.id.vid_Play);

        final String vidId = getIntent().getStringExtra("idVid");

        mPlayer.initialize(key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                if (!wasRestored){
                    youTubePlayer.cueVideo(vidId);
                    youTubePlayer.play();
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


    }
}
